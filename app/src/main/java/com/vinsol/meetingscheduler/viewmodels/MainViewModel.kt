package com.vinsol.meetingscheduler.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vinsol.meetingscheduler.data.repositories.MainRepository
import com.vinsol.meetingscheduler.models.apiresponse.ApiResponseItem
import com.vinsol.meetingscheduler.models.apiresponse.ApiResponseItemWithDate
import com.vinsol.meetingscheduler.utils.toReadableDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.joda.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    application: Application,
    private val repository: MainRepository
): AndroidViewModel(application) {

    private val _listOfApiResponseItems = MutableLiveData<List<ApiResponseItemWithDate>>()
    val listOfApiResponseItems: LiveData<List<ApiResponseItemWithDate>>
        get() = _listOfApiResponseItems

    private val _loadingState = MutableLiveData(true)
    val loadingState: LiveData<Boolean>
        get() = _loadingState

    private val _currentLocalDate = MutableLiveData<LocalDate>()
    val currentLocalDate: LiveData<LocalDate>
        get() = _currentLocalDate

    fun getFlowOfApiResponseItemsFromDb(date: String?) {

        _loadingState.postValue(true)

        viewModelScope.launch {
            repository.getFlowOfApiResponseItemsFromDb(date).collectLatest {
                _listOfApiResponseItems.postValue(it)
                _loadingState.postValue(false)
            }
        }
    }

    fun getCurrentDate() : LocalDate {
        val currentLocalDate = repository.getCurrentDate()
        Log.d(TAG, "currentLocalDate: $currentLocalDate")
        _currentLocalDate.postValue(currentLocalDate)
        return currentLocalDate
    }

    fun incrementDate(localDate: LocalDate): String {
        val incrementedDate = repository.incrementDate(localDate)
        _currentLocalDate.postValue(incrementedDate)
        return incrementedDate.toReadableDate()
    }

    fun decrementDate(localDate: LocalDate): String {
        val decrementedDate = repository.decrementDate(localDate)
        _currentLocalDate.postValue(decrementedDate)
        return decrementedDate.toReadableDate()
    }

    companion object {
        private const val TAG = "MainViewModel"
    }

}