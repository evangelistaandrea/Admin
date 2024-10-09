package com.example.admin.api

import com.example.admin.api.requests_responses.admin.getAdminData
import com.example.admin.api.requests_responses.publicnotes.UpdateNoteRequest
import com.example.admin.api.requests_responses.publicnotes.UpdateNoteResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {




    @GET("pending_notes")
    suspend fun getPendingNotes(): Response<List<getAdminData>>

    @PUT("adminUpdater/{noteId}")
    suspend fun updateNoteAsAdmin(
        @Path("noteId") noteId: Int,
        @Body request: UpdateNoteRequest
    ): Response<UpdateNoteResponse>
}