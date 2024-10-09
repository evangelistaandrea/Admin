package com.example.admin

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
        val creatorEmail = intent.getStringExtra("creator_email")
        val creatorUsername =  intent.getStringExtra("creator_username")
        val contents = intent.getStringExtra("contents")
        val createdAt = intent.getStringExtra("created_at")
        val public = intent.getBooleanExtra("public", false)
        val toPublic = intent.getBooleanExtra("to_public", false) ?: false
        val noteId = intent.getIntExtra("notes_id", -1)  // Make sure the note ID is passed in the intent
        val userId = intent.getIntExtra("user_id", -1)


        tvtitle.setText(title)
        tvcontents.setText(contents)

        ibthumbsup.setOnClickListener {
            Log.d("View_Admin", "Note ID: $noteId")
            if(noteId != -1){
                noteApprovedToBePublic(noteId)
            }

        }
    }

    private fun noteApprovedToBePublic(noteId: Int){
        lifecycleScope.launch {
            try {
                val apiService = ApiClient.retrofit.create(ApiService::class.java)
                val request = UpdateNoteRequest(public = true, to_public = false)
                val response = apiService.updateNoteAsAdmin(noteId, request)
                if (response.isSuccessful) {
                    Toast.makeText(this@View_Admin, "Note approved successfully", Toast.LENGTH_LONG).show()
                    val intent = Intent(this@View_Admin, AdminActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@View_Admin, response.message(), Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("View_Admin", "Error posting public notes: ${e.message}")
            } catch (e: HttpException) {
                Log.e("View_Admin", "HTTP error: ${e.response()?.errorBody()?.string()}")
                Toast.makeText(this@View_Admin, "Failed to update the note", Toast.LENGTH_LONG).show()
            }
        }
    }
}