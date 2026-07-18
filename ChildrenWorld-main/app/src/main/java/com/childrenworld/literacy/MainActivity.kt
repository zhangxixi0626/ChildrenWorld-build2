package com.childrenworld.literacy

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent

/**
 * Main activity - Home screen of the app
 */
class MainActivity : AppCompatActivity() {
    private lateinit var progressManager: ProgressManager
    private lateinit var progressText: TextView
    private lateinit var starsText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressManager = ProgressManager(this)

        // Initialize views
        progressText = findViewById(R.id.progressText)
        starsText = findViewById(R.id.starsText)
        val btnStartLearning: Button = findViewById(R.id.btnStartLearning)
        val btnPlayGame: Button = findViewById(R.id.btnPlayGame)

        // Update progress display
        updateProgress()

        // Set up button listeners
        btnStartLearning.setOnClickListener {
            val intent = Intent(this, LearningActivity::class.java)
            startActivity(intent)
        }

        btnPlayGame.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        updateProgress()
    }

    private fun updateProgress() {
        val charactersLearned = progressManager.getCharactersLearned()
        val totalStars = progressManager.getTotalStars()
        
        progressText.text = getString(R.string.characters_learned, charactersLearned)
        starsText.text = getString(R.string.total_stars, totalStars)
    }
}
