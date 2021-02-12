package me.kennyvaldivia.riway.alarm

import androidx.lifecycle.*
import io.reactivex.Completable
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.launch
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

    private lateinit var owner: LifecycleOwner

    val alarmTime = MutableLiveData<String?>()
    val state = MutableLiveData<State>(State.NO_UPCOMING_ALARMS)

    fun bind(owner: LifecycleOwner) {
        this.owner = owner
        alarmRepository.getUpcomingAlarm().observe(owner, Observer {
            MutableLiveData<Alarm?>(it).also { alarm -> currentAlarm.value = alarm.value }
        })
        currentAlarm.observe(owner, Observer {
            // FIXME: the query checks this already, so... if we get an instance it should be
            // in the state of active and unsnoozed
            if (it != null && it.isActive == true && it.isSnoozed == false) {
                alarmTime.value = it.time
                state.value = State.UPCOMING_ALARM_AVAILABLE
            } else {
                state.value = State.NO_UPCOMING_ALARMS
            }
        })
    }

    override fun snoozeAlarm(): CompletableDeferred<Int> {
        val alarm = currentAlarm.value!!.copy()
        alarm.isSnoozed = true
        val completed = CompletableDeferred<Int>()
        viewModelScope.launch {
            val id = alarmRepository.update(alarm)
            alarmRepository.getAlarm(id.toLong()).observe(owner, Observer {
                currentAlarm.value = it
            })
            completed.complete(id)
        }
        return completed
    }
}