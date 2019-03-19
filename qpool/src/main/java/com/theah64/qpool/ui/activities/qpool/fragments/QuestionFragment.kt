package com.theah64.qpool.ui.activities.qpool.fragments


import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.theah64.qpool.R
import com.theah64.qpool.databinding.FragmentQuestionBinding
import com.theah64.qpool.models.Answer
import com.theah64.qpool.models.questions.CheckBoxQuestion
import com.theah64.qpool.models.questions.Question
import com.theah64.qpool.models.questions.RadioQuestion
import com.theah64.qpool.ui.activities.qpool.Callback
import kotlinx.android.synthetic.main.fragment_question.*


/**
 * A simple [Fragment] subclass.
 *
 */
class QuestionFragment : Fragment() {

    private lateinit var callback: Callback

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.callback = context as Callback
    }

    private lateinit var viewModel: QuestionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.viewModel = ViewModelProviders.of(this).get(QuestionViewModel::class.java)

        // Watching for button clicks
        viewModel.getButtonClicks().observe(this, Observer { buttonId ->

            Log.e("X", "Button clicked trigger from vm to fragment")

            when (buttonId) {

                // Next
                QuestionViewModel.ID_NEXT -> {


                    if (viewModel.question is RadioQuestion) {
                        // It was a radio question, so manually setting answer variable
                        val checkedRadioButton = binding.nrgInput.checkedRadioButton
                        checkedRadioButton?.let {
                            viewModel.answer = it.text as String
                        }
                    }

                    if (viewModel.question is CheckBoxQuestion) {
                        viewModel.answer = getCheckBoxAnswer()
                    }

                    Log.e("X", "Next button clicked @fragment")
                    callback.onNextButtonClicked(
                        Answer(viewModel.question!!, viewModel.answer)
                    )
                }

                // Prev
                QuestionViewModel.ID_PREV -> {

                    Log.e("X", "Prev button clicked @fragment")
                    callback.onPrevButtonClicked()
                }

                else -> throw IllegalArgumentException("Unmanaged click")
            }

        })

        // Watching for time input click
        viewModel.getTimeInputClick().observe(this, Observer { isShow ->
            if (isShow) {
                val dialog = TimePickerDialog(
                    this.activity,
                    TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                        val minuteString = if (minute == 0) "00" else "$minute"
                        viewModel.answer = "$hourOfDay:$minuteString"
                        mb_time.text = viewModel.answer
                    },
                    0,
                    0,
                    false
                )

                dialog.show()
            }
        })
    }

    private fun getCheckBoxAnswer(): String {

        val stringBuilder = StringBuilder()
        val mcbs = arrayOf(
            binding.mcbOption1,
            binding.mcbOption2,
            binding.mcbOption3,
            binding.mcbOption4
        )

        mcbs.forEach { mcb ->
            if (mcb.isChecked) {
                stringBuilder.append(mcb.text).append(",")
            }
        }
        return if (stringBuilder.isEmpty()) "" else stringBuilder.substring(0, stringBuilder.length - 1)
    }

    lateinit var binding: FragmentQuestionBinding

    private lateinit var question: Question

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        this.binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_question, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        arguments?.let {
            this.question = it.get(Question.KEY) as Question
            viewModel.question = question
            val curPosQues = it.getInt(KEY_CURRENT_QUESTION_POSITION)
            val totalQuestions = it.getInt(KEY_TOTAL_QUESTIONS)
            viewModel.setQuestionPositions(curPosQues, totalQuestions)


            // Loading image
            Glide.with(binding.ivImage)
                .load(question.imageUrl)
                .into(binding.ivImage)

            if (question is CheckBoxQuestion) {

                // Watching for 'None of the above'
                binding.mcbOption4.setOnCheckedChangeListener { buttonView, isChecked ->

                    if (buttonView.text == Question.OPTION_NONE_OF_THE_ABOVE) {

                        binding.mcbOption1.isEnabled = !isChecked
                        binding.mcbOption2.isEnabled = !isChecked
                        binding.mcbOption3.isEnabled = !isChecked

                        if (isChecked) {
                            binding.mcbOption1.isChecked = false
                            binding.mcbOption2.isChecked = false
                            binding.mcbOption3.isChecked = false
                        }

                    }

                }
            }
        }

        return binding.root
    }

    companion object {

        const val KEY_CURRENT_QUESTION_POSITION = "current_question_position"
        const val KEY_TOTAL_QUESTIONS = "total_questions"


        /**
         * Creates new instance of the question fragment
         */
        fun newInstance(question: Question, curQuesPos: Int, totalQuestions: Int): QuestionFragment {
            val fragment = QuestionFragment()
            val args = Bundle()
            args.putSerializable(Question.KEY, question)
            args.putInt(KEY_CURRENT_QUESTION_POSITION, curQuesPos)
            args.putInt(KEY_TOTAL_QUESTIONS, totalQuestions)
            fragment.arguments = args
            return fragment
        }
    }


}
