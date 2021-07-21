package com.example.spacemiracle

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MarsPhoto(
    val urlImage:String,
    val name:String,
    val fullName:String,
    val sol:Int,
    val earthDate:String,
) : Parcelable