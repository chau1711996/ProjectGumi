package com.example.projectgumi.data.model

data class ImageSlideModel(
    val id: Int,
    val url: String,
    val title: String? = "",
    val caption: String? = ""
)