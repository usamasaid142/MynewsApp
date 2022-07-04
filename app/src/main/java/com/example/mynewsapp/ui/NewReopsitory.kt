package com.example.mynewsapp.ui

import com.example.mynewsapp.api.RetrofitInstance
import com.example.mynewsapp.dp.Articledatabase
import com.example.mynewsapp.model.Article
import com.example.mynewsapp.model.NewsResponse
import retrofit2.Response

class NewReopsitory(val db:Articledatabase) {


    suspend fun getbreakingnews(country:String,pagenumber:Int):Response<NewsResponse>{

       return RetrofitInstance.api.getBreakingNews(country,pagenumber)

    }

    suspend fun searchNews(q:String,pagenumber:Int):Response<NewsResponse>{

        return RetrofitInstance.api.getSearchNews(q,pagenumber)

    }

   suspend fun savedArticels(article:Article){

        db.articleDao().insert(article)
    }

    suspend fun deleteArticle(article:Article){
        db.articleDao().delete(article)
    }

    fun getArticle()=db.articleDao().selectArticle()

}