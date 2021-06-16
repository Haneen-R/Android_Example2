package com.example.myapplication2.model

class Question(private var answer: String, private var answerTrue: Boolean) {

    fun getAnswer(): String {
        return answer
    }
    fun setAnswer(answer: String) {
        this.answer = answer
    }
    fun isAnswerTrue(): Boolean {
        return answerTrue
    }
    fun setAnswerTrue(answerTrue: Boolean) {
        this.answerTrue = answerTrue
    }
    override fun toString(): String{
        return "Question{" +
                "answer='" + answer + '\'' +
                ", answerTrue=" + answerTrue +
                '}'
    }
}