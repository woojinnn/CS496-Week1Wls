package com.example.week1wls.ui.healthcare

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Profile(
    val name: String,
    val height: Double,
    val weight: Double,
    val age: Int,
    val isMale: Boolean,
    val bmr: Double,
    val total: Double
    ): Parcelable
