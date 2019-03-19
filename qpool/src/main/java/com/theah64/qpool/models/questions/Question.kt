package com.theah64.qpool.models.questions

import java.io.Serializable

open class Question(
    val question: String,
    val imageUrl: String? = null
) : Serializable {

    /**
     * checking if the none-of-the-above value set to option 4 if exits
     */
    fun checkNoneOfTheAboveConstraints(option1: String, option2: String, option3: String) {
        if (
            option1 == OPTION_NONE_OF_THE_ABOVE ||
            option2 == OPTION_NONE_OF_THE_ABOVE ||
            option3 == OPTION_NONE_OF_THE_ABOVE
        ) {
            throw IllegalArgumentException("$OPTION_NONE_OF_THE_ABOVE can only be set to option 4")
        }
    }

    companion object {
        const val KEY = "question"
        const val OPTION_NONE_OF_THE_ABOVE = "None of the above"
    }
}