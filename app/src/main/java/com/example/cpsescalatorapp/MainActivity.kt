package com.example.cpsescalatorapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.appcompat.app.AppCompatActivity
import com.example.cpsescalatorapp.databinding.ActivityMainBinding
import java.util.Locale


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var textToSpeechManager: TextToSpeechManager

    companion object {
        private const val TAG = "WelcomeActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        textToSpeechManager = TextToSpeechManager(this)
//        textToSpeechManager.speak(getString(R.string.welcome_message))

        textToSpeechManager = TextToSpeechManager(this) {
            // This block will be executed when TextToSpeech is initialized
            textToSpeechManager.speak(getString(R.string.welcome_message))
        }

        binding.imageView2.setOnClickListener {
            textToSpeechManager.speak(getString(R.string.welcome_message))
        }

        binding.whatsNext.setOnClickListener {
            startActivity(Intent(this, ContestActivity::class.java))
        }


    }

    override fun onResume() {
        super.onResume()
        textToSpeechManager.speak(getString(R.string.welcome_message))
    }

    override fun onDestroy() {
        textToSpeechManager.shutdown()
        super.onDestroy()
    }
}