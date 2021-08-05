package com.vinsol.meetingscheduler.views.fragments

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.vinsol.meetingscheduler.R
import com.vinsol.meetingscheduler.databinding.FragmentScheduleMeetingBinding
import com.vinsol.meetingscheduler.views.fragments.controllers.ScheduleMeetingFragmentController
import com.vinsol.meetingscheduler.views.fragments.interfaces.ScheduleMeetingClickEvents
import java.text.SimpleDateFormat
import java.util.*

class ScheduleMeetingFragment : BaseFragment(R.layout.fragment_schedule_meeting), ScheduleMeetingClickEvents {

    private val binding: FragmentScheduleMeetingBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAllViews(findNavController(), binding)
    }

    private fun setupAllViews(navController: NavController, binding: FragmentScheduleMeetingBinding) {
        binding.apply {
            meetingFragBackBtnIv.setOnClickListener {
                navController.navigateUp()
            }
            meetingFragBackBtnTv.setOnClickListener {
                meetingFragBackBtnIv.performClick()
            }
            val epoxyController = ScheduleMeetingFragmentController(mainViewModel,this@ScheduleMeetingFragment)
            meetingFragEpoxyRecyclerView.apply {
                setController(epoxyController)
                adapter = epoxyController.adapter
                epoxyController.requestModelBuild()
            }
        }
    }

    override fun showDatePicker(){

        val tag = "MaterialDatePicker"

        val constraints = CalendarConstraints.Builder()
            .setValidator(DateValidatorPointForward.now())
            .build()

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select meeting date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setCalendarConstraints(constraints)
            .build()

        datePicker.show(childFragmentManager, tag)

        datePicker.addOnPositiveButtonClickListener {
            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.US)
            lifecycleScope.launchWhenResumed {
                mainViewModel.userSelectedDate.emit(formatter.format(Date(it)))
            }
        }
    }

    override fun showTimePicker(whichTime: String) {

        val tag = "MaterialTimePicker"

        val calendar = Calendar.getInstance()
        val currentHour = calendar[Calendar.HOUR_OF_DAY]
        val currentMinute = calendar[Calendar.MINUTE]
        val titleText = if (whichTime == "start_time") "Select start time" else "Select end time"

        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(currentHour)
            .setMinute(currentMinute)
            .setTitleText(titleText)
            .build()

        picker.show(childFragmentManager, tag)

        picker.addOnPositiveButtonClickListener {
            lifecycleScope.launchWhenResumed {

                val pickedTime = "${picker.hour}:${picker.minute}"

                when (whichTime) {
                    "start_time" -> {
                        mainViewModel.userSelectedStartTime.emit(pickedTime)
                    }
                    "end_time" -> {
                        mainViewModel.userSelectedEndTime.emit(pickedTime)
                    }
                }
            }
        }
    }

    override fun submitButtonOnClicked(navController: NavController) {
        //todo implement this
    }

}