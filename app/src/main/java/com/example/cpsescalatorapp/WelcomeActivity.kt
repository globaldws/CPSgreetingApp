package com.example.cpsescalatorapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.cpsescalatorapp.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {
    private lateinit var textToSpeechManager: TextToSpeechManager
    private lateinit var binding: ActivityWelcomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_welcome)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        textToSpeechManager = TextToSpeechManager(this)

        binding.imageView2.setOnClickListener {
            textToSpeechManager.speak(getString(R.string.welcome_message))
        }

        binding.imageView3.setOnClickListener {
            textToSpeechManager.speak(getString(R.string.welcome_message))
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        textToSpeechManager.shutdown()
    }
}