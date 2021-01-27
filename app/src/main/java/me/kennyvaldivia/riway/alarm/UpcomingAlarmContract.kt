package me.kennyvaldivia.riway.alarm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController

interface UpcomingAlarmContract {
    fun snoozeAlarm(): LiveData<Int>
}