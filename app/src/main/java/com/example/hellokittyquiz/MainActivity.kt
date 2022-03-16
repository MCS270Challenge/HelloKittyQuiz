
package com.example.hellokittyquiz

import TFQuestion
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders


private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"
private const val REQUEST_CODE_CHEAT = 0


class MainActivity : AppCompatActivity() {

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var questionTextView: TextView
    private lateinit var previousButton: ImageButton
    private lateinit var cheatButton: Button
    private lateinit var a1Button: Button
    private lateinit var a2Button: Button
    private lateinit var a3Button: Button
    private lateinit var a4Button: Button

    // load my questions by creating a list of Question objects
    private val QuestionBank = listOf(
        TFQuestion(R.string.kitty1, true),
        TFQuestion(R.string.kitty2, false),
        TFQuestion(R.string.kitty3, false),
        TFQuestion(R.string.kitty4, true)
     )


    // create index counter

    var correct = 0
    var answered = false
    var count = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0)?:0
        quizViewModel.currentIndex = currentIndex


        trueButton=findViewById(R.id.true_button)
        falseButton=findViewById(R.id.false_button)
        nextButton=findViewById(R.id.next_button)
        previousButton=findViewById(R.id.previous_button)
        questionTextView=findViewById(R.id.question_text_view)
        cheatButton=findViewById(R.id.cheat_button)
        a1Button=findViewById(R.id.answer1)
        a2Button=findViewById(R.id.answer2)
        //a3Button=findViewById(R.id.answer3)
        //a4Button=findViewById(R.id.answer4)

        fun checkAnswer(userAnswer: Boolean){
            val correctAnswer = quizViewModel.currentQuestionAnswer

            if (answered==true){
                val toast3 = Toast.makeText(this, R.string.answered_toast, Toast.LENGTH_LONG)
                toast3.setGravity(Gravity.TOP,0,100)
                toast3.show()

            }

            else{
                if(quizViewModel.isCheater == true){
                    Toast.makeText(this, R.string.judgement_2, Toast.LENGTH_LONG).show()
                }
                else {
                    if (userAnswer == correctAnswer){
                        answered = true
                        correct+=1
                        val toast1 = Toast.makeText(this, R.string.correct_toast, Toast.LENGTH_LONG)
                        toast1.setGravity(Gravity.TOP,0,100)
                        toast1.show()

                    }

                    else {
                        answered = true
                        val toast2 = Toast.makeText(this, R.string.incorrect_toast, Toast.LENGTH_LONG)
                        toast2.setGravity(Gravity.TOP,0,100)
                        toast2.show()

                    }
                }

            }

        }


        // AP3 Hint: Keep a counter to prevent clicking answers multiple times
        trueButton.setOnClickListener {

                checkAnswer(true)

        } // do something when you click the true button


        falseButton.setOnClickListener {

                checkAnswer(false)

        } // do something when you click the false button


        cheatButton.setOnClickListener {
            // If I press this button, I will go to the second activity
            // Wrap second activity into an intent
            startActivityForResult(intent, REQUEST_CODE_CHEAT)
            quizViewModel.isCheater = true
            quizViewModel.cheatQuestion ++
            Toast.makeText(this, R.string.judgement_toast, Toast.LENGTH_LONG).show()
            // val answerIsTrue:Boolean? = quizViewModel.currentQuestionAnswer
            // val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            //fix this later

        }


        fun updateQuestionMC(){
            setContentView(R.layout.test)
            a1Button=findViewById(R.id.answer1)
            a2Button=findViewById(R.id.answer2)
            a3Button=findViewById(R.id.answer3)
            a4Button=findViewById(R.id.answer4)
            val questionTextResId = quizViewModel.currentQuestionText
            questionTextView.setText(questionTextResId)
            a1Button.text = quizViewModel.currentQuestion.A1
            a2Button.text = quizViewModel.currentQuestion.A2
            a3Button.text = quizViewModel.currentQuestion.A3
            a4Button.text = quizViewModel.currentQuestion.A4
        }

        fun updateQuestions(){
            if (quizViewModel.currentQuestionTF){
                setContentView(R.layout.activity_main)
            } else updateQuestionMC()

            Log.d(TAG, "Checking: updating question text", Exception())
            val questionTextResId = quizViewModel.currentQuestionText
            questionTextView.setText(questionTextResId)


        }

        updateQuestions()


        nextButton.setOnClickListener {
            answered = false
            quizViewModel.isCheater = false
            count += 1

            correct.toDouble()
            val number:Double = 4.0
            val corr = correct/number
            val finalCorr = corr * 100

            val total = "Your total score is: $finalCorr %"

            if (count == QuestionBank.size){
                Toast.makeText(this, total, Toast.LENGTH_LONG).show()

            }
            else{
                quizViewModel.moveToNext()
                updateQuestions()

            }
            setContentView(R.layout.test)

        } // increase index counter


        previousButton.setOnClickListener {
           answered = true
            quizViewModel.moveToPrevious()
                updateQuestions()
            }


        questionTextView.setOnClickListener{
            quizViewModel.moveToNext()
            updateQuestions()

        }




        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK){
            return
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            quizViewModel.isCheater =
                data?.getBooleanExtra(EXTRA_ANSWER_IS_SHOWN, false) ?: false
        }
    }




    // ******* New, after create, log processes *******

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart is called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause is called")
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)

    }

    override fun onResume(){
        super.onResume()
        Log.d(TAG, "onResume is called")
    }

    override fun onStop(){
        super.onStop()
        Log.d(TAG, "onStop is called")
    }

    override fun onDestroy(){
        super.onDestroy()
        Log.d(TAG, "onDestroy is called")
    }



}






// Challenge AP1: Customizing the toast- Use Toast class's setGravity function to show Toast at the top
//                of the screen. Use Gravity.TOP for the gravity value
// Complete: X 2/8
// Due: 2/18



// Challenge AP1: Add a listener to the TextView- User could press TextView to see the next question.
//                Use View.OnClickListener for the TextView used with the buttons, because TextView
//                inherits from View
// Complete: X 2/10
// Due: 2/18

// Challenge AP1: Add a previous button.
// Complete: X 2/10
// Due: 2/18

// Challenge Bonus AP1: Add images to the buttons.
// Complete: X 2/14
// Due: 2/18

// Challenge AP2: Preventing repeating answers- disable buttons for that question after answered once
// Complete: X 2/21
// Due: 2/25

// Challenge AP2: Graded quiz- display a Toast with a percentage score for the quiz
// Complete: X 2/21
// Due: 2/25

// Challenge AP3: Record a video following the steps on pages 102-105
// Complete: X 2/27
// Due: 3/4

// Challenge AP3: Use the layout inspector
// Complete: X 3/1
// Due: 3/4

// Challenge AP3: Use the Profiler inspector
// Complete: X 3/1
// Due: 3/4

// Challenge AP6: Follow chapter examples for the cheating activity
// Complete: X 3/3
// Due: 3/11

// Challenge AP6: Tracking the cheat status by each question
// Complete: X 3/7
// Due: 3/11
