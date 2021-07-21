package com.example.spacemiracle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.spacemiracle.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private var _binding : ActivitySecondBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewPager = binding.viewPager
        viewPager.adapter = ViewPagerAdapter(supportFragmentManager)
        binding.indicator.setViewPager(viewPager)
        binding.tabLayout.setupWithViewPager(viewPager)
    }
}