package me.kennyvaldivia.riway.alarm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import dagger.Provides

/**
 * The ViewModel used in [UpcomingAlarmFragment]
 */
class AlarmSummaryViewModel @AssistedInject constructor(
    alarmRepository: AlarmRepository,
    @Assisted private val alarmId: String
): ViewModel() {
    lateinit  var alarm: Alarm

    @AssistedInject.Factory
    interface AssistedFactory {
        fun create(alarmId: String): AlarmSummaryViewModel
    }

    companion object {

        fun provideFactory(
            assistedFactory: AssistedFactory,
            alarmId: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(alarmId) as T
            }
        }
    }
}