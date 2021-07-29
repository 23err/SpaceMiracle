package com.example.spacemiracle

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_state_list_animator.*

class StateListAnimatorActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_state_list_animator)
        scroll_view.setOnScrollChangeListener{ _,_,_,_,_,->
            header.isSelected = scroll_view.canScrollVertically(-1)
        }
    }
}