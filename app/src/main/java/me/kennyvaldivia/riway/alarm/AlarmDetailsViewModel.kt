package me.kennyvaldivia.riway.alarm

import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.launch
import java.util.*
import kotlin.time.hours

class AlarmDetailsViewModel @AssistedInject constructor(
    var alarmRepository: AlarmRepository,
    @Assisted alarmId: Int
) : ViewModel() {

    private lateinit var owner: LifecycleOwner

    private val alarm: MutableLiveData<Alarm?> by lazy {
        val alarm: Alarm? = alarmRepository.getAlarm(alarmId).value
        val result: Alarm = alarm ?: {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)
            Alarm(hour = hour, minute = minute)
        }()
        MutableLiveData<Alarm?>(result)
    }

    val alarmCreated: MutableLiveData<Boolean> by lazy {
        var state: Boolean = false
        alarm.observe(owner, Observer {
            state = it?.alarmId != null && it.alarmId > 0
        })
        MutableLiveData<Boolean>(state)
    }

    val alarmTime = MutableLiveData<String>()
    val alarmVolume = MutableLiveData<Double>()
    val alarmSong = MutableLiveData<String>()
    val alarmIsActive = MutableLiveData<Boolean>()
    val alarmVibrate = MutableLiveData<Boolean>()
    val alarmDays = MutableLiveData<Map<Alarm.DayNames, Boolean>>()

    fun bind(owner: LifecycleOwner) {
        this.owner = owner
        alarm.observe(owner, Observer {
            alarmTime.value = alarm.value!!.time
            alarmVolume.value = alarm.value!!.volumeLvl
            alarmSong.value = alarm.value!!.songUri
            alarmIsActive.value = alarm.value!!.isActive
            alarmVibrate.value = alarm.value!!.shouldVibrate
            alarmDays.value = alarm.value!!.activeDays
        })
    }

    fun setTime(hour: Int, minute: Int) {
        val newInstance = alarm.value!!.copy()
        newInstance.hour = hour
        newInstance.minute = minute
        alarm.value = newInstance
    }

    fun setVolume(level: Double) {
        val newInstance = alarm.value!!.copy()
        newInstance.volumeLvl = level
        alarm.value = newInstance
    }

    fun setAlarmSong(uri: String) {
        val newInstance = alarm.value!!.copy()
        newInstance.songUri = uri
        alarm.value = newInstance
    }

    fun setAlarmActive(active: Boolean) {
        val newInstance = alarm.value!!.copy()
        newInstance.isActive = active
        alarm.value = newInstance
    }

    fun setAlarmVibration(active: Boolean) {
        val newInstance = alarm.value!!.copy()
        newInstance.shouldVibrate = active
        alarm.value = newInstance
    }

    fun setAlarmDays(days: Map<Alarm.DayNames, Boolean>) {
        val newInstance = alarm.value!!.copy()
        newInstance.activeDays
        alarm.value = newInstance
    }

    fun save() {
        viewModelScope.launch {
            val alarmInstance = alarm.value!!
            if (alarmInstance.alarmId > 0) {
                alarmRepository.update(alarmInstance)
            } else {
                    alarmRepository.create(alarmInstance)
            }
        }
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(alarmId: Int): AlarmDetailsViewModel
    }

    companion object {
        fun provideFactory(
            factory: Factory,
            alarmId: Int
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return factory.create(alarmId) as T
                }
            }
    }
}