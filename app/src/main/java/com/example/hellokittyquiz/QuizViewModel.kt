package com.example.hellokittyquiz

import Question
import androidx.lifecycle.ViewModel


class QuizViewModel: ViewModel() {
    // load my questions by creating a list of Question objects
    private val questionBank = listOf(
        Question(R.string.kitty1, true),
        Question(R.string.kitty2, false),
        Question(R.string.kitty3, false),
        Question(R.string.kitty4, true)
    )



    // create index counter
    var currentIndex = 0
    var isCheater = false
    var cheatQuestion = 0
    var correct = 0
    var answered = false
    var finished = false


    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textReID





    fun moveToNext() {
        answered = false
        currentIndex = (currentIndex + 1) % questionBank.size

    }


    fun moveToPrevious() {
        currentIndex = (currentIndex - 1) % questionBank.size

    }


    fun quizFinished() {
        if (currentIndex == questionBank.size) {
            finished = true
        }
    }



}


// var score = ...
// val message =
// var toast = Toast.makeText(this, message, Toast.LENGTH_LONG)
// toast.show()




