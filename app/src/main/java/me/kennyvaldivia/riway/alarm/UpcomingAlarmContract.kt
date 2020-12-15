package me.kennyvaldivia.riway.alarm

import me.kennyvaldivia.riway.base.MVPContract

class UpcomingAlarmContract : MVPContract {
    interface View : MVPContract.View {
        fun displayHelp()
        fun displaySummary()
        fun onClickCancel()
        fun onClickDetails()
        fun onClickSnooze()
        fun updateView(alarm: Alarm?)
    }

    abstract class Presenter : MVPContract.Presenter() {
        private var alarm: Alarm? = null

        fun setAlarm(alarm: Alarm) {
            this.alarm = alarm
        }

        abstract fun cancelAlarm()
        abstract fun createAlarm()
        abstract fun displayHelp()
        abstract fun displaySummary()
        abstract fun snoozeAlarm()
    }
}