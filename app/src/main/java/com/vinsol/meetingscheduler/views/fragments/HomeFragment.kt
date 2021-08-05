package com.vinsol.meetingscheduler.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import com.vinsol.meetingscheduler.R
import com.vinsol.meetingscheduler.databinding.FragmentHomeBinding
import com.vinsol.meetingscheduler.models.apiresponse.ApiResponseItem
import com.vinsol.meetingscheduler.views.fragments.controllers.HomeFragmentController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val binding: FragmentHomeBinding by viewBinding()

    companion object {
        private const val TAG = "HomeFragmentTAG"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.getFlowOfApiResponseItemsFromDb(null)

        val epoxyController = HomeFragmentController()
        binding.homeFragEpoxyRecyclerView.setController(epoxyController)
        binding.homeFragEpoxyRecyclerView.adapter = epoxyController.adapter

        mainViewModel.listOfApiResponseItems.observe(viewLifecycleOwner) {
            it?.let { listOfApiResponseItem ->
                epoxyController.listOfApiResponseItems = listOfApiResponseItem as ArrayList<ApiResponseItem>
            }
        }

        mainViewModel.loadingState.observe(viewLifecycleOwner) {
            epoxyController.isLoading = it
        }
    }

}