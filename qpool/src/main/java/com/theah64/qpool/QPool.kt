package com.theah64.qpool

import com.theah64.qpool.models.questions.Question

object QPool {
    private lateinit var questions: Array<out Question>

    fun init(vararg questions: Question) {
        this.questions = questions
    }
}