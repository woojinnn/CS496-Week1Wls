package com.example.week1wls.ui.Gallery

import android.net.Uri
import android.widget.ImageView

data class AddImageData (
    //val img : String -> for crwaling
    val img : Uri,
    var tag : String
    )