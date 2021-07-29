package com.example.spacemiracle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import kotlinx.android.synthetic.main.activity_mixing.*

class MixingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mixing)
        val titles:MutableList<String> = ArrayList()

        for (i in 0..4) {
            titles.add(String.format("Item %d", i+1))
            createViews(transitionsContainer, titles)
            button.setOnClickListener{
                TransitionManager.beginDelayedTransition(
                    transitionsContainer,
                    ChangeBounds()
                )
                titles.shuffle()
                createViews(transitionsContainer, titles)
            }
        }

    }

    private fun createViews(layout: ViewGroup, titles: List<String>){
        layout.removeAllViews()
        for (title in titles) {
            if (titles.indexOf(title) % 2 == 0){
                val textView = Button(this).apply {
                    text = title
                    gravity = Gravity.CENTER_HORIZONTAL
                }
                ViewCompat.setTransitionName(textView, title)
                layout.addView(textView)
            } else {
                val textView = TextView(this).apply {
                    text = title
                    gravity = Gravity.CENTER_HORIZONTAL
                }
                ViewCompat.setTransitionName(textView, title)
                layout.addView(textView)
            }


        }
    }
}