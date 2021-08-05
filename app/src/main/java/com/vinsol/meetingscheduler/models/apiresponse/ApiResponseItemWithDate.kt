package com.vinsol.meetingscheduler.models.apiresponse

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.vinsol.meetingscheduler.utils.NullToEmptyStringAdapter

@Entity(tableName = "apiResponseItem_with_date_table")
data class ApiResponseItemWithDate(
    @PrimaryKey(autoGenerate = false)
    val date: String,
    val apiResponseItem: List<ApiResponseItem>
)

class ApiResponseWithDateConverters {
    private val moshi = Moshi.Builder()
        .add(NullToEmptyStringAdapter)
        .add(KotlinJsonAdapterFactory())
        .build()!!

    private val listOfApiResponseItems = Types.newParameterizedType(List::class.java, ApiResponseItem::class.java)
    private val jsonAdapterForListOfApiResponseItems = moshi.adapter<List<ApiResponseItem>>(listOfApiResponseItems)

    @TypeConverter
    @ToJson fun fromListOfApiResponseItemsToJson(listOfApiResponseItems: List<ApiResponseItem>): String {
        return jsonAdapterForListOfApiResponseItems.toJson(listOfApiResponseItems)
    }

    @TypeConverter
    @FromJson fun toListOfApiResponseItemsFromJson(json: String): List<ApiResponseItem> {
        return jsonAdapterForListOfApiResponseItems.fromJson(json)!!
    }
}
