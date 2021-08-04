package com.vinsol.meetingscheduler.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vinsol.meetingscheduler.models.apiresponse.ApiResponseConverters
import com.vinsol.meetingscheduler.models.apiresponse.ApiResponseItem

@Database(entities = [ApiResponseItem::class], version = 1, exportSchema = true)
@TypeConverters(
    ApiResponseConverters::class
)
abstract class MainDatabase: RoomDatabase() {

    abstract fun getDao(): MainDao

}