package com.dualtalk.activity.chat

import com.google.firebase.Timestamp
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class MessageModel(
    val id: String,
    val chatId: String? = null,
    val sendId: String? = null,
    val message: String? = null,
    val createdAt: Timestamp? = null
) {
    override fun toString(): String {
        return "sendId: $sendId, chatId: $chatId, message: $message, createdAt: $createdAt"
    }
}