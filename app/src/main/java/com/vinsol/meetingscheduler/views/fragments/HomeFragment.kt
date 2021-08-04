package com.vinsol.meetingscheduler.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.vinsol.meetingscheduler.R
import com.vinsol.meetingscheduler.databinding.FragmentHomeBinding
import com.vinsol.meetingscheduler.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val binding: FragmentHomeBinding by viewBinding()

    companion object {
        private const val TAG = "HomeFragmentTAG"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.getFlowOfApiResponseItemsFromDb(null)

        mainViewModel.listOfApiResponseItems.observe(viewLifecycleOwner) {
            it?.let { listOfApiResponseItem ->
                listOfApiResponseItem.forEachIndexed { index, apiResponseItem ->
                    Log.d(TAG, "Item$index is $apiResponseItem")
                }
            }
        }
    }

}