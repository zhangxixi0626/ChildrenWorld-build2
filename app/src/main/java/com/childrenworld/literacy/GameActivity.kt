package com.childrenworld.literacy

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * Game activity - A fun quiz game to reinforce character learning
 */
class GameActivity : AppCompatActivity() {
    private lateinit var targetPinyin: TextView
    private lateinit var gameFeedback: TextView
    private lateinit var scoreText: TextView
    private lateinit var option1: Button
    private lateinit var option2: Button
    private lateinit var option3: Button
    private lateinit var option4: Button

    private lateinit var progressManager: ProgressManager
    private val optionButtons = mutableListOf<Button>()
    private var currentQuestion: ChineseCharacter? = null
    private var score = 0
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        progressManager = ProgressManager(this)

        // Initialize views
        targetPinyin = findViewById(R.id.targetPinyin)
        gameFeedback = findViewById(R.id.gameFeedback)
        scoreText = findViewById(R.id.scoreText)
        option1 = findViewById(R.id.option1)
        option2 = findViewById(R.id.option2)
        option3 = findViewById(R.id.option3)
        option4 = findViewById(R.id.option4)

        optionButtons.addAll(listOf(option1, option2, option3, option4))

        // Set up click listeners for all option buttons
        optionButtons.forEachIndexed { index, button ->
            button.setOnClickListener {
                checkAnswer(index)
            }
        }

        // Start the game
        loadNewQuestion()
    }

    private fun loadNewQuestion() {
        gameFeedback.visibility = View.GONE

        // Get random characters for this question
        val characters = CharacterRepository.getRandomCharacters(4)
        currentQuestion = characters.random()

        // Display the pinyin to find
        targetPinyin.text = "找出: ${currentQuestion?.pinyin}"

        // Shuffle and display options
        val shuffledCharacters = characters.shuffled()
        optionButtons.forEachIndexed { index, button ->
            button.text = shuffledCharacters[index].character
            button.isEnabled = true
            button.setBackgroundColor(getColor(R.color.primary_blue))
        }
    }

    private fun checkAnswer(selectedIndex: Int) {
        val selectedCharacter = optionButtons[selectedIndex].text.toString()
        val isCorrect = selectedCharacter == currentQuestion?.character

        // Disable all buttons temporarily
        optionButtons.forEach { it.isEnabled = false }

        if (isCorrect) {
            gameFeedback.text = getString(R.string.correct) + " ⭐"
            gameFeedback.setTextColor(getColor(R.color.primary_green))
            optionButtons[selectedIndex].setBackgroundColor(getColor(R.color.primary_green))
            
            score += 10
            progressManager.addStars(1)
            scoreText.text = "得分: $score"
        } else {
            gameFeedback.text = getString(R.string.incorrect)
            gameFeedback.setTextColor(getColor(R.color.primary_orange))
            optionButtons[selectedIndex].setBackgroundColor(getColor(R.color.primary_orange))
            
            // Highlight the correct answer
            optionButtons.forEachIndexed { index, button ->
                if (button.text == currentQuestion?.character) {
                    button.setBackgroundColor(getColor(R.color.primary_green))
                }
            }
        }

        gameFeedback.visibility = View.VISIBLE

        // Load next question after a delay
        handler.postDelayed({
            loadNewQuestion()
        }, 2000)
    }

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }
}
