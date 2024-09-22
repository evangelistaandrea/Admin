package com.example.admin

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class View_Admin : AppCompatActivity() {
    private lateinit var tvtitle: TextView
    private lateinit var tvcontents: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_admin)

        tvtitle = findViewById(R.id.view_tvtitle)
        tvcontents = findViewById(R.id.view_tvcontents)

        val intent = intent
        val title = intent.getStringExtra("title")
        val contents = intent.getStringExtra("contents")

        tvtitle.text = title
        tvcontents.text = contents
    }

}