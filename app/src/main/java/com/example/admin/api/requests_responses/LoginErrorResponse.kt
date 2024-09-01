package com.example.admin.api.requests_responses

import com.google.gson.annotations.SerializedName

data class LoginErrorResponse(

	@field:SerializedName("error")
	val error: String? = null
)
