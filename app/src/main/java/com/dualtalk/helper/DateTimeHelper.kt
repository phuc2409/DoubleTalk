package com.dualtalk.helper

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat

class DateTimeHelper {
    companion object {
        fun timestampToDateTimeString(timestamp: Timestamp): String {
            val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
            val date = timestamp.toDate()
            return simpleDateFormat.format(date)
        }
    }
}