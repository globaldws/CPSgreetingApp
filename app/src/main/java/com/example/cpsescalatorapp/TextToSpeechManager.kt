package com.example.cpsescalatorapp

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.Locale

class TextToSpeechManager(private val context: Context) : TextToSpeech.OnInitListener {

    private var textToSpeech: TextToSpeech? = null

    init {
        initializeTextToSpeech()
    }

    override fun onInit(status: Int) {
        if (status != TextToSpeech.ERROR) {
            textToSpeech?.language = Locale.US
        } else {
            Log.e(TAG, "Initialization failed")
        }
    }

    fun speak(text: String) {
        textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    fun shutdown() {
        textToSpeech?.stop()
        textToSpeech?.shutdown()
    }

    private fun initializeTextToSpeech() {
        textToSpeech = TextToSpeech(context, this)
    }

    companion object {
        private const val TAG = "TextToSpeechManager"
    }
}