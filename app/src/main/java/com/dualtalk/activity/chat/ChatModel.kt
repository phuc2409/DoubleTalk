package com.dualtalk.activity.chat

import com.google.firebase.Timestamp

data class ChatModel(
    var id: String,
    val participantIds: ArrayList<String>,
    val participantNames: ArrayList<String>,
    val sendId: String? = null,
    val sendName: String? = null,
    val latestMessage: String? = null,
    val updatedAt: Timestamp? = null
) {
    override fun toString(): String {
        return "id: $id, participantIds: $participantIds, participantNames: $participantNames, sendId: $sendId, sendName: $sendName, latestMessage: $latestMessage, updatedAt: $updatedAt"
    }
}