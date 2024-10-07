package com.example.admin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.admin.api.ApiClient
import com.example.admin.api.ApiService
import com.example.admin.api.requests_responses.publicnotes.UpdateNoteRequest

import kotlinx.coroutines.launch
import retrofit2.HttpException

class View_Admin : AppCompatActivity() {
    private lateinit var tvtitle: TextView
    private lateinit var tvcontents: TextView
    private lateinit var ibthumbsup: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_admin)

        tvtitle = findViewById(R.id.view_tvtitle)
        tvcontents = findViewById(R.id.view_tvcontents)
        ibthumbsup = findViewById(R.id.thumbs_up)

        val intent = intent
        val title = intent.getStringExtra("title")
        val contents = intent.getStringExtra("contents")
        val creator = intent.getStringExtra("creator")
        val noteId = intent.getIntExtra("notesId", -1)  // Make sure the note ID is passed in the intent



        tvtitle.setText(title)
        tvcontents.setText(contents)

        ibthumbsup.setOnClickListener {
            if(noteId != -1){
                postPublicNotes(noteId)
            }

        }
    }

    private fun postPublicNotes(noteId: Int) {
        lifecycleScope.launch {
            try {
                Log.d("View_Admin_tester", "Updating note with ID: $noteId")
                // Prepare the request
                val apiService = ApiClient.retrofit.create(ApiService::class.java)
                val request = UpdateNoteRequest(public = true, to_public = false)  // Set desired values for public & to_public

                // Make the API call
                val response = apiService.updateNoteAsAdmin(noteId, request)

                if (response.isSuccessful) {
                    // Handle successful response
                    Toast.makeText(this@View_Admin, "Note updated successfully", Toast.LENGTH_LONG).show()
                    Log.d("View_Admin", "Note updated successfully: ${response.code()}")
                } else {
                    // Handle error response
                    Log.e("View_Admin", "Failed to update note: ${response.code()} - ${response.errorBody()?.string()}")
                    Toast.makeText(this@View_Admin, "Failed to update the note: ${response.code()}", Toast.LENGTH_LONG).show()
                }
            } catch (e: HttpException) {
                Log.e("View_Admin", "HTTP error: ${e.response()?.errorBody()?.string()}")
                Toast.makeText(this@View_Admin, "Failed to update the note", Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("View_Admin", "Error posting public notes: ${e.message}")
                Toast.makeText(this@View_Admin, "An error occurred", Toast.LENGTH_LONG).show()
            }
        }
    }

}