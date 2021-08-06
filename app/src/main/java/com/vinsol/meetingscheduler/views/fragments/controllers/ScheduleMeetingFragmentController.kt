package com.vinsol.meetingscheduler.views.fragments.controllers

import android.util.Log
import androidx.core.widget.doOnTextChanged
import com.airbnb.epoxy.EpoxyController
import com.vinsol.meetingscheduler.R
import com.vinsol.meetingscheduler.databinding.ScheduleMeetingDescriptionItemBinding
import com.vinsol.meetingscheduler.databinding.ScheduleMeetingSelectableItemBinding
import com.vinsol.meetingscheduler.utils.ViewBindingKotlinModel
import com.vinsol.meetingscheduler.views.fragments.interfaces.ScheduleMeetingClickEvents
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ScheduleMeetingFragmentController(
    private val scheduleMeetingClickEvents: ScheduleMeetingClickEvents
): EpoxyController() {

    var userPickedDate: String = String()
        set(value) {
            field = value
            if (field.isNotBlank()) {
                requestModelBuild()
            }
        }

    var userPickedStartTime: String = String()
        set(value) {
            field = value
            if (field.isNotBlank()) {
                requestModelBuild()
            }
        }

    var userPickedEndTime: String = String()
        set(value) {
            field = value
            if (field.isNotBlank()) {
                requestModelBuild()
            }
        }

    var userDescription: String = String()
        set(value) {
            field = value
            if (field.isNotBlank()) {
                Log.d(TAG, "UserDescription is changed!")
            }
        }

    override fun buildModels() {
        MeetingSelectableItem("Meeting Date", scheduleMeetingClickEvents, "meeting_date", userPickedDate, this).id("meeting_date").addTo(this)
        MeetingSelectableItem("Start Time", scheduleMeetingClickEvents, "start_time", userPickedStartTime, this).id("start_time").addTo(this)
        MeetingSelectableItem("End Time", scheduleMeetingClickEvents, "end_time", userPickedEndTime, this).id("end_time").addTo(this)
        MeetingDescriptionItem(userDescription, scheduleMeetingClickEvents, this).id("description_with_button").addTo(this)
    }

    data class MeetingSelectableItem(
        val hintText: String,
        val scheduleMeetingClickEvents: ScheduleMeetingClickEvents,
        val modelId: String,
        val userPickedValue: String,
        val scheduleMeetingFragmentController: ScheduleMeetingFragmentController
    ): ViewBindingKotlinModel<ScheduleMeetingSelectableItemBinding>(R.layout.schedule_meeting_selectable_item) {
        override fun ScheduleMeetingSelectableItemBinding.bind() {
            meetingItemEt.apply {
                hint = hintText
                setText(userPickedValue)
                setOnClickListener {
                    when(modelId) {
                        "meeting_date" -> {
                            scheduleMeetingClickEvents.showDatePicker(scheduleMeetingFragmentController)
                        }
                        "start_time" -> {
                            scheduleMeetingClickEvents.showTimePicker("start_time", scheduleMeetingFragmentController)
                        }
                        "end_time" -> {
                            scheduleMeetingClickEvents.showTimePicker("end_time", scheduleMeetingFragmentController)
                        }
                    }
                }
            }
        }
    }

    data class MeetingDescriptionItem(
        private var userDescription: String,
        private val scheduleMeetingClickEvents: ScheduleMeetingClickEvents,
        private val scheduleMeetingFragmentController: ScheduleMeetingFragmentController
    ) : ViewBindingKotlinModel<ScheduleMeetingDescriptionItemBinding>(R.layout.schedule_meeting_description_item) {
        override fun ScheduleMeetingDescriptionItemBinding.bind() {
            meetingFragSubmitBtn.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    scheduleMeetingClickEvents.checkIfTimeSlotExists(scheduleMeetingFragmentController)
                }
            }

            meetingFragDescriptionEt.doOnTextChanged { text, _, _, _ ->
                userDescription = text.toString()
                scheduleMeetingClickEvents.getUserDescriptionValue(userDescription)
            }
        }

    }

    companion object {
        private const val TAG = "ScheduleMeetingFragment"
    }
}