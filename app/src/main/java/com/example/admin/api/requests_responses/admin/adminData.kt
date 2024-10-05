package com.example.admin.api.requests_responses.admin

import com.google.gson.annotations.SerializedName

class getAdminData(
    @SerializedName("id")
    val adminId: Int,
    @SerializedName("notes_id")
    val notesId: Int,
    val title: String,
    @SerializedName("creator_username")
    val creatorUsername: String,
    @SerializedName("creator_email")
    val creatorEmail: String,
    val contents: String,
    val public: Boolean,
)

class UpdateAdminRequest(
    val title: String,
    @SerializedName("creator_username")
    val creatorUsername: String,
    @SerializedName("creator_email")
    val creatorEmail: String,
    val contents: String,
    val public: Boolean,
)

class UpdateAdminResponse(
    val id: Int,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("notes_id")
    val notesId: Int,
    val title: String,
    @SerializedName("creator_username")
    val creatorUsername: String,
    @SerializedName("creator_email")
    val creatorEmail: String,
    val contents: String,
    val public: Boolean
)