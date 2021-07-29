package com.example.spacemiracle

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import androidx.transition.Slide
import androidx.transition.Transition
import androidx.transition.TransitionManager
import kotlinx.android.synthetic.main.activity_animation.view.*
import java.text.SimpleDateFormat
import java.util.*

fun Date.format():String {
    val dateFormat = SimpleDateFormat("dd-MM-yyyy")
    return dateFormat.format(this)
}

fun TextView.animatedChangeText(container: ViewGroup, text: String?){
    TransitionManager.beginDelayedTransition(container)
    this.visibility = View.INVISIBLE
    this.text = text
    this.visibility = View.VISIBLE
}