package com.example.admin.api.requests_responses.admin

import com.google.gson.annotations.SerializedName

class getAdminData(
    @SerializedName("notes_id")
    val notesId: Int,
    @SerializedName("user_id")
    val userId: Int,
    val title: String,
    @SerializedName("creator_username")
    val creatorUsername: String,
    @SerializedName("creator_email")
    val creatorEmail: String,
    val contents: String,
    val public: Boolean,
    @SerializedName("to_public")
    val toPublic: Boolean,
    @SerializedName("created_at")
    val createdAt: String
)