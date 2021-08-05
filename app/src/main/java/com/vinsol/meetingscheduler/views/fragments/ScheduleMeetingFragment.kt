package com.vinsol.meetingscheduler.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.airbnb.epoxy.EpoxyController
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.vinsol.meetingscheduler.R
import com.vinsol.meetingscheduler.databinding.FragmentScheduleMeetingBinding
import com.vinsol.meetingscheduler.utils.fromTimeToInt
import com.vinsol.meetingscheduler.utils.shortSimpleToast
import com.vinsol.meetingscheduler.views.fragments.controllers.ScheduleMeetingFragmentController
import com.vinsol.meetingscheduler.views.fragments.interfaces.ScheduleMeetingClickEvents
import kotlinx.coroutines.flow.collectLatest
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
            val epoxyController = ScheduleMeetingFragmentController(this@ScheduleMeetingFragment)
            meetingFragEpoxyRecyclerView.apply {
                setController(epoxyController)
                adapter = epoxyController.adapter
                epoxyController.requestModelBuild()
            }
        }
    }

    override fun showDatePicker(epoxyController: ScheduleMeetingFragmentController){

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
                epoxyController.userPickedDate = formatter.format(Date(it))
            }
        }
    }

    override fun showTimePicker(whichTime: String, epoxyController: ScheduleMeetingFragmentController) {

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

                val pickedHour = if (picker.hour < 10) "0${picker.hour}" else picker.hour
                val pickedMinute = if (picker.minute < 10) "0${picker.minute}" else picker.minute
                val pickedTime = "${pickedHour}:${pickedMinute}"

                when (whichTime) {
                    "start_time" -> {
                        epoxyController.userPickedStartTime = pickedTime
                    }
                    "end_time" -> {
                        epoxyController.userPickedEndTime = pickedTime
                    }
                }
            }
        }
    }

    override fun submitButtonOnClicked(epoxyController: ScheduleMeetingFragmentController) {
        lifecycleScope.launchWhenResumed {
            checkIfTimeSlotExists(epoxyController)?.let {
                if (it) {
                    requireActivity().shortSimpleToast("Slot is available!")
                } else {
                    requireActivity().shortSimpleToast("Slot not available!")
                }
            }
        }
    }

    override suspend fun checkIfTimeSlotExists(epoxyController: ScheduleMeetingFragmentController): Boolean? {

        mainViewModel.getSingleItemForSelectedDate(epoxyController.userPickedDate).apiResponseItem.forEach { item ->
            val userPickedStartTimeInt = epoxyController.userPickedStartTime.fromTimeToInt()
            val userPickedEndTimeInt = epoxyController.userPickedEndTime.fromTimeToInt()
            val itemStartTimeInt = item.startTime.fromTimeToInt()
            val itemEndTimeInt = item.endTime.fromTimeToInt()

            Log.d(TAG, "checkIfTimeSlotExists: $userPickedStartTimeInt\t$userPickedEndTimeInt\t$itemStartTimeInt\t$itemEndTimeInt")

            return !(userPickedStartTimeInt in itemStartTimeInt until(itemEndTimeInt) || userPickedEndTimeInt in itemStartTimeInt until(itemEndTimeInt))
        }

        return null
    }

    companion object {
        private const val TAG = "ScheduleMeetingFragment"
    }
}