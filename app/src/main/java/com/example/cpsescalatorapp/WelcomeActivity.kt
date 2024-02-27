package com.example.cpsescalatorapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val welcomeImage = findViewById<ImageView>(R.id.imageView3)
        welcomeImage.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}