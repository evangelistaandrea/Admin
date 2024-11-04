package com.example.admin.api.requests_responses.publicnotes

import com.google.gson.annotations.SerializedName


data class UpdateNoteRequest(
    @SerializedName("is_public")
    val isPublic: Boolean,
    @SerializedName("to_public")
    val toPublic: Boolean
)

data class UpdateNoteResponse(
    val message: String,
    val note: Note
)

data class Note(
    val id: Int,
    val public: Boolean,
    val to_public: Boolean
)