package com.example.cpsescalatorapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cpsescalatorapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "WelcomeActivity"
    }

    private val context: Context = this

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.whatsNext.setOnClickListener {
            startActivity(Intent(this, ContestActivity::class.java))
        }
//        binding.contestEntry.setOnClickListener {
//            startActivity(Intent(this, ContestActivity::class.java))
//        }
//        binding.whatsNext.setOnClickListener {
//            startActivity(Intent(this, WelcomeActivity::class.java))
//        }
    }


}