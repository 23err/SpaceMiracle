package com.example.spacemiracle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.ArcMotion
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import kotlinx.android.synthetic.main.activity_animation_path.*

class AnimationPathActivity : AppCompatActivity() {
    var toRightAnimation = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation_path)
        button.setOnClickListener {
            val changeBounds = ChangeBounds().apply {
                setPathMotion(
                    ArcMotion().apply {
                        minimumHorizontalAngle = 90f
                        minimumVerticalAngle = 90f
                    })
                duration = 500

            }
            TransitionManager.beginDelayedTransition(
                container,
                changeBounds
            )

            toRightAnimation = !toRightAnimation
            val constraintSet = ConstraintSet()
            constraintSet.clone(container)
            constraintSet.clear(R.id.button)
            constraintSet.constrainWidth(R.id.button, ViewGroup.LayoutParams.WRAP_CONTENT)
            constraintSet.constrainHeight(R.id.button, ViewGroup.LayoutParams.WRAP_CONTENT)

            if (toRightAnimation) {
                constraintSet.connect(
                    R.id.button,
                    ConstraintSet.END,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.END,
                    10
                )
                constraintSet.connect(
                    R.id.button,
                    ConstraintSet.BOTTOM,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.BOTTOM,
                    10
                )
            }
            constraintSet.applyTo(container)
        }
    }

    companion object {
        private const val TAG = "AnimationPathActivity"
    }
}