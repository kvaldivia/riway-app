package me.kennyvaldivia.riway.alarm

import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import android.view.MenuItem
import android.widget.Toolbar
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_alarm_detail.*
import me.kennyvaldivia.riway.R
import javax.inject.Inject

/**
 * An activity representing a single Alarm detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a [AlarmListActivity].
 */
@AndroidEntryPoint
class AlarmDetailActivity : FragmentActivity() {

    @Inject
    lateinit var factory: AlarmDetailsViewModel.Factory

    private val alarmId: Long by lazy {
        intent.extras!!.get("alarmId") as Long
    }

    private val viewModel: AlarmDetailsViewModel by viewModels {
        AlarmDetailsViewModel.provideFactory(factory, alarmId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_detail)
        setActionBar(findViewById(R.id.detail_toolbar))
        findViewById<FloatingActionButton>(R.id.fab_create_alarm).setOnClickListener { view ->
            viewModel.save()
            Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        findViewById<FloatingActionButton>(R.id.fab_snooze_alarm).setOnClickListener { view ->
            viewModel.snooze()
            Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        findViewById<FloatingActionButton>(R.id.fab_unsnooze_alarm).setOnClickListener { view ->
            viewModel.unsnooze()
            Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        // Show the Up button in the action bar.
        actionBar?.setDisplayHomeAsUpEnabled(true)
        viewModel.bind(this)
    }

    private fun shouldCreateAlarm() {
        fab_snooze_alarm.hide()
        fab_create_alarm.show()
    }

    private fun shouldSnoozeAlarm() {
        fab_create_alarm.hide()
        fab_snooze_alarm.show()
    }

    override fun onResume() {
        super.onResume()
        viewModel.alarmCreated.observe(this, Observer {
            if (it) shouldSnoozeAlarm()
            else shouldCreateAlarm()
        })
        viewModel.alarmTime.observe(this, Observer {
            tv_time.text = it
        })
        viewModel.alarmVolume.observe(this, Observer {
            tv_volume_value.value = it.toFloat()
        })
        viewModel.alarmSong.observe(this, Observer {
            // TODO("Adapter for song URI")
            tv_song_value.text = it
        })
        viewModel.alarmIsActive.observe(this, Observer {
        })
        viewModel.alarmVibrate.observe(this, Observer {
            tv_vibrate_value.text = it.toString()
        })
        viewModel.alarmDays.observe(this, Observer {
            var days: String = ""
            // TODO: move this formmatter into a utils maybe
            it.forEach { entry -> days += entry.key.toString().slice(0..2) + ". " }
            tv_repeat_value.text = days.trim()
        })
    }

    override fun onPause() {
        super.onPause()
        viewModel.save()
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back

                navigateUpTo(Intent(this, AlarmListActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}