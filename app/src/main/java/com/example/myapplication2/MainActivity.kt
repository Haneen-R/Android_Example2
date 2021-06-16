package com.example.myapplication2

import android.graphics.Color
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.myapplication2.data.AnswerListAsyncResponse
import com.example.myapplication2.data.Repository
import com.example.myapplication2.databinding.ActivityMainBinding
import com.example.myapplication2.model.Question
import com.example.myapplication2.model.Score
import com.example.myapplication2.util.Prefs
import java.lang.String.valueOf
import java.util.*
class MainActivity : AppCompatActivity() {
    private val MODE_ID = "1"
    private lateinit var binding: ActivityMainBinding
    private var currentQuestionIndex = 0
    private var scoreResult = 0
    private var highestResult = 0
    private val scoreObj: Score = Score()
    private lateinit var sharedPreferences: Prefs
    lateinit var questions: List<Question>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreferences= Prefs(this)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)
        currentQuestionIndex=sharedPreferences.getState()
        questions = Repository(this).getQuestions(
            object : AnswerListAsyncResponse {
                override fun processFinished(questionArrayList: ArrayList<Question>) {
                    binding.questionTextview.text = questionArrayList[currentQuestionIndex].getAnswer()
                    updateCounter(questions as ArrayList<Question>)
                }
            }
        )
        binding.buttonNext.setOnClickListener {
            currentQuestionIndex=(currentQuestionIndex+1)%questions.size //avoid list out of bound
            updateQuestion()

        }
        binding.buttonTrue.setOnClickListener { checkAnswer(true) }
        binding.buttonFalse.setOnClickListener { checkAnswer(false) }
        scoreResult=sharedPreferences.getScoreResult()
        highestResult=sharedPreferences.getHighestResult()
        binding.scoreResult.text=valueOf(scoreResult)
        binding.highestScore.text=valueOf(highestResult)

    }

    private fun checkAnswer(userChoseCorrect: Boolean) {
       val answer:Boolean= questions[currentQuestionIndex].isAnswerTrue()
        var snackMessageId = 0
        if (userChoseCorrect === answer) {
            snackMessageId = R.string.correct_answer
            fadeAnimation()
            addPoints()
            currentQuestionIndex = (currentQuestionIndex + 1) % questions.size
            updateQuestion()
            sharedPreferences.saveState(currentQuestionIndex)
        } else {
            snackMessageId = R.string.incorrect_answer
            shakeAnimation()
            deductPoints()
            currentQuestionIndex = (currentQuestionIndex + 1) % questions.size
            updateQuestion()
            sharedPreferences.saveState(currentQuestionIndex)
        }

        Toast.makeText(this, snackMessageId, Toast.LENGTH_SHORT).show()

    }

    private fun shakeAnimation() {
        val shake:Animation=AnimationUtils.loadAnimation(this,R.anim.shake_anim)
        binding.cardView.animation=shake
        shake.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                binding.questionTextview.setTextColor(Color.RED)}
            override fun onAnimationEnd(animation: Animation?) {
                binding.questionTextview.setTextColor(Color.WHITE) }
            override fun onAnimationRepeat(animation: Animation?) {}
        })
    }
    private fun fadeAnimation() {
      val alphaAnimation= AlphaAnimation(1.0f,0.0f)
        alphaAnimation.duration=300
        alphaAnimation.repeatCount=1
        alphaAnimation.repeatMode=Animation.REVERSE
        binding.cardView.animation=alphaAnimation
        alphaAnimation.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationStart(animation: Animation?) {
                binding.questionTextview.setTextColor(Color.GREEN) }
            override fun onAnimationEnd(animation: Animation?) {
                binding.questionTextview.setTextColor(Color.WHITE) }
            override fun onAnimationRepeat(animation: Animation?) {}
        })
    }

    private fun deductPoints() {
        scoreResult=sharedPreferences.getScoreResult()
        if(scoreResult>=100){
            scoreResult-=100
            scoreObj.setScore(scoreResult)
            binding.scoreResult.text=valueOf(scoreObj.getScore())
            sharedPreferences.saveScoreResult(scoreResult)
            sharedPreferences.saveHighestResult(scoreResult)

        }
        else{
            scoreResult=0
            scoreObj.setScore(scoreResult)
            binding.scoreResult.text=valueOf(scoreObj.getScore())
            sharedPreferences.saveScoreResult(scoreResult)
            sharedPreferences.saveHighestResult(scoreResult)

        }
    }

    private fun addPoints() {
        scoreResult+=100
        scoreObj.setScore(scoreResult)
        binding.scoreResult.text= valueOf(scoreObj.getScore())
        sharedPreferences.saveScoreResult(scoreObj.getScore())
        sharedPreferences.saveHighestResult(scoreObj.getScore())
    }

    private fun updateQuestion() {
        val question:String= questions[currentQuestionIndex].getAnswer()
        binding.questionTextview.text=question
        updateCounter(questions as ArrayList<Question>)
    }

    private fun updateCounter(questions: ArrayList<Question>) {
        binding.textViewOutOf.text=
            String.format(getString(R.string.text_formatted),currentQuestionIndex,questions.size)
    }
}