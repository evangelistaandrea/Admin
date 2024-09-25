package com.example.admin.api.requests_responses.publicnotes

class postPublicNotesData(
    val title: String,
    val creator: String,
    val contents: String,
    val public: Boolean,
)

class respondPostPublicNotes(
    val title: String,
    val creator: String,
    val contents: String,
    val public: Boolean,
)