package com.example.admin

import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class SplashActivity : AppCompatActivity() {
    private lateinit var videoView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        videoView = findViewById(R.id.videoView)

        val uri: Uri = Uri.parse("android.resource://$packageName/${R.raw.intro}")
        videoView.setVideoURI(uri)
        videoView.requestFocus()
        videoView.start()

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, AdminActivity::class.java)
            startActivity(intent)
            finish()
        }, 4500)

    }
}