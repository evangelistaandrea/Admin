package com.example.admin.api.requests_responses

data class LoginResponse(
    val error: String,
    val message: String,
    val token: String
)
