package me.kennyvaldivia.riway.alarm

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import io.reactivex.Flowable
import me.kennyvaldivia.riway.AppDatabase
import me.kennyvaldivia.riway.RiwayApp
import javax.inject.Inject


class UpcomingAlarmPresenter @Inject constructor(): UpcomingAlarmContract.Presenter() {

    lateinit var alarm: LiveData<Alarm?>

    @Inject
    lateinit var db: AppDatabase

    override fun cancelAlarm() {
        TODO("Not yet implemented")
    }

    override fun snoozeAlarm() {
        TODO("Not yet implemented")
    }

    override fun createAlarm() {
        TODO("Not yet implemented")
    }

    override fun displaySummary() {
        this.view!!.displaySummary()
    }

    override fun displayHelp() {
        this.view!!.displayHelp()
    }

    override fun updateView() {
        alarm = db.alarmDao()?.getUpcomingAlarm()
    }

    override fun bind(view: UpcomingAlarmContract.View, context: Context, application: Application) {
        super.bind(view, context, application)
        alarm = db.alarmDao()?.getUpcomingAlarm()

        alarm.observe(view as Fragment, Observer<Alarm?> {
            this.view!!.updateView(it)
        })
    }
}