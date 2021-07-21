package com.example.spacemiracle

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.spacemiracle.databinding.ActivitySwipeBehaviorBinding
import com.google.android.material.behavior.SwipeDismissBehavior

class SwipeBehaviorActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivitySwipeBehaviorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySwipeBehaviorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSwipe()
    }

    private fun setSwipe() = with(binding) {
        val swipe: SwipeDismissBehavior<CardView?> = SwipeDismissBehavior<CardView?>()
        swipe.setSwipeDirection(SwipeDismissBehavior.SWIPE_DIRECTION_ANY)
        swipe.listener = object : SwipeDismissBehavior.OnDismissListener {
            override fun onDismiss(view: View?) {
                Toast.makeText(applicationContext, "Card swipe", Toast.LENGTH_SHORT).show()
            }

            override fun onDragStateChanged(state: Int) {}
        }
        val coordinatorParams = cardView.layoutParams as CoordinatorLayout.LayoutParams
        coordinatorParams.behavior = swipe


    }

}