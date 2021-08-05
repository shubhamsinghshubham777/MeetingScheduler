package com.vinsol.meetingscheduler.views.fragments.controllers

import com.airbnb.epoxy.EpoxyController
import com.vinsol.meetingscheduler.R
import com.vinsol.meetingscheduler.databinding.ScheduleMeetingDescriptionItemBinding
import com.vinsol.meetingscheduler.databinding.ScheduleMeetingSelectableItemBinding
import com.vinsol.meetingscheduler.utils.ViewBindingKotlinModel
import com.vinsol.meetingscheduler.viewmodels.MainViewModel
import com.vinsol.meetingscheduler.views.fragments.interfaces.ScheduleMeetingClickEvents
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ScheduleMeetingFragmentController(
    private val mainViewModel: MainViewModel,
    private val scheduleMeetingClickEvents: ScheduleMeetingClickEvents
): EpoxyController() {

    override fun buildModels() {
        MeetingSelectableItem("Meeting Date", scheduleMeetingClickEvents, "meeting_date", mainViewModel).id("meeting_date").addTo(this)
        MeetingSelectableItem("Start Time", scheduleMeetingClickEvents, "start_time", mainViewModel).id("start_time").addTo(this)
        MeetingSelectableItem("End Time", scheduleMeetingClickEvents, "end_time", mainViewModel).id("end_time").addTo(this)
        MeetingDescriptionItem().id("description_with_button").addTo(this)
    }

    data class MeetingSelectableItem(
        val hintText: String,
        val scheduleMeetingClickEvents: ScheduleMeetingClickEvents,
        val modelId: String,
        val mainViewModel: MainViewModel
    ): ViewBindingKotlinModel<ScheduleMeetingSelectableItemBinding>(R.layout.schedule_meeting_selectable_item) {
        override fun ScheduleMeetingSelectableItemBinding.bind() {

            meetingItemEt.hint = hintText

            meetingItemEt.setOnClickListener {
                when(modelId) {
                    "meeting_date" -> {
                        scheduleMeetingClickEvents.showDatePicker()
                        CoroutineScope(Dispatchers.IO).launch {
                            mainViewModel.userSelectedDate.collectLatest {
                                withContext(Dispatchers.Main) {
                                    meetingItemEt.setText(it)
                                }
                            }
                        }
                    }
                    "start_time" -> {
                        scheduleMeetingClickEvents.showTimePicker("start_time")
                        CoroutineScope(Dispatchers.IO).launch {
                            mainViewModel.userSelectedStartTime.collectLatest {
                                withContext(Dispatchers.Main) {
                                    meetingItemEt.setText(it)
                                }
                            }
                        }
                    }
                    "end_time" -> {
                        scheduleMeetingClickEvents.showTimePicker("end_time")
                        CoroutineScope(Dispatchers.IO).launch {
                            mainViewModel.userSelectedEndTime.collectLatest {
                                withContext(Dispatchers.Main) {
                                    meetingItemEt.setText(it)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    class MeetingDescriptionItem: ViewBindingKotlinModel<ScheduleMeetingDescriptionItemBinding>(R.layout.schedule_meeting_description_item) {
        override fun ScheduleMeetingDescriptionItemBinding.bind() {
            meetingFragSubmitBtn.setOnClickListener {
                //todo submit values to DB
            }
        }

    }

    companion object {
        private const val TAG = "ScheduleMeetingFragment"
    }
}