package com.example.admin

import com.google.gson.annotations.SerializedName

data class Data(
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
