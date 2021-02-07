package me.kennyvaldivia.riway.alarm

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlarmRepository @Inject constructor(private val alarmDao: AlarmDao) {

    fun getAlarms() = alarmDao.getAll()

    fun getAlarm(alarmId: Int) = alarmDao.get(alarmId)

    fun getUpcomingAlarm() = alarmDao.getUpcomingAlarm()

    fun update(alarm: Alarm) = alarmDao.update(alarm)

    suspend fun create(alarm: Alarm) = alarmDao.create(alarm)
}