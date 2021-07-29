package com.example.spacemiracle

import android.graphics.Rect
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.*
import com.example.spacemiracle.AnimationActivity.Adapter.ViewHolder
import com.example.spacemiracle.databinding.ActivityAnimationBinding
import kotlinx.android.synthetic.main.activity_animation.*

class AnimationActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "AnimationActivity"
    }

    private var _binding: ActivityAnimationBinding? = null
    private val binding get() = _binding!!
    private var textIsVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAnimationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setButtonAnimation()
        recyclerView.adapter = Adapter()
    }

    private fun setButtonAnimation() {
        binding.button.setOnClickListener {
            TransitionManager.beginDelayedTransition(
                binding.transitionsContainer,
                Slide(Gravity.RIGHT)
            )
            textIsVisible = !textIsVisible
            binding.text.visibility = if (textIsVisible) View.VISIBLE else View.GONE
        }
    }

    private fun explode(clickedView: View){
        val viewRect = Rect()
        clickedView.getGlobalVisibleRect(viewRect)
        val explode = Explode().apply {
            epicenterCallback = object : androidx.transition.Transition.EpicenterCallback(){
                override fun onGetEpicenter(transition: androidx.transition.Transition): Rect {
                    return viewRect
                }
            }
            duration = 1000
            excludeTarget(clickedView, true)
        }
        val set = TransitionSet().apply {
            addTransition(explode)
            addTransition(Fade().addTarget(clickedView))
            addListener(object: TransitionListenerAdapter(){
                override fun onTransitionEnd(transition: Transition) {
                    transition.removeListener(this)
                    onBackPressed()
                }
            })
        }
        TransitionManager.beginDelayedTransition(recyclerView, set)
        recyclerView.adapter = null
    }

    private inner class Adapter : RecyclerView.Adapter<ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.rv_action_item,
                    parent,
                    false
                ) as View
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.itemView.setOnClickListener{
                explode(it)
            }
        }

        override fun getItemCount(): Int {
            return 32
        }

        private inner class ViewHolder(view: View): RecyclerView.ViewHolder(view)

    }

}