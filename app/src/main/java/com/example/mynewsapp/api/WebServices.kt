package com.example.mynewsapp.api

import com.example.mynewsapp.model.NewsResponse
import com.example.mynewsapp.utils.Constant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(

        @Query("country") country :String="us",
        @Query("page") pagenumber:Int,
        @Query("apiKey") apiKey:String=Constant.PIKEY
    ):Response<NewsResponse>

    @GET("v2/everything")
    suspend fun getSearchNews(

        @Query("q") q :String,
        @Query("page") pagenumber:Int,
        @Query("apiKey") apiKey:String=Constant.PIKEY
    ):Response<NewsResponse>
}