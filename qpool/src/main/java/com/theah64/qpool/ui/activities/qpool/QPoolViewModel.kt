package com.theah64.qpool.ui.activities.qpool

import androidx.lifecycle.ViewModel
import com.theah64.qpool.models.Answer

class QPoolViewModel : ViewModel() {
    val answersMap = mutableMapOf<Int, Answer>()
}