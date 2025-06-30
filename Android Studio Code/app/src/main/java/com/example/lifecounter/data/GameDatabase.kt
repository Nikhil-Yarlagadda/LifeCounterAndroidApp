package com.example.lifecounter.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Player::class, GameStateEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class GameDatabase:  RoomDatabase(){
    abstract fun gameDao(): GameDao

    companion object{
        @Volatile
        private var instance: GameDatabase? = null

        fun getDatabase(context: Context): GameDatabase{
            return instance ?: synchronized(this){
                Room.databaseBuilder(context, GameDatabase::class.java, "game_database").build()
                    .also{instance = it}            }
        }
    }
}