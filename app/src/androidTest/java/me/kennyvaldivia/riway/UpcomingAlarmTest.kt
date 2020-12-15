package me.kennyvaldivia.riway

import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.observe
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import me.kennyvaldivia.riway.alarm.Alarm
import me.kennyvaldivia.riway.alarm.AlarmDao
import me.kennyvaldivia.riway.alarm.UpcomingAlarmContract
import org.junit.*
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import java.io.IOException
import java.sql.Time
import java.time.LocalTime
import java.util.*

/**
 * Instrumented test, which will execute on an Android device.
 */
//@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class UpcomingAlarmTest {
    private lateinit var alarmDao: AlarmDao
    private lateinit var db: AppDatabase
    private val hiltRule = HiltAndroidRule(this)
    private val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun createDb() {
        hiltRule.inject()
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        db.clearAllTables()
        alarmDao = db.alarmDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @get:Rule
    val upcomingAlarmActivityRule: RuleChain = RuleChain.outerRule(hiltRule)
        .around(activityRule)
        .around(instantTaskExecutorRule)

    @Test
    fun displayUpcomingAlarmSummary() {
        val alarm: Alarm = Alarm(
            1, LocalTime.now().hour + 1, LocalTime.now().minute, 1, "",
            "Test alarm", isActive = true, shouldVibrate = true, monday = true, tuesday = true,
            wednesday = true, thursday = true, friday = true, saturday = true, sunday = true
        )
        alarmDao.insert(alarm)
        val activity = activityRule.activity
        val fm = activity.supportFragmentManager
        val frag = fm.findFragmentById(R.id.fragment_upcoming_alarm) as UpcomingAlarmContract.View
        val presenter = frag.getPresenter()
        val upcomingAlarmLD = alarmDao.getUpcomingAlarm()
        val alarmHour = upcomingAlarmLD!!.value?.hour.toString().padStart(2, '0')
        val alarmMinute = upcomingAlarmLD!!.value?.minute.toString().padStart(2, '0')
        val text = "$alarmHour:$alarmMinute"
        onView(withId(R.id.tv_upcoming_alarm_time)).check(matches(withText(text)))
    }
}
