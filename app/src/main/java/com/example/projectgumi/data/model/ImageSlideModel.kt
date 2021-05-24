package com.example.projectgumi.data.model

data class ImageSlideModel(
    val id: String,
    val url: String,
    val title: String? = "",
    val caption: String? = ""
)