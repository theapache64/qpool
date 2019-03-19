package com.theah64.qpool.models

import com.theah64.qpool.models.questions.Question

data class Answer(
    val question: Question,
    val answer: String
)