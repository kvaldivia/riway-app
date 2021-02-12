package me.kennyvaldivia.riway.alarm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import kotlinx.coroutines.CompletableDeferred

interface UpcomingAlarmContract {
    fun snoozeAlarm(): CompletableDeferred<Int>
}