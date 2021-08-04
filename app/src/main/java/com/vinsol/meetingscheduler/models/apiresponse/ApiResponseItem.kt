package com.vinsol.meetingscheduler.models.apiresponse


import androidx.room.Entity
import androidx.room.TypeConverter
import com.squareup.moshi.*
import javax.inject.Inject

@Entity(tableName = "meeting_table")
@JsonClass(generateAdapter = true)
data class ApiResponseItem(
    @Json(name = "description")
    val description: String,
    @Json(name = "end_time")
    val endTime: String,
    @Json(name = "participants")
    val participants: List<String>,
    @Json(name = "start_time")
    val startTime: String
)

class ApiResponseConverters
@Inject
constructor(
    moshi: Moshi
) {

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