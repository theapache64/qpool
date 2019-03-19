package com.theah64.qpoolexample

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AlertDialog
import com.theah64.qpool.models.Answer
import com.theah64.qpool.models.questions.*
import com.theah64.qpool.ui.activities.qpool.QPoolActivity

class MainActivity : QPoolActivity() {
    companion object {
        fun getStartIntent(context: Context): Intent {
            val intent = Intent(context, MainActivity::class.java)
            return intent
        }
    }

    /**
     * Questions to be asked
     */
    override fun getQuestions(): Array<out Question> {

        //TODO: Replace dummy questions with real questions
        return arrayOf(

            // Favorite Language
            RadioQuestion(
                question = "Which is your favorite language ?",
                option1 = "Kotlin",
                option2 = "Java",
                option3 = "Python",
                option4 = "Other",
                imageUrl = getImageUrl(1)
            ),

            // Favorite Project
            RadioQuestion(
                question = "Which is your favorite project ?",
                option1 = "Retrofit",
                option2 = "RxAndroid",
                option3 = "ButterKnife",
                option4 = "Other",
                imageUrl = getImageUrl(2)
            ),

            // Favorite Food
            FactualQuestion(
                question = "Which is your favorite food ?",
                imageUrl = getImageUrl(3)
            ),

            // Hobby
            CheckBoxQuestion(
                question = "What are your hobbies other than programming ?",
                option1 = "Gaming",
                option2 = "Reading",
                option3 = "Travelling",
                option4 = getString(R.string.option_none),
                imageUrl = getImageUrl(6)
            ),

            // Sleep time
            TimeQuestion(
                question = "What time do you sleep ?",
                imageUrl = getImageUrl(4)
            ),

            // Wake-up time
            TimeQuestion(
                question = "What time do you wake up?",
                imageUrl = getImageUrl(5)
            )
        )
    }

    /**
     * Returns valid image from github
     */
    private fun getImageUrl(id: Int): String? {
        require(id in 1..8) { "id should be between 1 and 8, but passed $id" }
        return "https://raw.githubusercontent.com/theapache64/ask-wharton/master/extras/photos/$id.jpg"
    }

    override fun getWelcomeMessageWithTitle(): Pair<String, String>? {
        return Pair("Hi Jake", "Thank you for trying this app! :)")
    }

    /**
     * When survery gets finished, this method will get invoked
     */
    override fun onSurveyFinished(answers: List<Answer>) {

        // Showing a thank you dialog
        val thankYouDialog = AlertDialog.Builder(this)
            .setTitle("Congratulations")
            .setCancelable(false)
            .setMessage("You've finished the interview. Next, choose an email client to send the interview data. ")
            .setPositiveButton(android.R.string.ok) { _: DialogInterface, _: Int ->
                sendEmail(answers)
            }.create()

        thankYouDialog.show()
    }

    private fun sendEmail(answers: List<Answer>) {
        val mailIntent = Intent(Intent.ACTION_SEND)
        mailIntent.data = Uri.parse("mailto:")
        mailIntent.type = "message/rfc822"
        mailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("theapache64@gmail.com"))
        mailIntent.putExtra(Intent.EXTRA_SUBJECT, "Jake Wharton Answered!")
        mailIntent.putExtra(Intent.EXTRA_TEXT, getMailBody(answers))
        startActivity(Intent.createChooser(mailIntent, "Choose email client"))
    }

    /**
     * Simple method to create mail body
     */
    private fun getMailBody(answers: List<Answer>): String? {
        val stringBuilder = StringBuilder()

        stringBuilder.append("Hi theapache64, \nI've finished your interview. \n\n")

        answers.forEach {
            val answer = if (it.answer.trim().isEmpty()) "-" else it.answer
            stringBuilder.append("Q. ${it.question.question}\nA. ${answer}\n\n")
        }

        return stringBuilder.toString()
    }


}
