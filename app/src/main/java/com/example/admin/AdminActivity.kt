package com.example.admin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.admin.api.ApiClient
import com.example.admin.api.ApiService
import kotlinx.coroutines.launch
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

class AdminActivity : AppCompatActivity() {
    lateinit var logout: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        logout = findViewById(R.id.logout_btn)

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchAdminData(recyclerView)

        this.logout.setOnClickListener {
            logoutUser()
        }
    }

    private fun fetchAdminData(recyclerView: RecyclerView) {
        lifecycleScope.launch {
            val apiService = ApiClient.retrofit.create(ApiService::class.java)

            try {
                val response = apiService.getAdmin()  // Fetch admin data

                if (response.isSuccessful) {
                    val adminData = response.body() ?: emptyList()

                    // Create Adapter and set it on the RecyclerView
                    val adapter = Adapter(adminData.map {
                        Data(it.title, it.public.toString())  // Assuming `Data` uses title and public status
                    })
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

    private fun logoutUser() {
        val token = getToken() ?: run {
            Toast.makeText(this@AdminActivity, "No token found", Toast.LENGTH_SHORT).show()
            return
        }
        val apiService = ApiClient.retrofit.create(ApiService::class.java)
        val call = apiService.logout("Bearer $token")

        call.enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful) {
                    clearToken()
                    Toast.makeText(
                        this@AdminActivity,
                        "Logged Out Successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent =Intent(this@AdminActivity,SplashActivity::class.java)
                    startActivity(intent)

                } else {
                    Toast.makeText(
                        this@AdminActivity,
                        "Error: ${response.errorBody()?.string()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                Log.e("AdminActivity", "Error: ${response.errorBody()?.string()}")
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Toast.makeText(
                    this@AdminActivity,
                    "Network error occurred: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun clearToken() {
        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().remove("auth_token").apply()
    }
    private fun getToken(): String? {
        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("auth_token", null)
    }
}