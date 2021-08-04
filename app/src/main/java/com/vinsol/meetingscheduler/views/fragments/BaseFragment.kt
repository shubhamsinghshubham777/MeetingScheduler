package com.vinsol.meetingscheduler.views.fragments

import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewbinding.ViewBinding
import com.vinsol.meetingscheduler.R
import com.vinsol.meetingscheduler.viewmodels.MainViewModel
import com.vinsol.meetingscheduler.views.MainActivity

abstract class BaseFragment constructor(layout: Int) : Fragment(layout) {

    protected val mainActivity: MainActivity
        get() = activity as MainActivity

    protected val mainViewModel: MainViewModel by activityViewModels()

}