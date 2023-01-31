package com.example.mathquizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {
    private var mCurrentPosition:Int = 1
    private var mQuestionsList: ArrayList<Question>? = null
    private var mSelectedOptionPosition: Int = 0
    private var mCorrectAnswers: Int = 0
    private var mUserName: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        mUserName = intent.getStringExtra(Constants.USER_NAME)


        mQuestionsList = Constants.getQuestions()

        val tvOptionOne = findViewById<TextView>(R.id.tv_option_one)
        val tvOptionTwo = findViewById<TextView>(R.id.tv_option_two)
        val tvOptionThree = findViewById<TextView>(R.id.tv_option_three)
        val tvOptionFour = findViewById<TextView>(R.id.tv_option_four)
        val btnSubmit = findViewById<Button>(R.id.btn_submit)


        setQuestion()

        tvOptionOne.setOnClickListener(this)
        tvOptionTwo.setOnClickListener(this)
        tvOptionThree.setOnClickListener(this)
        tvOptionFour.setOnClickListener(this)
        btnSubmit.setOnClickListener(this)
    }

    //setting up default options(answer) view
    private fun defaultOptionsView() {
        val tvOptionOne = findViewById<TextView>(R.id.tv_option_one)
        val tvOptionTwo = findViewById<TextView>(R.id.tv_option_two)
        val tvOptionThree = findViewById<TextView>(R.id.tv_option_three)
        val tvOptionFour = findViewById<TextView>(R.id.tv_option_four)
        val options = ArrayList<TextView>()
        options.add(0, tvOptionOne)
        options.add(1, tvOptionTwo)
        options.add(2, tvOptionThree)
        options.add(3, tvOptionFour)
        //setting up default colors in quiz UI
        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_option_border_bg
            )
        }
    }

    private fun setQuestion() {
        val btnSubmit =  findViewById<Button>(R.id.btn_submit)
        val question = mQuestionsList!![mCurrentPosition - 1]

        defaultOptionsView()

        if(mCurrentPosition == mQuestionsList!!.size) {
            btnSubmit.text = "FINISH"
        }else{
            btnSubmit.text = "SUBMIT"
        }

        //take xml markdown elements by id
        val progBar =  findViewById<ProgressBar>(R.id.progressBar)
        val tvProgress = findViewById<TextView>(R.id.tv_progress)
        val tvQuestion = findViewById<TextView>(R.id.tv_question)
        val ivImage = findViewById<ImageView>(R.id.iv_image)
        val tvOptionOne = findViewById<TextView>(R.id.tv_option_one)
        val tvOptionTwo = findViewById<TextView>(R.id.tv_option_two)
        val tvOptionThree = findViewById<TextView>(R.id.tv_option_three)
        val tvOptionFour = findViewById<TextView>(R.id.tv_option_four)

        progBar.progress = mCurrentPosition
        tvProgress.text = "$mCurrentPosition" + "/" + progBar.max
        tvQuestion.text = question!!.question
        ivImage.setImageResource(question.image)
        tvOptionOne.text = question.optionOne
        tvOptionTwo.text = question.optionTwo
        tvOptionThree.text = question.optionThree
        tvOptionFour.text = question.optionFour
    }

    override fun onClick(v: View?) {
        val tvOptionOne = findViewById<TextView>(R.id.tv_option_one)
        val tvOptionTwo = findViewById<TextView>(R.id.tv_option_two)
        val tvOptionThree = findViewById<TextView>(R.id.tv_option_three)
        val tvOptionFour = findViewById<TextView>(R.id.tv_option_four)
        when(v?.id) {
            R.id.tv_option_one-> {
                selectedOptionView(tvOptionOne, 1)
            }
            R.id.tv_option_two-> {
                selectedOptionView(tvOptionTwo, 2)
            }
            R.id.tv_option_three-> {
                selectedOptionView(tvOptionThree, 3)
            }
            R.id.tv_option_four-> {
                selectedOptionView(tvOptionFour, 4)
            }
            //implementation of repainting after submit button and next page moving
            R.id.btn_submit ->{
                //if no answers were chosen yet
                if(mSelectedOptionPosition == 0) {
                    mCurrentPosition++

                    //draw a new question page
                    when {
                        mCurrentPosition <= mQuestionsList!!.size -> {
                            setQuestion()
                        }
                        else -> {
                            val intent = Intent(this, ResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME, mUserName)
                            intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers)
                            intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList!!.size)
                            startActivity(intent)
                        }
                    }
                }else{
                    val question = mQuestionsList?.get(mCurrentPosition - 1)
                    if(question!!.correctAnswer != mSelectedOptionPosition) {
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                    }
                    else {
                        mCorrectAnswers++
                    }
                    answerView(question.correctAnswer,R.drawable.correct_option_border_bg)
                    val btnSubmit = findViewById<Button>(R.id.btn_submit)
                    if(mCurrentPosition == mQuestionsList!!.size) {

                        btnSubmit.text = "FINISH"
                    }
                    else{
                        btnSubmit.text = "GO TO NEXT QUESTION"
                    }
                    //hide chosen answer to render next question page
                    mSelectedOptionPosition = 0

                }
            }
        }

    }

    private fun answerView(answer: Int, drawableView:Int) {
        val tvOptionOne = findViewById<TextView>(R.id.tv_option_one)
        val tvOptionTwo = findViewById<TextView>(R.id.tv_option_two)
        val tvOptionThree = findViewById<TextView>(R.id.tv_option_three)
        val tvOptionFour = findViewById<TextView>(R.id.tv_option_four)
        //user clicked at answer, according it`s number we paint it to drawable view
        when(answer) {
            1 -> {
                tvOptionOne.background = ContextCompat.getDrawable(
                    this, drawableView
                )
            }
            2 -> {
                tvOptionTwo.background = ContextCompat.getDrawable(
                    this, drawableView
                )
            }
            3 -> {
                tvOptionThree.background = ContextCompat.getDrawable(
                    this, drawableView
                )
            }
            4 -> {
                tvOptionFour.background = ContextCompat.getDrawable(
                    this, drawableView
                )
            }

        }
    }
    private fun selectedOptionView(tv: TextView, selectedOptionNum:Int) {
        //set another fields by default
        defaultOptionsView()
        mSelectedOptionPosition = selectedOptionNum
        //repaint selected answer and make it`s text bold
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this,
            R.drawable.default_option_border_bg
        )

    }

}