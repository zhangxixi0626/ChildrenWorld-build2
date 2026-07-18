package com.childrenworld.literacy

import android.content.Context
import android.content.SharedPreferences

/**
 * Manager for tracking user progress including characters learned and stars earned
 */
class ProgressManager(context: Context) {
    private val prefs: SharedPreferences = 
        context.getSharedPreferences("literacy_progress", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_CHARACTERS_LEARNED = "characters_learned"
        private const val KEY_TOTAL_STARS = "total_stars"
        private const val KEY_CURRENT_INDEX = "current_index"
    }

    fun getCharactersLearned(): Int {
        return prefs.getInt(KEY_CHARACTERS_LEARNED, 0)
    }

    fun incrementCharactersLearned() {
        val current = getCharactersLearned()
        prefs.edit().putInt(KEY_CHARACTERS_LEARNED, current + 1).apply()
    }

    fun getTotalStars(): Int {
        return prefs.getInt(KEY_TOTAL_STARS, 0)
    }

    fun addStars(stars: Int) {
        val current = getTotalStars()
        prefs.edit().putInt(KEY_TOTAL_STARS, current + stars).apply()
    }

    fun getCurrentIndex(): Int {
        return prefs.getInt(KEY_CURRENT_INDEX, 0)
    }

    fun setCurrentIndex(index: Int) {
        prefs.edit().putInt(KEY_CURRENT_INDEX, index).apply()
    }

    fun markCharacterAsLearned(characterIndex: Int) {
        val key = "learned_$characterIndex"
        if (!prefs.getBoolean(key, false)) {
            prefs.edit().putBoolean(key, true).apply()
            incrementCharactersLearned()
        }
    }

    fun isCharacterLearned(characterIndex: Int): Boolean {
        return prefs.getBoolean("learned_$characterIndex", false)
    }
}
