package com.theah64.qpool.ui.activities.qpool

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.theah64.qpool.R
import com.theah64.qpool.databinding.ActivityQpoolBinding
import com.theah64.qpool.models.Answer
import com.theah64.qpool.models.questions.Question
import com.theah64.qpool.ui.activities.qpool.adapters.QuestionPagerAdapter
import com.theah64.qpool.ui.base.BaseAppCompatActivity
import com.theah64.qpool.utils.PreferenceUtils

abstract class QPoolActivity : BaseAppCompatActivity(), Callback {

    private lateinit var binding: ActivityQpoolBinding
    private lateinit var adapter: QuestionPagerAdapter
    private lateinit var viewModel: QPoolViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_qpool)
        this.viewModel = ViewModelProviders.of(this).get(QPoolViewModel::class.java)


        // Attaching question adapter
        this.adapter = QuestionPagerAdapter(getQuestions(), supportFragmentManager)
        binding.vpQuestions.adapter = adapter

        // Showing welcome message
        getWelcomeMessageWithTitle()?.let { welcomeMessage ->
            showMessage(welcomeMessage)
        }
    }

    private fun showMessage(welcomeMessage: Pair<String, String>) {

        val preferenceUtils = PreferenceUtils(this)

        if (preferenceUtils.isFirstRun()) {

            val title = welcomeMessage.first
            val message = welcomeMessage.second

            val alertDialog = AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(R.string.action_dismiss) { _, _ ->
                    preferenceUtils.setFirstRun(false)
                }
                .create()

            alertDialog.show()
        }


    }

    abstract fun getQuestions(): Array<out Question>
    abstract fun onSurveyFinished(answers: List<Answer>)

    override fun onNextButtonClicked(answer: Answer) {


        Log.e("X", "Next button clicked @act")

        val currentItem = binding.vpQuestions.currentItem

        // Saving answer
        viewModel.answersMap[currentItem] = answer


        // Moving to next question
        val nextPos = currentItem + 1
        val totalPos = getQuestions().size
        if (nextPos < totalPos) {
            binding.vpQuestions.currentItem = nextPos
        } else {
            // Survey finished
            onSurveyFinished(viewModel.answersMap.values.toList())
        }
    }

    override fun onPrevButtonClicked() {

        Log.e("X", "Prev button clicked @act")

        val prevPos = binding.vpQuestions.currentItem - 1
        if (prevPos > -1) {
            binding.vpQuestions.currentItem = prevPos
        } else {
            Log.d("X", "At the start")
        }
    }

    /**
     * To show welcome message. First param will be the title and second will be the message.
     */
    open fun getWelcomeMessageWithTitle(): Pair<String, String>? {
        return null
    }

}

interface Callback {
    fun onNextButtonClicked(answer: Answer)
    fun onPrevButtonClicked()
}


