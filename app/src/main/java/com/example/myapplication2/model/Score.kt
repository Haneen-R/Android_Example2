package com.example.myapplication2.model

class Score() {
    private var score = 0
    constructor(score: Int) : this() {
        this.score = score
    }
    fun getScore(): Int {
        return score
    }
    fun setScore(score: Int) {
        this.score = score
    }
}