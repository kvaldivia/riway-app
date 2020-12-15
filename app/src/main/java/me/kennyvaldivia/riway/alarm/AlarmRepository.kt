package me.kennyvaldivia.riway.alarm

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlarmRepository @Inject constructor(private val alarmDao: AlarmDao) {

    fun getAlarms() = alarmDao.getAll()

    fun getAlarm(alarmId: String) = alarmDao.get(alarmId)

    fun getUpcomingAlarm() = alarmDao.getUpcomingAlarm()
}