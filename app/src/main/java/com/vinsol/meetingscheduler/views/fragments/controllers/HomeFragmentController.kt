package com.vinsol.meetingscheduler.views.fragments.controllers

import android.annotation.SuppressLint
import com.airbnb.epoxy.EpoxyController
import com.vinsol.meetingscheduler.R
import com.vinsol.meetingscheduler.databinding.ItemApiresponseBinding
import com.vinsol.meetingscheduler.databinding.NoItemsScreenBinding
import com.vinsol.meetingscheduler.models.apiresponse.ApiResponseItemWithDate
import com.vinsol.meetingscheduler.utils.ViewBindingKotlinModel
import com.vinsol.meetingscheduler.views.common.LoadingScreenModel

class HomeFragmentController: EpoxyController() {

    companion object {
        private const val TAG = "HomeFragmentController"
    }

    var isLoading: Boolean = true
        set(value) {
            field = value
            if (field) {
                requestModelBuild()
            }
        }

    var listOfApiResponseItems = ArrayList<ApiResponseItemWithDate>()
        set(value) {
            field = value
            isLoading = false
            requestModelBuild()
        }

    override fun buildModels() {

        if (isLoading) {
            LoadingScreenModel().id("loading_state").addTo(this)
            return
        }

        listOfApiResponseItems.forEach {

            if (it.apiResponseItem.isNullOrEmpty()) {
                NoItemFoundModel().id("no_item").addTo(this)
                return
            }

            it.apiResponseItem.sortedBy { item ->
                item.startTime.replace(":", "").toInt()
            }.forEach { apiResponseItem ->
                ItemApiResponseModel(apiResponseItem.startTime, apiResponseItem.endTime, apiResponseItem.description, apiResponseItem.participants).id(it.date).addTo(this)
            }
        }
    }

    data class ItemApiResponseModel(
        val startTime: String,
        val endTime: String,
        val description: String,
        val participants: List<String>
    ): ViewBindingKotlinModel<ItemApiresponseBinding>(R.layout.item_apiresponse) {
        @SuppressLint("SetTextI18n")
        override fun ItemApiresponseBinding.bind() {

            val indexOfStartTimeColon = startTime.indexOf(":")
            val indexOfEndTimeColon = endTime.indexOf(":")
            val startTimeAmOrPm = if (startTime.substring(0,indexOfStartTimeColon).toInt() < 12) { "AM" } else { "PM" }
            val endTimeAmOrPm = if (endTime.substring(0,indexOfEndTimeColon).toInt() < 12) { "AM" } else { "PM" }

            val stringOfParticipants = participants.joinToString { it }

            itemStartTimeTv.text = "$startTime$startTimeAmOrPm"
            itemEndTimeTv.text = "$endTime$endTimeAmOrPm"
            itemDescription.text = description
            itemParticipants?.text = stringOfParticipants
        }

    }

    class NoItemFoundModel: ViewBindingKotlinModel<NoItemsScreenBinding>(R.layout.no_items_screen) {
        override fun NoItemsScreenBinding.bind() {
            // nothing to do
        }
    }
}