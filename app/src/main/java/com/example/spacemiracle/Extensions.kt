package com.example.spacemiracle

import java.text.SimpleDateFormat
import java.util.*

fun Date.format():String {
    val dateFormat = SimpleDateFormat("dd-MM-yyyy")
    return dateFormat.format(this)
}