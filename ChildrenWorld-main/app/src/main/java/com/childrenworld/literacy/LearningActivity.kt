package com.childrenworld.literacy

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.Locale

/**
 * Learning activity where children learn character pronunciation and practice speaking
 */
class LearningActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private lateinit var characterDisplay: TextView
    private lateinit var pinyinDisplay: TextView
    private lateinit var feedbackText: TextView
    private lateinit var btnListen: Button
    private lateinit var btnSpeak: Button
    private lateinit var btnNext: Button
    private lateinit var btnPrevious: Button

    private lateinit var progressManager: ProgressManager
    private var currentIndex = 0
    private var currentCharacter: ChineseCharacter? = null

    private var textToSpeech: TextToSpeech? = null
    private var speechRecognizer: SpeechRecognizer? = null
    private var isTtsInitialized = false

    companion object {
        private const val REQUEST_RECORD_AUDIO_PERMISSION = 200
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learning)

        // Initialize views
        characterDisplay = findViewById(R.id.characterDisplay)
        pinyinDisplay = findViewById(R.id.pinyinDisplay)
        feedbackText = findViewById(R.id.feedbackText)
        btnListen = findViewById(R.id.btnListen)
        btnSpeak = findViewById(R.id.btnSpeak)
        btnNext = findViewById(R.id.btnNext)
        btnPrevious = findViewById(R.id.btnPrevious)

        progressManager = ProgressManager(this)
        currentIndex = progressManager.getCurrentIndex()

        // Initialize Text-to-Speech
        textToSpeech = TextToSpeech(this, this)

        // Load current character
        loadCharacter()

        // Set up button listeners
        btnListen.setOnClickListener {
            speakCharacter()
        }

        btnSpeak.setOnClickListener {
            startSpeechRecognition()
        }

        btnNext.setOnClickListener {
            nextCharacter()
        }

        btnPrevious.setOnClickListener {
            previousCharacter()
        }

        // Check audio permission
        checkAudioPermission()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech?.setLanguage(Locale.CHINESE)
            isTtsInitialized = result != TextToSpeech.LANG_MISSING_DATA && 
                              result != TextToSpeech.LANG_NOT_SUPPORTED
            
            if (!isTtsInitialized) {
                Toast.makeText(this, "Chinese language not supported", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadCharacter() {
        currentCharacter = CharacterRepository.getCharacter(currentIndex)
        currentCharacter?.let { char ->
            characterDisplay.text = char.character
            pinyinDisplay.text = char.pinyin
            feedbackText.visibility = android.view.View.GONE
        }

        // Update button states
        btnPrevious.isEnabled = currentIndex > 0
        btnNext.isEnabled = currentIndex < CharacterRepository.getTotalCount() - 1
    }

    private fun speakCharacter() {
        currentCharacter?.let { char ->
            if (isTtsInitialized) {
                textToSpeech?.speak(char.character, TextToSpeech.QUEUE_FLUSH, null, null)
            } else {
                Toast.makeText(this, "语音功能未就绪", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkAudioPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                REQUEST_RECORD_AUDIO_PERMISSION
            )
        }
    }

    private fun startSpeechRecognition() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, getString(R.string.permission_required), Toast.LENGTH_SHORT).show()
            checkAudioPermission()
            return
        }

        if (speechRecognizer == null) {
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
            speechRecognizer?.setRecognitionListener(object : RecognitionListener {
                override fun onReadyForSpeech(params: Bundle?) {
                    feedbackText.text = "请说话..."
                    feedbackText.visibility = android.view.View.VISIBLE
                }

                override fun onBeginningOfSpeech() {}

                override fun onRmsChanged(rmsdB: Float) {}

                override fun onBufferReceived(buffer: ByteArray?) {}

                override fun onEndOfSpeech() {}

                override fun onError(error: Int) {
                    feedbackText.text = getString(R.string.try_again)
                    feedbackText.setTextColor(getColor(R.color.primary_orange))
                    feedbackText.visibility = android.view.View.VISIBLE
                }

                override fun onResults(results: Bundle?) {
                    val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    if (matches != null && matches.isNotEmpty()) {
                        checkSpeechResult(matches[0])
                    }
                }

                override fun onPartialResults(partialResults: Bundle?) {}

                override fun onEvent(eventType: Int, params: Bundle?) {}
            })
        }

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "zh-CN")
            putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, packageName)
            putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
        }

        speechRecognizer?.startListening(intent)
    }

    private fun checkSpeechResult(spokenText: String) {
        currentCharacter?.let { char ->
            // Simple check if the spoken text contains the character
            val isCorrect = spokenText.contains(char.character) || 
                           spokenText.contains(char.pinyin.replace("ǎ", "a")
                               .replace("á", "a").replace("à", "a").replace("ā", "a")
                               .replace("ē", "e").replace("é", "e").replace("è", "e").replace("ě", "e")
                               .replace("ī", "i").replace("í", "i").replace("ì", "i").replace("ǐ", "i")
                               .replace("ō", "o").replace("ó", "o").replace("ò", "o").replace("ǒ", "o")
                               .replace("ū", "u").replace("ú", "u").replace("ù", "u").replace("ǔ", "u")
                               .replace("ǖ", "v").replace("ǘ", "v").replace("ǚ", "v").replace("ǜ", "v"))

            if (isCorrect) {
                feedbackText.text = getString(R.string.great_job) + " ⭐"
                feedbackText.setTextColor(getColor(R.color.primary_green))
                progressManager.addStars(1)
                progressManager.markCharacterAsLearned(currentIndex)
            } else {
                feedbackText.text = getString(R.string.try_again)
                feedbackText.setTextColor(getColor(R.color.primary_orange))
            }
            feedbackText.visibility = android.view.View.VISIBLE
        }
    }

    private fun nextCharacter() {
        if (currentIndex < CharacterRepository.getTotalCount() - 1) {
            currentIndex++
            progressManager.setCurrentIndex(currentIndex)
            loadCharacter()
        }
    }

    private fun previousCharacter() {
        if (currentIndex > 0) {
            currentIndex--
            progressManager.setCurrentIndex(currentIndex)
            loadCharacter()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        textToSpeech?.stop()
        textToSpeech?.shutdown()
        speechRecognizer?.destroy()
        super.onDestroy()
    }
}
