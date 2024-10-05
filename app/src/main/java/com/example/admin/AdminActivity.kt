package com.example.admin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.admin.api.ApiClient
import com.example.admin.api.ApiService
import kotlinx.coroutines.launch

class AdminActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchAdminData(recyclerView)
    }

    private fun fetchAdminData(recyclerView: RecyclerView) {
        lifecycleScope.launch {
            try {
                val apiService = ApiClient.retrofit.create(ApiService::class.java)
                val response = apiService.getAdmin()  // Fetch admin data

                if (response.isSuccessful) {
                    val adminData = response.body() ?: emptyList()

                    // Create Adapter and set it on the RecyclerView
                    val adapter = Adapter(adminData.map {
                        // Map fields correctly from API response
                        Data(
                            it.adminId,
                            it.notesId,
                            it.title,
                            it.creatorUsername,
                            it.creatorEmail,
                            it.contents,
                            it.public
                        )
                    }) { note ->
                        // Handle item click here, navigate to View_Admin

                        val intent = Intent(this@AdminActivity, View_Admin::class.java)
                        intent.putExtra("id", note.adminId)
                        intent.putExtra("notesId", note.notesId)
                        intent.putExtra("title", note.title)
                        intent.putExtra("creator_username", note.creatorUsername)
                        intent.putExtra("creator_email", note.creatorEmail)// Assuming contents are in `creator`
                        intent.putExtra("contents", note.contents)
                        intent.putExtra("public", note.public)
                        startActivity(intent)
                    }
                    recyclerView.adapter = adapter
                } else {
                    Toast.makeText(this@AdminActivity, "Error: ${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@AdminActivity, "Error fetching admin data: ${e.message}", Toast.LENGTH_SHORT).show()
                Log.e("AdminActivity", "Error fetching admin data", e)
            }
        }
    }
}
