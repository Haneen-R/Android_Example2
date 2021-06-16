package com.example.myapplication2.data

import android.content.Context
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.example.myapplication2.MainActivity
import com.example.myapplication2.controller.AppController
import com.example.myapplication2.model.Question
import org.json.JSONArray
import org.json.JSONException
import java.util.*


class Repository(var context: Context) {
    var questionArrayList = ArrayList<Question>()
    var url =
        "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json"

    fun getQuestions(callBack: AnswerListAsyncResponse): List<Question> {
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response: JSONArray ->
                for (i in 0 until response.length()) {
                    try {
                        val question = Question(
                            response.getJSONArray(i)[0].toString(),
                            response.getJSONArray(i).getBoolean(1)
                        )
                        questionArrayList.add(question)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
                callBack!!.processFinished(questionArrayList)
            }
        ) { error: VolleyError -> }
        AppController.getInstance(context).addToRequestQueue(jsonArrayRequest)
        return questionArrayList
    }
}