package me.kennyvaldivia.riway

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import me.kennyvaldivia.riway.alarm.Alarm
import me.kennyvaldivia.riway.alarm.UpcomingAlarmContract
import me.kennyvaldivia.riway.alarm.UpcomingAlarmPresenter

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}