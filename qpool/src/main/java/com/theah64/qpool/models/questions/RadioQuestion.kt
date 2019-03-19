package com.theah64.qpool.models.questions

open class RadioQuestion(
    question: String,

    val option1: String,
    val option2: String,
    val option3: String,
    val option4: String,

    imageUrl: String? = null
) : Question(question, imageUrl) {
    init {
        super.checkNoneOfTheAboveConstraints(option1, option2, option3)
    }
}