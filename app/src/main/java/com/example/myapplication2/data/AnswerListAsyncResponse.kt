package com.example.myapplication2.data

import com.example.myapplication2.model.Question
import java.util.*


interface AnswerListAsyncResponse {
    fun processFinished(questionArrayList: ArrayList<Question>)

}