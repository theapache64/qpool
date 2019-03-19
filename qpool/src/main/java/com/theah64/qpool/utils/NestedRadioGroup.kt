package com.theah64.qpool.utils

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.annotation.RequiresApi


class NestedRadioGroup : RadioGroup {

    private var clickedView: View? = null

    private val radioButtons = mutableListOf<RadioButton>()

    val checkedRadioButton: RadioButton?
        get() {
            for (rb in radioButtons) {
                if (rb.isChecked) {
                    return rb
                }
            }
            return null
        }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (Build.VERSION.SDK_INT < 16) {
                    removeLayoutListenerPre16(viewTreeObserver, this)
                } else {
                    removeLayoutListenerPost16(viewTreeObserver, this)
                }
                init()
            }
        })
    }

    private fun removeLayoutListenerPre16(
        observer: ViewTreeObserver,
        listener: ViewTreeObserver.OnGlobalLayoutListener
    ) {
        observer.removeGlobalOnLayoutListener(listener)
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private fun removeLayoutListenerPost16(
        observer: ViewTreeObserver,
        listener: ViewTreeObserver.OnGlobalLayoutListener
    ) {
        observer.removeOnGlobalLayoutListener(listener)
    }

    private fun init() {

        Log.d("NestedRadioGroup", "initializing nested radio groups")

        radioButtons.addAll(getAllRadioButtons(this))

        Log.d("NestedRadioGroup", "Found: " + radioButtons.size)

        for (rb in radioButtons) {

            Log.d("NestedRadioGroup", "Setting listener for : " + rb.getTag())
            rb.setOnClickListener { view ->
                System.out.println("Clicked on :" + view.tag)
                clickedView = view
                clearCheck()
            }
        }
    }

    override fun getCheckedRadioButtonId(): Int {

        for (rb in radioButtons) {
            if (rb.isChecked) {
                return rb.id
            }
        }
        return -1
    }

    override fun clearCheck() {

        for (rb in radioButtons) {
            rb.isChecked = !(clickedView == null || rb.id != clickedView!!.getId())
        }

    }

    private fun getAllRadioButtons(viewGroup: ViewGroup): Collection<RadioButton> {
        val radioButtons = mutableListOf<RadioButton>()
        Log.d("NestedRadioGroup", "Child count :" + viewGroup.childCount)
        for (i in 0 until viewGroup.childCount) {
            val view = viewGroup.getChildAt(i)
            if (view is RadioButton) {
                Log.d("NestedRadioGroup", "Found radio button at $i")
                radioButtons.add(view)
            } else if (view is ViewGroup) {
                radioButtons.addAll(getAllRadioButtons(view))
            }
        }
        return radioButtons
    }
}