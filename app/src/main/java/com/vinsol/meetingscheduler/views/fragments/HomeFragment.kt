package com.vinsol.meetingscheduler.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import com.vinsol.meetingscheduler.R
import com.vinsol.meetingscheduler.databinding.FragmentHomeBinding
import com.vinsol.meetingscheduler.models.apiresponse.ApiResponseItem
import com.vinsol.meetingscheduler.models.apiresponse.ApiResponseItemWithDate
import com.vinsol.meetingscheduler.utils.toReadableDate
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

        mainViewModel.apply {
            getFlowOfApiResponseItemsFromDb(null)

            val epoxyController = HomeFragmentController()

            binding.apply {
                homeFragEpoxyRecyclerView.setController(epoxyController)
                homeFragEpoxyRecyclerView.adapter = epoxyController.adapter

                currentLocalDate.observe(viewLifecycleOwner) {
                    it?.let { currentDate ->
                        homeFragTopBarBackBtnRightIv.setOnClickListener {
                            Log.d(TAG, "on next button pressed: ${incrementDate(currentDate)}")
                        }
                        homeFragTopBarBackBtnLeftIv.setOnClickListener {
                            Log.d(TAG, "on previous button pressed: ${decrementDate(currentDate)}")
                        }
                    }

                    homeFragTopBarDateTv.text = it.toReadableDate()
                }
            }

            listOfApiResponseItems.observe(viewLifecycleOwner) {
                it?.let { listOfApiResponseItem ->
                    epoxyController.listOfApiResponseItems = listOfApiResponseItem as ArrayList<ApiResponseItemWithDate>
                }
            }

            loadingState.observe(viewLifecycleOwner) {
                epoxyController.isLoading = it
            }

            //Initialise currentLocalDate LiveData
            getCurrentDate()

            currentLocalDate.observe(viewLifecycleOwner) {

            }
        }
    }

}