package com.vinsol.meetingscheduler.views.fragments

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.navigation.fragment.findNavController
import com.vinsol.meetingscheduler.R
import com.vinsol.meetingscheduler.databinding.FragmentHomeBinding
import com.vinsol.meetingscheduler.models.apiresponse.ApiResponseItemWithDate
import com.vinsol.meetingscheduler.utils.fromMillisToLocalDate
import com.vinsol.meetingscheduler.utils.fromMillisToReadableDate
import com.vinsol.meetingscheduler.utils.toReadableDate
import com.vinsol.meetingscheduler.viewmodels.MainViewModel
import com.vinsol.meetingscheduler.views.fragments.controllers.HomeFragmentController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val binding: FragmentHomeBinding by viewBinding()

    companion object {
        private const val TAG = "HomeFragmentTAG"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.apply {

            currentLocalDate.value?.let {
                getItemsForSelectedDate(it.toReadableDate())
            } ?: getItemsForSelectedDate(getCurrentDate().toReadableDate())

            val epoxyController = HomeFragmentController()

            binding.apply {
                homeFragEpoxyRecyclerView.setController(epoxyController)
                homeFragEpoxyRecyclerView.adapter = epoxyController.adapter
                setupTopBar(mainViewModel, binding)
                homeFragScheduleBtn.setOnClickListener {
                    val action = HomeFragmentDirections.actionHomeFragmentToScheduleMeetingFragment()
                    findNavController().navigate(action)
                }
                homeFragTopBarDateTv.setOnClickListener {
                    datePicker.show(childFragmentManager, myTag)
                    datePicker.addOnPositiveButtonClickListener {
                        setCustomDate(it.fromMillisToLocalDate())
                        getItemsForSelectedDate(it.fromMillisToReadableDate())
                    }
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

            currentLocalDate.observe(viewLifecycleOwner) {

            }
        }
    }

    private fun setupTopBar(
        mainViewModel: MainViewModel,
        fragmentHomeBinding: FragmentHomeBinding
    ) {
        fragmentHomeBinding.apply {
            mainViewModel.apply {
                currentLocalDate.observe(viewLifecycleOwner) {
                    it?.let { currentDate ->
                        homeFragTopBarBackBtnRightIv.setOnClickListener {
                            incrementDate(currentDate)
                        }
                        homeFragTopBarNextTv.setOnClickListener {
                            homeFragTopBarBackBtnRightIv.performClick()
                        }
                        homeFragTopBarBackBtnLeftIv.setOnClickListener {
                            decrementDate(currentDate)
                        }
                        homeFragTopBarPrevTv.setOnClickListener {
                            homeFragTopBarBackBtnLeftIv.performClick()
                        }
                    }
                    homeFragTopBarDateTv.text = it.toReadableDate()
                }
            }
        }
    }

}