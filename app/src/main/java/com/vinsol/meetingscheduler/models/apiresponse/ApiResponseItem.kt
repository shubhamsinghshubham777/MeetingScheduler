package com.vinsol.meetingscheduler.models.apiresponse


import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.squareup.moshi.*
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.vinsol.meetingscheduler.utils.NullToEmptyStringAdapter
import javax.inject.Inject

@Entity(tableName = "meeting_table")
@JsonClass(generateAdapter = true)
data class ApiResponseItem(
    @PrimaryKey
    @Json(name = "description")
    val description: String,
    @Json(name = "end_time")
    val endTime: String,
    @Json(name = "participants")
    val participants: List<String>,
    @Json(name = "start_time")
    val startTime: String
)

class ApiResponseConverters {

    private val moshi = Moshi.Builder()
        .add(NullToEmptyStringAdapter)
        .add(KotlinJsonAdapterFactory())
        .build()!!

    private val listOfParticipants = Types.newParameterizedType(List::class.java, String::class.java)
    private val jsonAdapterForListOfParticipants = moshi.adapter<List<String>>(listOfParticipants)

    @TypeConverter
    @ToJson fun fromListOfParticipantsToJson(participants: List<String>): String {
        return jsonAdapterForListOfParticipants.toJson(participants)
    }

    @TypeConverter
    @FromJson fun fromJsonToListOfParticipants(json: String): List<String> {
        return jsonAdapterForListOfParticipants.fromJson(json)!!
    }

}