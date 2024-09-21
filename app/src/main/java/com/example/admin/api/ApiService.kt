package com.example.admin.api

import com.example.admin.api.requests_responses.LoginRequest
import com.example.admin.api.requests_responses.LoginResponse
import com.example.admin.api.requests_responses.admin.getAdminData
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {


    //Unfinished
    @POST("login")
    fun signInUser(@Body request: LoginRequest): Call<LoginResponse>
    @POST("logout")
    fun logout(@Header("Authorization") authHeader: String): Call<Unit>
    @GET("admin")
    suspend fun getAdmin(): Response<List<getAdminData>>
}