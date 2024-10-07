package com.example.admin.api.requests_responses.admin

class getAdminData(
    val title: String,
    val creator_username: String,
    val contents: String,
    val public: Boolean,
    val notes_id: Int
)