package me.kennyvaldivia.riway

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.navigation.ActivityNavigator
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import me.kennyvaldivia.riway.alarm.UpcomingAlarmViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: UpcomingAlarmViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        viewModel.state.observe(this, Observer {
            fab_alarm_summary.hide()
            when (it) {
                UpcomingAlarmViewModel.State.UPCOMING_ALARM_AVAILABLE -> {
                    setDefaultActionSnooze()
                }
                UpcomingAlarmViewModel.State.NO_UPCOMING_ALARMS -> {
                    setDefaultActionCreate()
                }
            }
            fab_alarm_summary.show()
        })
    }

    fun onSuggestedActionClick(view: View) {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_main_content) as NavHostFragment
        val baseView = navHostFragment.childFragmentManager.fragments.first() as BaseView
        baseView.suggestedAction()
    }

    private fun setDefaultActionCreate() {
        fab_alarm_summary.hide()
        fab_alarm_summary.setImageResource(R.drawable.ic_baseline_add_24)
        fab_alarm_summary.show()
    }

    private fun setDefaultActionSnooze() {
        fab_alarm_summary.hide()
        fab_alarm_summary.setImageResource(R.drawable.ic_alarm_off_48px)
        fab_alarm_summary.show()
    }
}