package com.example.week1wls.ui.Gallery

data class AddImageData(
    val pageURL: String,
    val tags: String,
    val imageURL: String,
    val views: Int,
    val downloads: Int,
    val likes: Int
)