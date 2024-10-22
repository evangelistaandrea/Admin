package com.example.admin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.admin.api.ApiClient
import com.example.admin.api.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AdminActivity : AppCompatActivity() {
    lateinit var layoutManager: LinearLayoutManager
    lateinit var swipelayout: SwipeRefreshLayout
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: Adapter  // Declare Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager  // Set layout manager

        // Fetch the admin data and populate RecyclerView

       swipelayout = findViewById(R.id.swipe_refresh)

       swipelayout.setOnRefreshListener {
           fetchAdminData()
           swipelayout.isRefreshing = false
       }

        fetchAdminData()
    }

    private fun fetchAdminData() {
        lifecycleScope.launch {
            try {
                val apiService = ApiClient.retrofit.create(ApiService::class.java)
                val response = withContext(Dispatchers.IO) {
                    apiService.getPendingNotes() // Synchronous call to get admin data
                }

                if (response.isSuccessful) {
                    val adminData = response.body()

                    // Ensure the activity is still active before updating UI
                    if (!isFinishing && adminData != null) {
                        // Create and set the adapter with the fetched admin data
                        adapter = Adapter(adminData)
                        recyclerView.adapter = adapter
                    } else {
                        if (!isFinishing) {
                            Toast.makeText(this@AdminActivity, "No data available", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    if (!isFinishing) {
                        Toast.makeText(this@AdminActivity, "Failed to fetch data", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                if (!isFinishing) {
                    Toast.makeText(this@AdminActivity, "Error fetching admin data: ${e.message}", Toast.LENGTH_SHORT).show()
                }
                Log.e("AdminActivity", "Error fetching admin data: ${e.message}")
            }
        }
    }

    override fun onBackPressed() {
       finishAffinity()
    }
}
