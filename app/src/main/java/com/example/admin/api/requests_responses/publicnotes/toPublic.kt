package com.example.admin.api.requests_responses.publicnotes


data class UpdateNoteRequest(
    val public: Boolean,
    val to_public: Boolean
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