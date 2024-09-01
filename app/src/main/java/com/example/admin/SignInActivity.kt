package com.example.admin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.admin.api.ApiClient
import com.example.admin.api.ApiService
import com.example.admin.api.requests_responses.LoginRequest
import com.example.admin.api.requests_responses.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivity : AppCompatActivity() {
    private lateinit var btnSignup: Button
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnForgot: Button
    private lateinit var btnLoginNow: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        btnSignup = findViewById(R.id.btnSignUp)
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnForgot = findViewById(R.id.btnForgotPassword)
        btnLoginNow = findViewById(R.id.btnSignInNow)

        btnLoginNow.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this@SignInActivity, "Fill up all fields", Toast.LENGTH_SHORT).show()
            } else {
                loginUser(username, password)
            }
        }

    }

    private fun loginUser(username: String, password: String) {
        val apiService = ApiClient.retrofit.create(ApiService::class.java)
        val loginRequest = LoginRequest(username = username, password = password)
        val call = apiService.signInUser(loginRequest)

        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    val token = loginResponse?.token
                    if (token != null) {
                        saveToken(token)

                        if (username == "admin" && password == "admin123") {
                            Log.d(
                                "SignInActivity",
                                "Correct credentials provided. Launching AdminActivity."
                            )
                            val intent = Intent(this@SignInActivity, AdminActivity::class.java)
                            startActivity(intent)
                            finish()
                            Toast.makeText(
                                this@SignInActivity,
                                "Admin Logged In",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(this@SignInActivity, "Unauthorized", Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else if (response.code() == 401) {
                        Toast.makeText(
                            this@SignInActivity,
                            "Invalid Credentials",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@SignInActivity,
                        "Error: ${response.errorBody()?.string()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@SignInActivity, "Network error occurred: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun saveToken(token: String) {
        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("auth_token", token).apply()
    }

}