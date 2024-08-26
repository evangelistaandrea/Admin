package com.example.admin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = Adapter(example())

    }

    private fun example(): List<Notes> {
        return listOf(
            Notes("Title 1", "Creator 1"),
            Notes("Title 2", "Creator 2"),
            Notes("Title 3", "Creator 3"),
            Notes("Title 4", "Creator 4"),
            Notes("Title 5", "Creator 5"),
            Notes("Title 6", "Creator 6"),
            Notes("Title 7", "Creator 7"),
            Notes("Title 8", "Creator 8"),
            Notes("Title 9", "Creator 9"),
            Notes("Title 10", "Creator 10")
        )
    }

}
