package com.example.hellokittyquiz

import TFQuestion
import androidx.lifecycle.ViewModel


class QuizViewModel: ViewModel() {
    // load my questions by creating a list of Question objects
    private val questionBank = listOf(
        TFQuestion(R.string.kitty1, true, null),
        TFQuestion(R.string.kitty2, false, null),
        TFQuestion(R.string.kitty3, false, null),
        TFQuestion(R.string.kitty4, true, null),
        TFQuestion(R.string.testMC, null, 0)
    )



    // create index counter
    var currentIndex = 0
    var isCheater = false
    var cheatQuestion = 0
    var correct = 0
    var answered = false
    var finished = false

    val currentQuestionTF: Boolean
        get() = currentQuestion.tfAnswer != null

    val currentQuestion: TFQuestion
        get() = questionBank[currentIndex]

    val currentQuestionAnswerTF: Boolean?
        get() = questionBank[currentIndex].tfAnswer

    val currentQuestionAnswerMC: Int?
        get() = questionBank[currentIndex].multAnswer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textReID

    val currentQuestionAnswers: Array<String?>
        get() = arrayOf(currentQuestion.A1, currentQuestion.A2, currentQuestion.A3, currentQuestion.A4)





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




