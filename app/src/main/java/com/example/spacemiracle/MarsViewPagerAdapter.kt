package com.example.spacemiracle

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.spacemiracle.repository.Photo

class MarsViewPagerAdapter(fragmentManager:FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {
    var list : List<Photo> = ArrayList<Photo>()
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Fragment {
        val responseData = list[position]
        val marsPhoto = MarsPhoto(
            responseData.imgSrc,
            responseData.camera.name,
            responseData.camera.fullName,
            responseData.sol,
            responseData.earthDate,
        )
        return MarsViewPagerFragment.newInstance(marsPhoto)
    }

    companion object{
        private const val TAG = "MarsViewPagerAdapter"
    }
}