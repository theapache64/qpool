# qpool
An android library to create a questionnaire/feedback application  

## Installation

```groovy
implementation 'com.theah64.qpool:qpool:1.0.0'
```

## Usage

1. Create a class and extend it from `QPoolActivity`

```kotlin
class MyQuestionnaireActivity : QPoolActivity() {

}
```

2. Override `abstract` methods

```kotlin

class MyQuestionnaireActivity : QPoolActivity() {
    override fun getQuestions(): Array<out Question> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSurveyFinished(answers: List<Answer>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
```

- `getQuestions` will be called when the component ready to render questions.
In this method, you should return the questions you want to display.

There are 4 types of question

|  Types            | Description                       |
|-------------------|-----------------------------------|
|RadioQuestion      | Question with single choice       |
|CheckBoxQuestion   | Question with multiple choice     |
|FactualQuestion    | Question with custom text input   |
|TimeQuestion       | Question with time input          |


- `onSurveyFinished` will be called when the survery/questionnaire got finished.
This method will give you a `List` of answers. 

3. Now try starting the `Activity` as usual

```kotlin
startActivity(Intent(this, MyQuestionnaireActivity))
```

NOTE: (Don't forgot to add the activity to `AndroidManifest` file.)

 
### Example Usage

```kotlin
class MyQuestionnaireActivity : QPoolActivity() {

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
     * When survey gets finished, this method will get invoked
     */
    override fun onSurveyFinished(answers: List<Answer>) {

        // Showing a thank you dialog
        val thankYouDialog = AlertDialog.Builder(this)
            .setTitle("Congratulations")
            .setCancelable(false)
            .setMessage("You've finished the interview.")
            .setPositiveButton(android.R.string.ok) { _: DialogInterface, _: Int ->
                doSomethingWithTheAnswers(answers)
            }.create()

        thankYouDialog.show()
    }

}
```

### Other features

If you want to show a greeting message on the activity start, you can override the 
`getWelcomeMessageWithTitle` method, and return a `Pair` of `String`. The first `String`
denotes the title and the second one denotes the message to be displayed.
 The alert message will be only displayed at the initial startup of the activity and will not show it again. 
 This is achieved with the help of `SharedPreference`: 
 
```kotlin
override fun getWelcomeMessageWithTitle(): Pair<String, String>? {
        return Pair("Hi Jake", "Thank you for trying this app! :)")
}
```

### Projects that using qpool

- [AskWharton](https://github.com/theapache64/ask-wharton)

### Author

theapache64
