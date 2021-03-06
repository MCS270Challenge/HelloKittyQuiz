package com.example.hellokittyquiz

import TFQuestion
import androidx.lifecycle.ViewModel


class QuizViewModel: ViewModel() {
    // load my questions by creating a list of Question objects
    private val questionBank = listOf(
        MultQuestion(R.string.kitty1, true),
        MultQuestion(R.string.kitty2, false),
        MultQuestion(R.string.kitty3, false),
        MultQuestion(R.string.kitty4, true),
        MultQuestion(R.string.testMC, null, "a", "b", "c" ,"d" , 0)
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

    val currentQuestion: MultQuestion
        get() = questionBank[currentIndex]

    val currentQuestionAnswerTF: Boolean?
        get() = questionBank[currentIndex].tfAnswer

    val currentQuestionAnswerMC: Int?
        get() = questionBank[currentIndex].mcAnswer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textReID

    val numberOfQuestions : Int
        get() = questionBank.size

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




