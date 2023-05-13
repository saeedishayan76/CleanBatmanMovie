package com.shayan.composeBatmanMovie.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database( version = 1, entities = [], exportSchema = true)
abstract class AppDataBase: RoomDatabase() {

}