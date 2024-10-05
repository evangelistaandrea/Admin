package com.example.admin.api

import com.example.admin.api.requests_responses.LoginRequest
import com.example.admin.api.requests_responses.LoginResponse
import com.example.admin.api.requests_responses.admin.UpdateAdminRequest
import com.example.admin.api.requests_responses.admin.UpdateAdminResponse
import com.example.admin.api.requests_responses.admin.getAdminData
import com.example.admin.api.requests_responses.publicnotes.postPublicNotesData
import com.example.admin.api.requests_responses.publicnotes.respondPostPublicNotes
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {


    //Unfinished
    @POST("login")
    fun signInUser(@Body request: LoginRequest): Call<LoginResponse>
    @POST("logout")
    fun logout(@Header("Authorization") authHeader: String): Call<Unit>
    @GET("pending_notes")
    suspend fun getAdmin(): Response<List<getAdminData>>
    @POST("public_notes")
    suspend fun postPublicNotes(@Body request: postPublicNotesData): Response<respondPostPublicNotes>
    @PUT("admin/{id}")
    suspend fun updateAdmin(@Path("id") id: Int, @Body request: UpdateAdminRequest): Response<UpdateAdminResponse>

}