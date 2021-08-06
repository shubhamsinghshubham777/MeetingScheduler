package com.vinsol.meetingscheduler.views.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.vinsol.meetingscheduler.viewmodels.MainViewModel

abstract class BaseFragment constructor(layout: Int) : Fragment(layout) {

    protected val mainViewModel: MainViewModel by activityViewModels()

    protected val myTag = "MaterialDatePicker"

    private val constraints = CalendarConstraints.Builder()
        .setValidator(DateValidatorPointForward.now())
        .build()

    val datePicker = MaterialDatePicker.Builder.datePicker()
        .setTitleText("Select meeting date")
        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
        .setCalendarConstraints(constraints)
        .build()

}