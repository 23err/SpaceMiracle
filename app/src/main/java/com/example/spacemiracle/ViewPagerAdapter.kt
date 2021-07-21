package com.example.spacemiracle

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import kotlin.math.E

class ViewPagerAdapter(private val fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {
    private val listFragments = arrayOf(EarthFragment(), MarsFragment(), WeatherFragment())

    override fun getCount(): Int {
        return listFragments.size
    }

    override fun getItem(position: Int): Fragment {
        return listFragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Earth"
            1 -> "Mars"
            2 -> "Weather"
            else -> "Earth"
        }
    }
}