package com.vinsol.meetingscheduler.views.fragments.interfaces

import com.vinsol.meetingscheduler.views.fragments.controllers.ScheduleMeetingFragmentController

interface ScheduleMeetingClickEvents {

    fun showDatePicker(epoxyController: ScheduleMeetingFragmentController)
    fun showTimePicker(whichTime: String, epoxyController: ScheduleMeetingFragmentController)
    fun submitButtonOnClicked(epoxyController: ScheduleMeetingFragmentController, ifSlotExists: Boolean)
    suspend fun checkIfTimeSlotExists(epoxyController: ScheduleMeetingFragmentController)
    fun getUserDescriptionValue(description: String)

}