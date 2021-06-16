package com.example.myapplication2.util

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences


class Prefs(context: Activity) {
    private var preferences: SharedPreferences? = null
    private val SCORE = "score_result"
    private val HIGHEST = "highest_score"
    private val STATE = "state"

    init {
        preferences = context.getPreferences(Context.MODE_PRIVATE)
    }

    fun saveScoreResult(score: Int) {
        val editor = preferences!!.edit()
        editor.putInt(SCORE, score)
        editor.apply()
    }

    fun getScoreResult(): Int {
        return preferences!!.getInt(SCORE, 0)
    }

    fun saveHighestResult(score: Int) {
        val lastScore = preferences!!.getInt(HIGHEST, 0)
        if (score > lastScore) {
            val editor = preferences!!.edit()
            editor.putInt(HIGHEST, score)
            editor.apply()
        }
    }

    fun getHighestResult(): Int {
        return preferences!!.getInt(HIGHEST, 0)
    }

    fun saveState(state: Int) {
        preferences!!.edit().putInt(STATE, state).apply()
    }

    fun getState(): Int {
        return preferences!!.getInt(STATE, 0)
    }
}