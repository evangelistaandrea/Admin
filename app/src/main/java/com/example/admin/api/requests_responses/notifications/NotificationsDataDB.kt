package com.example.admin.api.requests_responses.notifications

import com.google.gson.annotations.SerializedName


data class PostNotificationDB (
    @SerializedName("notes_id")
    val notesId: Int,
    val email: String,
    val message: String
)

data class ResponseNotificationDB(
    @SerializedName("notes_id")
    val notesId: Int,
    @SerializedName("notes_title")
    val notesTitle: String,
    @SerializedName("notification_type")
    val notificationType: String,
    val email: String,
    val message: String,
    val userId: String
)