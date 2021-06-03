package com.example.projectgumi.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.projectgumi.data.model.FavoriteModel
import com.example.projectgumi.data.model.Product


@Dao
interface FavoriteDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavorite(product: FavoriteModel)

    @Query("SELECT * FROM favorite_table")
    suspend fun getAllFavorite(): MutableList<FavoriteModel>

    @Query("DELETE FROM favorite_table WHERE productId= :productId")
    suspend fun deleteFavorite(productId: Int)
}