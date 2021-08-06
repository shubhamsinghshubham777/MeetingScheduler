package com.vinsol.meetingscheduler.data.room

import androidx.room.*
import com.vinsol.meetingscheduler.models.apiresponse.ApiResponseItemWithDate
import kotlinx.coroutines.flow.Flow

@Dao
interface MainDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertApiResponseItemWithDateIntoDb(apiResponseItemWithDate: ApiResponseItemWithDate)

    @Query("DELETE FROM apiresponseitem_with_date_table")
    suspend fun deleteAllResponseItemsWithDates()

    @Query("SELECT * FROM apiresponseitem_with_date_table WHERE :date=date")
    fun getItemsForSelectedDate(date: String): Flow<List<ApiResponseItemWithDate>>

    @Query("SELECT * FROM apiresponseitem_with_date_table WHERE :date=date LIMIT 1")
    suspend fun getSingleItemForSelectedDate(date: String): ApiResponseItemWithDate?

    @Update
    suspend fun updateApiResponseItemWithDateIntoDb(apiResponseItemWithDate: ApiResponseItemWithDate)

}