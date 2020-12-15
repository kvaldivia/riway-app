package me.kennyvaldivia.riway.alarm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import me.kennyvaldivia.riway.R
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 * Use the [UpcomingAlarmFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class UpcomingAlarmFragment : Fragment(), UpcomingAlarmContract.View {

    @Inject lateinit var presenter: UpcomingAlarmPresenter

    private var alarm: Alarm? = null
    private var helpView: View? = null
    private var summaryView: View? = null

    private val args: UpcomingAlarmFragmentArgs by navArgs()

    @Inject
    lateinit var alarmSummaryViewModelFactory: AlarmSummaryViewModel.AssistedFactory

    private val alarmSummaryViewModel: AlarmSummaryViewModel by viewModels {
        AlarmSummaryViewModel.provideFactory(
            alarmSummaryViewModelFactory,
            args.alarmId
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_upcoming_alarm, container, false)
        helpView = view.findViewById(R.id.view_help)
        summaryView = view.findViewById(R.id.view_upcoming_alarm_summary)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.bind(this, view.context, requireActivity().application)
        presenter.updateView()
    }



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

    override fun displayHelp() {
        helpView!!.visibility = View.VISIBLE
        summaryView!!.visibility = View.GONE
    }

    override fun displaySummary() {
        helpView!!.visibility = View.GONE
        summaryView!!.visibility = View.VISIBLE
    }

    override fun onClickCancel() {
        TODO("Not yet implemented")
    }

    override fun onClickDetails() {
        TODO("Not yet implemented")
    }

    override fun onClickSnooze() {
        TODO("Not yet implemented")
    }

    override fun updateView(alarm: Alarm?) {
        this.alarm = alarm?: this.alarm
        val tvAlarmTime = summaryView!!.findViewById<TextView>(R.id.tv_upcoming_alarm_time)
        // TODO: make this a template resource string
        if (alarm == null) {
            displayHelp()
            return
        }
        val text = "${alarm!!.hour}:${alarm!!.minute}"
        tvAlarmTime.text = text
        displaySummary()
    }

    override fun updateView() {
        updateView(alarm!!)
    }

    override fun getPresenter(): UpcomingAlarmContract.Presenter {
        return presenter
    }
}