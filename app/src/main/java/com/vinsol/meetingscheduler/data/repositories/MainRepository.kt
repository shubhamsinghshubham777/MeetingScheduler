package com.vinsol.meetingscheduler.data.repositories

import android.util.Log
import com.vinsol.meetingscheduler.data.retrofit.ApiService
import com.vinsol.meetingscheduler.data.room.MainDao
import com.vinsol.meetingscheduler.models.apiresponse.ApiResponseItem
import com.vinsol.meetingscheduler.models.apiresponse.ApiResponseItemWithDate
import com.vinsol.meetingscheduler.utils.toReadableDate
import kotlinx.coroutines.flow.Flow
import org.joda.time.DateTime
import org.joda.time.LocalDate
import javax.inject.Inject

class MainRepository
@Inject
constructor(
    private val dao: MainDao,
    private val apiService: ApiService
) {

    companion object {
        private const val TAG = "MainRepositoryTAG"
    }

    suspend fun getFlowOfApiResponseItemsFromDb(date: String?): Flow<List<ApiResponseItemWithDate>> {

        if (date == null) {
            val currentDateTime = getCurrentDate().toReadableDate()
            currentDateTime.let {
                Log.d(TAG, "Generated Date is: $it")
                getResponseFromApiAndInsertIntoDb(it)
            }
        } else {
            getResponseFromApiAndInsertIntoDb(date)
        }


        return dao.getApiResponseItemsWithDatesFromDb()
    }

    fun getCurrentDate(): LocalDate {
        val currentDate = DateTime().toLocalDate()
        Log.d(TAG, "getCurrentDate: $currentDate")
        return currentDate
    }

    fun incrementDate(localDate: LocalDate): LocalDate {
        return localDate.plusDays(1)
    }

    fun decrementDate(localDate: LocalDate): LocalDate {
        return localDate.minusDays(1)
    }

    private suspend fun getResponseFromApiAndInsertIntoDb(date: String) {
        dao.insertApiResponseItemWithDateIntoDb(ApiResponseItemWithDate(date, apiService.getSchedule(date)))
    }

}