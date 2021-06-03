package com.example.projectgumi.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favorite_table")
data class FavoriteModel(
    @PrimaryKey(autoGenerate = false)
    var productId: Int,
    var name: String,
    var unit: String,
    var price: String,
    var url: String
): Serializable