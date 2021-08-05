package com.vinsol.meetingscheduler.utils

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import org.joda.time.LocalDate

object NullToEmptyStringAdapter {
    @FromJson
    fun fromJson(reader: JsonReader): String {
        if (reader.peek() != JsonReader.Token.NULL) {
            return reader.nextString()
        }
        reader.nextNull<Unit>()
        return ""
    }
}

fun LocalDate.toReadableDate(): String {
    return this.toString("dd/MM/yyyy")
}