package com.vinsol.meetingscheduler.data.room

import androidx.room.*
import com.vinsol.meetingscheduler.models.apiresponse.ApiResponseItem
import kotlinx.coroutines.flow.Flow

@Dao
interface MainDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertApiResponseItemIntoDb(apiResponseItem: ApiResponseItem)

    @Update
    suspend fun updateApiResponseItemIntoDb(apiResponseItem: ApiResponseItem)

    @Query("SELECT * FROM meeting_table")
    fun getAllApiResponseItemsFromDb(): Flow<List<ApiResponseItem>>

    @Query("DELETE FROM meeting_table")
    suspend fun deleteAllResponseItems()

}