package com.vinsol.meetingscheduler.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import org.joda.time.LocalDate
import java.text.SimpleDateFormat
import java.util.*

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

fun Context.shortSimpleToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Context.longSimpleToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}

fun String.fromTimeToInt() = replace(":", "").toInt()

fun Long.fromMillisToReadableDate(): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.US)
    return formatter.format(Date(this))
}

fun Long.fromMillisToLocalDate(): LocalDate {
    return LocalDate(this)
}

fun View.closeKeyboard(context: Context) {
    val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    requestFocus()
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}