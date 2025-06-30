package com.example.lifecounter.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromIntList(value: List<Int>): String{
        return gson.toJson(value)
    }

    @TypeConverter
    fun toIntList(value: String): List<Int> {
        val listType = object : TypeToken<List<Int>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromCounterList(value: List<Counter>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toCounterList(value: String): List<Counter> {
        val listType = object : TypeToken<List<Counter>>() {}.type
        return gson.fromJson(value, listType)
    }
}