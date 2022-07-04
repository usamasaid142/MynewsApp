package com.example.mynewsapp.dp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mynewsapp.model.Article

private const val db="articledb"
@Database(entities = [Article::class],version = 1,exportSchema = false)
@TypeConverters(Converters::class)
abstract class Articledatabase:RoomDatabase() {

    abstract fun articleDao():ArticleDao

    companion object{
        @Volatile
        private var instance:Articledatabase?=null

        private val lock= Any()
        operator fun invoke(context: Context)= instance?:synchronized(lock){
                instance?: createDatabase(context).also{ instance=it}
        }

        private fun createDatabase(context: Context)=
         Room.databaseBuilder(context.applicationContext,Articledatabase::class.java,db).build()
    }
}