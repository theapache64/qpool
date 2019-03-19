package com.theah64.qpool.ui.activities.qpool.fragments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.theah64.qpool.models.questions.*


class QuestionViewModel : ViewModel() {

    var isFirstQuestion: Boolean = false
    var isLastQuestion: Boolean = false
    lateinit var textPerc: String
    var intPerc: Int = 0

    private val buttonClicks = MutableLiveData<Int>()
    private val timeInputClicked = MutableLiveData<Boolean>()
    var answer: String = ""

    fun getButtonClicks(): LiveData<Int> {
        return buttonClicks
    }

    var question: Question? = null
        set(value) {

            when (value) {

                is FactualQuestion -> factualQuestion = value
                is RadioQuestion -> radioQuestion = value
                is CheckBoxQuestion -> checkBoxQuestion = value
                is TimeQuestion -> timeQuestion = value
                else -> throw IllegalArgumentException("Undefined question type")
            }

            field = value
        }

    var factualQuestion: FactualQuestion? = null
    var radioQuestion: RadioQuestion? = null
    var checkBoxQuestion: CheckBoxQuestion? = null
    var timeQuestion: TimeQuestion? = null

    fun onPrevButtonClicked() {
        Log.e("X", "Prev button clicked @vm")
        buttonClicks.value = ID_PREV
    }

    fun onNextButtonClicked() {
        Log.e("X", "Next button clicked @vm")
        buttonClicks.value = ID_NEXT
    }

    fun onTimeInputClicked() {
        timeInputClicked.value = true
    }

    fun getTimeInputClick(): LiveData<Boolean> = timeInputClicked

    fun setQuestionPositions(curPosQues: Int, totalQuestions: Int) {
        isFirstQuestion = curPosQues == 0
        isLastQuestion = curPosQues == (totalQuestions - 1)
        textPerc = "${curPosQues + 1}/$totalQuestions"
        val x = ((curPosQues + 1) * 100) / totalQuestions
        intPerc = x
    }


    companion object {
        const val ID_PREV = 1
        const val ID_NEXT = 2
    }
}