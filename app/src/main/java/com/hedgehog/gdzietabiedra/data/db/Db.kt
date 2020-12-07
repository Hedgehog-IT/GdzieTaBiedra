package com.hedgehog.gdzietabiedra.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hedgehog.gdzietabiedra.data.db.recipes.RecipeDao
import com.hedgehog.gdzietabiedra.data.db.recipes.RecipeEntity
import com.hedgehog.gdzietabiedra.data.db.shops.ShopRoomDao
import com.hedgehog.gdzietabiedra.data.db.shops.ShopRoomEntity

@Database(entities = [
    ShopRoomEntity::class,
    RecipeEntity::class
], version = 2)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun shopRoomDao(): ShopRoomDao
    abstract fun recipeDao(): RecipeDao
}
