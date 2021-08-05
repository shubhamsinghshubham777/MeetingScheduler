package com.vinsol.meetingscheduler.views.fragments

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.vinsol.meetingscheduler.R
import com.vinsol.meetingscheduler.databinding.FragmentScheduleMeetingBinding
import com.vinsol.meetingscheduler.views.fragments.controllers.ScheduleMeetingFragmentController

class ScheduleMeetingFragment : BaseFragment(R.layout.fragment_schedule_meeting) {

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
            val epoxyController = ScheduleMeetingFragmentController()
            meetingFragEpoxyRecyclerView.apply {
                setController(epoxyController)
                adapter = epoxyController.adapter
                epoxyController.requestModelBuild()
            }
        }
    }

}