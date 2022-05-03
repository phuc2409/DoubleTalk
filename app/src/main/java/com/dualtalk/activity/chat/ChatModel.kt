package com.dualtalk.activity.chat

import com.google.firebase.Timestamp

data class ChatModel(
    val id: String,
    val participantIds: ArrayList<String>,
    val participantNames: ArrayList<String>,
    val latestMessage: String? = null,
    val updatedAt: Timestamp? = null
) {
    override fun toString(): String {
        return "id: $id, participantIds: $participantIds, participantNames: $participantNames, latestMessage: $latestMessage, updatedAt: $updatedAt"
    }
}