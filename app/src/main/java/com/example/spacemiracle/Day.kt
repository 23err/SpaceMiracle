package com.example.spacemiracle

import java.text.SimpleDateFormat
import java.util.*

enum class Day {
    TODAY, YESTERDAY, BEFORE_DAY;

    override fun toString(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val date = Calendar.getInstance()
        date.time = Date()
        when (this.name) {
            YESTERDAY.name -> {
                date.add(Calendar.DATE, -1)
            }
            BEFORE_DAY.name -> {
                date.add(Calendar.DATE, -2)
            }
        }
        return dateFormat.format(date.time)
    }
}
