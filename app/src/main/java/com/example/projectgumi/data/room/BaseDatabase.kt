package com.example.gumiproject8.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.projectgumi.data.model.CartModel
import com.example.projectgumi.data.model.FavoriteModel
import com.example.projectgumi.data.model.Product
import com.example.projectgumi.data.room.CartDAO
import com.example.projectgumi.data.room.FavoriteDAO

@Database(entities = [CartModel::class,FavoriteModel::class], version = 6, exportSchema = false)
abstract class BaseDatabase: RoomDatabase() {
    abstract fun cartDAO(): CartDAO
    abstract fun favoriteDAO(): FavoriteDAO
}