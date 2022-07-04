package com.example.mynewsapp.dp

import androidx.room.TypeConverter
import com.example.mynewsapp.model.Source

class Converters {
    @TypeConverter
    fun fromsource(source:Source)=source.name
    @TypeConverter
    fun tosource(name:String)=Source(name,name)
}