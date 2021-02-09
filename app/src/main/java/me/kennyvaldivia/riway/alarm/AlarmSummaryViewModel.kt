package me.kennyvaldivia.riway.alarm

import androidx.lifecycle.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * The ViewModel used in [UpcomingAlarmFragment]
 */
@Singleton
class UpcomingAlarmViewModel @Inject constructor(var alarmRepository: AlarmRepository): ViewModel(), UpcomingAlarmContract {

    enum class State {
        UPCOMING_ALARM_AVAILABLE,
        NO_UPCOMING_ALARMS
    }

    private val currentAlarm: MutableLiveData<Alarm?> = MutableLiveData<Alarm?>(null)

    val alarmTime = MutableLiveData<String?>()
    val state = MutableLiveData<State>(State.NO_UPCOMING_ALARMS)

    fun bind(owner: LifecycleOwner) {
        alarmRepository.getUpcomingAlarm().observe(owner, Observer {
            MutableLiveData<Alarm?>(it).also { alarm -> currentAlarm.value = alarm.value }
        })
        currentAlarm.observe(owner, Observer {
            if (it != null) {
                alarmTime.value = it.time
                state.value = State.UPCOMING_ALARM_AVAILABLE
            } else {
                state.value = State.NO_UPCOMING_ALARMS
            }
        })
    }

    override fun snoozeAlarm(): LiveData<Int> {
        val alarm = currentAlarm.value!!.copy()
        alarm.isActive = false
        return MutableLiveData<Int>(alarmRepository.update(alarm).also {
            currentAlarm.value = alarmRepository.getUpcomingAlarm().value
        })
    }
}