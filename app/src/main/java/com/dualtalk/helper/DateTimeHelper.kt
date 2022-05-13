package com.dualtalk.helper

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class DateTimeHelper {
    companion object {
        fun timestampToDateTimeString(timestamp: Timestamp): String {
            val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
            val date = timestamp.toDate()
            return simpleDateFormat.format(date)
        }

        fun getDateTimeNow(): String {
            val format = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.getDefault())
            val now = Date(System.currentTimeMillis())
            return format.format(now)
        }
    }
}