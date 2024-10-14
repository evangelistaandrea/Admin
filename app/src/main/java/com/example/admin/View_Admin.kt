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
import com.example.admin.api.requests_responses.notifications.PostNotification
import com.example.admin.api.requests_responses.notifications.PostNotificationDB
import com.example.admin.api.requests_responses.publicnotes.UpdateNoteRequest

import kotlinx.coroutines.launch
import retrofit2.HttpException

class View_Admin : AppCompatActivity() {
    private lateinit var tvtitle: TextView
    private lateinit var tvcontents: TextView
    private lateinit var ibthumbsup: ImageButton
    private lateinit var tvEmail : TextView
    private lateinit var tvUsername : TextView
    private lateinit var tvDate : TextView
    private lateinit var ibthumbsdown : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_admin)

        tvtitle = findViewById(R.id.view_tvtitle)
        tvcontents = findViewById(R.id.view_tvcontents)
        ibthumbsup = findViewById(R.id.thumbs_up)
        ibthumbsdown = findViewById(R.id.thumbs_down)
        tvEmail = findViewById(R.id.tvEmail)
        tvDate = findViewById(R.id.tvDate)
        tvUsername = findViewById(R.id.tvUsername)

        val intent = intent
        val title = intent.getStringExtra("title")
        val creatorEmail = intent.getStringExtra("creator_email")
        val creatorUsername =  intent.getStringExtra("creator_username")
        val contents = intent.getStringExtra("contents")
        val updatedAt = intent.getStringExtra("updated_at")
        val public = intent.getBooleanExtra("public", false)
        val toPublic = intent.getBooleanExtra("to_public", false) ?: false
        val noteId = intent.getIntExtra("notes_id", -1)  // Make sure the note ID is passed in the intent
        val userId = intent.getIntExtra("user_id", -1)
        val adminId = intent.getIntExtra("id", -1)

        Log.e("View_Admin", "Admin ID: $adminId")

        tvDate.setText(updatedAt)
        tvUsername.setText(creatorUsername)
        tvEmail.setText(creatorEmail)
        tvtitle.setText(title)
        tvcontents.setText(contents)

        ibthumbsdown.setOnClickListener {
            Log.d("View_Admin", "Note ID: $noteId")
            if(noteId != -1){
                if (creatorEmail != null && title != null) {
                    noteDisapprovedToBePublic(noteId, creatorEmail, title)
                }
            }
        }

        ibthumbsup.setOnClickListener {
            Log.d("View_Admin", "Note ID: $noteId")
            if(noteId != -1){
                if (creatorEmail != null && title != null) {
                    noteApprovedToBePublic(noteId, creatorEmail, title )
                }
            }
        }
    }

    private fun noteApprovedToBePublic(noteId: Int, email: String, title: String){
        lifecycleScope.launch {
            try {
                val apiService = ApiClient.retrofit.create(ApiService::class.java)
                val request = UpdateNoteRequest(public = true, to_public = false)
                val response = apiService.updateNoteAsAdmin(noteId, request)
                if (response.isSuccessful) {
                    sendNotificationNoteApproved(email, title)
                    postNotificationDBNoteAccepted(noteId, email,"Your note $title has been approved")
                    Toast.makeText(this@View_Admin, "Note approved successfully", Toast.LENGTH_LONG).show()
                    val intent = Intent(this@View_Admin, AdminActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@View_Admin, "Failed to update the note", Toast.LENGTH_LONG).show()
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

    private fun noteDisapprovedToBePublic(noteId: Int, email: String, title: String){
        lifecycleScope.launch {
            try {
                val apiService = ApiClient.retrofit.create(ApiService::class.java)
                val request = UpdateNoteRequest(public = false, to_public = false)
                val response = apiService.updateNoteAsAdmin(noteId, request)
                if (response.isSuccessful) {
                    sendNotificationNoteDecline(email, title)
                    postNotificationDBNoteDecline(noteId, email,"Your note $title has been disapproved")
                    Toast.makeText(this@View_Admin, "Note has been disapproved", Toast.LENGTH_LONG).show()
                    val intent = Intent(this@View_Admin, AdminActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@View_Admin, "Failed to update the note", Toast.LENGTH_LONG).show()
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

    private fun sendNotificationNoteApproved(email: String, title: String) {
        val apiService = ApiClient.retrofit.create(ApiService::class.java)
        val notificationRequest = PostNotification(email, title)
        lifecycleScope.launch {
            try {
                val response = apiService.postNoteAccepted(notificationRequest)
                if (response.isSuccessful) {
                    Log.d("View_Admin", "Notification sent successfully")
                } else {
                    Log.e("View_Admin", "Failed to send notification: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("View_Admin", "Error sending notification: ${e.message}")
            } catch (e: HttpException) {
                Log.e("View_Admin", "HTTP error: ${e.response()?.errorBody()?.string()}")
            }
        }
    }

    private fun sendNotificationNoteDecline(email: String, title: String) {
        val apiService = ApiClient.retrofit.create(ApiService::class.java)
        val notificationRequest = PostNotification(email, title)
        lifecycleScope.launch {
            try {
                val response = apiService.postNoteDecline(notificationRequest)
                if (response.isSuccessful) {
                    Log.d("View_Admin", "Notification sent successfully")
                } else {
                    Log.e("View_Admin", "Failed to send notification: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("View_Admin", "Error sending notification: ${e.message}")
            } catch (e: HttpException) {
                Log.e("View_Admin", "HTTP error: ${e.response()?.errorBody()?.string()}")
            }
        }
    }

    private fun postNotificationDBNoteAccepted(notesId: Int, email: String, message: String) {
        val apiService = ApiClient.retrofit.create(ApiService::class.java)
        val notificationRequest = PostNotificationDB(notesId, email, message)
        lifecycleScope.launch {
            try {
                val response = apiService.postNoteAcceptedDB(notificationRequest)
                if (response.isSuccessful) {
                    Log.d("View_Admin", "Notification sent successfully")
            } else {
                    Log.e("View_Admin", "Failed to send notification: ${response.code()}")
            }
        } catch (e: Exception) {
            Toast.makeText(this@View_Admin, "Failed to update the note", Toast.LENGTH_LONG).show()
            Log.e("View_Admin", "Error sending notification: ${e.message}")
        } catch (e: HttpException) {
            Toast.makeText(this@View_Admin, "Failed to update the note", Toast.LENGTH_LONG).show()
            Log.e("View_Admin", "HTTP error: ${e.response()?.errorBody()?.string()}")
            }
        }
    }

    private fun postNotificationDBNoteDecline(notesId: Int, email: String, message: String) {
        val apiService = ApiClient.retrofit.create(ApiService::class.java)
        val notificationRequest = PostNotificationDB(notesId, email, message)
        lifecycleScope.launch {
            try {
                val response = apiService.postNoteDeclineDB(notificationRequest)
                if (response.isSuccessful) {
                    Log.d("View_Admin", "Notification sent successfully")
                } else {
                    Log.e("View_Admin", "Failed to send notification: ${response.code()}")
                }
            } catch (e: Exception) {
                Toast.makeText(this@View_Admin, "Failed to update the note", Toast.LENGTH_LONG).show()
                Log.e("View_Admin", "Error sending notification: ${e.message}")
            } catch (e: HttpException) {
                Toast.makeText(this@View_Admin, "Failed to update the note", Toast.LENGTH_LONG).show()
                Log.e("View_Admin", "HTTP error: ${e.response()?.errorBody()?.string()}")
            }
        }
    }
}