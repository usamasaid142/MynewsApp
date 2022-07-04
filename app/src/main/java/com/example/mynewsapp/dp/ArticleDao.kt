package com.example.mynewsapp.dp

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mynewsapp.model.Article

@Dao
interface ArticleDao {

    @Insert( onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article):Long

    @Delete
    suspend fun delete(article: Article)

    @Query("select * from Articles")
    fun selectArticle():LiveData<List<Article>>
}