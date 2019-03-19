package com.theah64.qpool.ui.activities.qpool.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.theah64.qpool.models.questions.Question
import com.theah64.qpool.ui.activities.qpool.fragments.QuestionFragment

class QuestionPagerAdapter(
    private val questions: Array<out Question>,
    fm: FragmentManager
) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return QuestionFragment.newInstance(questions[position], position, count)
    }

    override fun getCount(): Int {
        return this.questions.size
    }
}