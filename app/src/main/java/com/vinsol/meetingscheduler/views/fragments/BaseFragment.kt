package com.vinsol.meetingscheduler.views.fragments

import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewbinding.ViewBinding
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.vinsol.meetingscheduler.R
import com.vinsol.meetingscheduler.viewmodels.MainViewModel
import com.vinsol.meetingscheduler.views.MainActivity

abstract class BaseFragment constructor(layout: Int) : Fragment(layout) {

    protected val mainActivity: MainActivity
        get() = activity as MainActivity

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