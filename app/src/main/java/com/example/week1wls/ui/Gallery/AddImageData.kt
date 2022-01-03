package com.example.week1wls.ui.Gallery

import android.net.Uri
import android.widget.ImageView

data class AddImageData (
    //val img : String -> for crwaling
    val pageURL : String,
    val tags: String,
    val imageURL: String,
    val views: Int,
    val downloads: Int,
    val likes: Int
    )