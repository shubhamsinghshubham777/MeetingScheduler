package com.vinsol.meetingscheduler.views.fragments.controllers

import com.airbnb.epoxy.EpoxyController
import com.vinsol.meetingscheduler.R
import com.vinsol.meetingscheduler.databinding.ScheduleMeetingDescriptionItemBinding
import com.vinsol.meetingscheduler.databinding.ScheduleMeetingSelectableItemBinding
import com.vinsol.meetingscheduler.utils.ViewBindingKotlinModel

class ScheduleMeetingFragmentController: EpoxyController() {

    override fun buildModels() {
        MeetingSelectableItem("Meeting Date").id("meeting_date").addTo(this)
        MeetingSelectableItem("Start Time").id("start_time").addTo(this)
        MeetingSelectableItem("End Time").id("end_time").addTo(this)
        MeetingDescriptionItem().id("description_with_button").addTo(this)
    }

    data class MeetingSelectableItem(
        val hintText: String
    ): ViewBindingKotlinModel<ScheduleMeetingSelectableItemBinding>(R.layout.schedule_meeting_selectable_item) {
        override fun ScheduleMeetingSelectableItemBinding.bind() {
            meetingItemEt.apply {
                hint = hintText
                setOnClickListener {
                    //todo display date/time picker
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
}