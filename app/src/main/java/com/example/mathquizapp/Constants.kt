package com.example.mathquizapp

object Constants {
    const val USER_NAME: String = "user_name"
    const val TOTAL_QUESTIONS: String = "total_questions"
    const val CORRECT_ANSWERS: String = "correct_answers"
    fun getQuestions(): ArrayList<Question> {
        //creation of empty question list
        val questionsList = ArrayList<Question>()
        //filling it up
        val que1 = Question(1,
            "What is this formula ?",
            R.drawable.cross_product,
            "Inner product",
            "Sum of vectors",
            "cross product",
            "Just three vectors",
            3)
        questionsList.add(que1)

        val que2 = Question(1,
            "What is this formula ?",
            R.drawable.bernoulli_distribution,
            "Normal distribution",
            "Poisson distribution",
            "Gaussian distribution",
            "Bernoulli distribution",
            4)
        questionsList.add(que2)
        return questionsList
    }
}