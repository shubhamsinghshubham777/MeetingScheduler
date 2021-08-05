package com.vinsol.meetingscheduler.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vinsol.meetingscheduler.data.repositories.MainRepository
import com.vinsol.meetingscheduler.models.apiresponse.ApiResponseItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    application: Application,
    private val repository: MainRepository
): AndroidViewModel(application) {

    private val _listOfApiResponseItems = MutableLiveData<List<ApiResponseItem>>()
    val listOfApiResponseItems: LiveData<List<ApiResponseItem>>
        get() = _listOfApiResponseItems

    private val _loadingState = MutableLiveData(true)
    val loadingState: LiveData<Boolean>
        get() = _loadingState

    fun getFlowOfApiResponseItemsFromDb(date: String?) {

        _loadingState.postValue(true)

        viewModelScope.launch {
            repository.getFlowOfApiResponseItemsFromDb(date).collectLatest {
                _listOfApiResponseItems.postValue(it)
                _loadingState.postValue(false)
            }
        }
    }

}