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
import com.example.admin.api.requests_responses.publicnotes.postPublicNotesData
import kotlinx.coroutines.launch

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

        tvtitle.setText(title)
        tvcontents.setText(contents)

        ibthumbsup.setOnClickListener {
            val adminTitle = title.toString()
            val adminCreator = creator.toString()
            val adminContents = contents.toString()
            val public = true
            postPublicNotes(adminTitle, adminCreator, adminContents, public)
        }
    }

    private fun postPublicNotes(title: String, creator: String, contents: String, public: Boolean) {
        lifecycleScope.launch {
            try {
                val apiService = ApiClient.retrofit.create(ApiService::class.java)
                val post_publicNotes = postPublicNotesData(title, creator, contents, public)
                val response = apiService.postPublicNotes(post_publicNotes)
                if (response.isSuccessful) {
                    Toast.makeText(this@View_Admin, "Public notes posted successfully", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@View_Admin, AdminActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@View_Admin, "Error: ${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("View_Admin", "Error posting public notes: ${e.message}")
            }
        }
    }
}