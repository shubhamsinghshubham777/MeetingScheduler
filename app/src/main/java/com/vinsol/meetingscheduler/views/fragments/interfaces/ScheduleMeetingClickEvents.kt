package com.vinsol.meetingscheduler.views.fragments.interfaces

import androidx.navigation.NavController

interface ScheduleMeetingClickEvents {

    fun showDatePicker()
    fun showTimePicker(whichTime: String)
    fun submitButtonOnClicked(navController: NavController)

}