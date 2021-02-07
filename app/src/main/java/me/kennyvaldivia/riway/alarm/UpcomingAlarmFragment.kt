package me.kennyvaldivia.riway.alarm

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.ActivityNavigator
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_upcoming_alarm.*
import me.kennyvaldivia.riway.BaseView
import me.kennyvaldivia.riway.MainActivity
import me.kennyvaldivia.riway.R
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 * Use the [UpcomingAlarmFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class UpcomingAlarmFragment : Fragment(), BaseView {

    private val stateToActionMap: Map<UpcomingAlarmViewModel.State, () -> Unit?> = mapOf(
        Pair(UpcomingAlarmViewModel.State.UPCOMING_ALARM_AVAILABLE, { snoozeAlarm() }),
        Pair(UpcomingAlarmViewModel.State.NO_UPCOMING_ALARMS, { createAlarm() })
    )

    private var state: MutableLiveData<UpcomingAlarmViewModel.State> =
        MutableLiveData(UpcomingAlarmViewModel.State.NO_UPCOMING_ALARMS)

    @Inject
    lateinit var viewModel: UpcomingAlarmViewModel

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment UpcomingAlarmFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            UpcomingAlarmFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upcoming_alarm, container, false)
    }

    override fun onResume() {
        super.onResume()
        viewLifecycleOwner?.let { viewModel.bind(it) }
        viewModel.alarmTime.observe(viewLifecycleOwner, Observer {
            if (it != null)
                tv_upcoming_alarm_time.text = it
        })
        viewModel.state.observe(viewLifecycleOwner, Observer {
            when (it) {
                UpcomingAlarmViewModel.State.UPCOMING_ALARM_AVAILABLE -> displaySummary()
                UpcomingAlarmViewModel.State.NO_UPCOMING_ALARMS -> displayHelp()
            }
        })
        state.observe(viewLifecycleOwner, Observer {
            when (it) {
                UpcomingAlarmViewModel.State.NO_UPCOMING_ALARMS -> displayHelp()
                UpcomingAlarmViewModel.State.UPCOMING_ALARM_AVAILABLE -> displaySummary()
            }
        })
    }

    private fun displayHelp() {
        view_help.visibility = View.VISIBLE
        view_upcoming_alarm_summary.visibility = View.GONE
    }

    private fun displaySummary() {
        view_help.visibility = View.GONE
        view_upcoming_alarm_summary.visibility = View.VISIBLE
    }

    override fun suggestedAction() {
        stateToActionMap[state.value]?.invoke()
    }

    override fun getActionButtonResourceId(): Int {
        TODO("Not yet implemented")
    }

    private fun snoozeAlarm() {
        viewModel.snoozeAlarm().observe(viewLifecycleOwner, Observer {
            val toast: Toast? =
                Toast.makeText(activity, R.string.snoozed_alarm_successfully, Toast.LENGTH_LONG)
            toast!!.show()
        })
    }

    private fun createAlarm() {
        val ctrl = findNavController()
        ctrl.navigate(
            UpcomingAlarmFragmentDirections.actionUpcomingAlarmFragmentToAlarmDetailActivity(1)
        )
    }
}