package com.dualtalk.helper

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class DateTimeHelper {
    companion object {
        fun timestampToDateTimeString(timestamp: Timestamp): String {
            val todayDate = Date()
            val todayCalendar = Calendar.getInstance()
            todayCalendar.time = todayDate

            val timestampDate = timestamp.toDate()
            val timestampCalendar = Calendar.getInstance()
            timestampCalendar.time = timestampDate

            var format = "dd/MM/yyyy HH:mm:ss"
            if (todayCalendar[Calendar.YEAR] == timestampCalendar[Calendar.YEAR] &&
                todayCalendar[Calendar.MONTH] == timestampCalendar[Calendar.MONTH] &&
                todayCalendar[Calendar.DATE] == timestampCalendar[Calendar.DATE]
            ) {
                format = "HH:mm:ss"
            }

            val dateFormat = SimpleDateFormat(format)
            return dateFormat.format(timestampDate)
        }

        fun getDateTimeNow(): String {
            val format = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.getDefault())
            val now = Date(System.currentTimeMillis())
            return format.format(now)
        }
    }
}