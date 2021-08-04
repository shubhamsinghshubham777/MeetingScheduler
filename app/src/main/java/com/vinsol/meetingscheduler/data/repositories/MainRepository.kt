package com.vinsol.meetingscheduler.data.repositories

import android.util.Log
import com.vinsol.meetingscheduler.data.retrofit.ApiService
import com.vinsol.meetingscheduler.data.room.MainDao
import com.vinsol.meetingscheduler.models.apiresponse.ApiResponseItem
import kotlinx.coroutines.flow.Flow
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
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

    suspend fun getFlowOfApiResponseItemsFromDb(date: String?): Flow<List<ApiResponseItem>> {

        if (date == null) {
            val currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            currentDateTime?.let {
                Log.d(TAG, "Generated Date is: $it")
                getResponseFromApiAndInsertIntoDb(it)
            }
        } else {
            getResponseFromApiAndInsertIntoDb(date)
        }


        return dao.getAllApiResponseItemsFromDb()
    }

    private suspend fun getResponseFromApiAndInsertIntoDb(date: String) {
        apiService.getSchedule(date).forEach { apiResponseItem ->
            dao.insertApiResponseItemIntoDb(apiResponseItem)
        }
    }

}