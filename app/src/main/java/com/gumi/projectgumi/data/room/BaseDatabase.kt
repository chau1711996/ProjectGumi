package com.gumi.gumiproject8.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gumi.projectgumi.data.model.CartModel
import com.gumi.projectgumi.data.room.CartDAO

@Database(entities = [CartModel::class], version = 7, exportSchema = false)
abstract class BaseDatabase: RoomDatabase() {
    abstract fun cartDAO(): CartDAO
}