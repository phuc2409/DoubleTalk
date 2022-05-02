package com.dualtalk.activity.chat

import com.google.firebase.Timestamp
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class MessageModel(
    val sendId: Long? = null,
    val receiveId: Long? = null,
    val message: String? = null,
    val createdAt: Timestamp? = null
) {
    override fun toString(): String {
        return "sendId: $sendId, receiveId: $receiveId, message: $message, createdAt: $createdAt"
    }
}