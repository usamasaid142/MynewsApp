package com.example.mynewsapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynewsapp.api.RetrofitInstance
import com.example.mynewsapp.dp.Articledatabase
import com.example.mynewsapp.model.Article
import com.example.mynewsapp.model.NewsResponse
import com.example.mynewsapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class Newsviewmodel( val newrepository:NewReopsitory):ViewModel() {

     val breakingnews:MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
     val searchnews:MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
     var breakingnewsresponse:NewsResponse?=null
    var searchnewsresponse:NewsResponse?=null
    var pagenumber:Int=1
    val country="eg"
    fun getbreakingnews()=viewModelScope.launch(Dispatchers.IO) {

        breakingnews.postValue(Resource.Loading())
        val response = newrepository.getbreakingnews(country,pagenumber)

        breakingnews.postValue(handlebreakingnews(response))
//        if (response.isSuccessful){
//           response.body()?.let {
//                breakingnews.postValue( Resource.sucess(it))
//            }
//
//        }else
//        {
//            breakingnews.postValue(Resource.Error(response.message()))
//        }

    }


    private fun handlebreakingnews(response:Response<NewsResponse>):Resource<NewsResponse>{

        if (response.isSuccessful){
            response.body()?.let { resultresponse->
                pagenumber++
                if (breakingnewsresponse==null)
                {
                    breakingnewsresponse=resultresponse
                }else
                {
                    val oldarticles=breakingnewsresponse?.articles
                    val newsarticles=resultresponse.articles
                    oldarticles?.addAll(newsarticles)
                }
               return Resource.sucess(breakingnewsresponse?:resultresponse)
            }
        }
        return Resource.Error(response.message())
    }


    fun getSearchNews(q:String)=viewModelScope.launch(Dispatchers.IO){
        searchnews.postValue(Resource.Loading())
        val response= newrepository.searchNews(q,pagenumber)
         searchnews.postValue(handelSearchnews(response))
    }

    private fun handelSearchnews(response: Response<NewsResponse>):Resource<NewsResponse>{
        if (response.isSuccessful)
        {
            response.body()?.let { resultresponse->
                pagenumber++
                if (searchnewsresponse==null)
                {
                    searchnewsresponse=resultresponse
                }else
                {
                    val oldarticles=searchnewsresponse?.articles
                    val newsarticles=resultresponse.articles
                    oldarticles?.addAll(newsarticles)
                }
                return Resource.sucess(searchnewsresponse?:resultresponse)
        }
        }

        return Resource.Error(response.message())
    }

    fun savedArtiles(article: Article)=viewModelScope.launch (Dispatchers.IO){

        newrepository.savedArticels(article)
    }

    fun deleteArtile(article: Article){
        viewModelScope.launch(Dispatchers.IO) {
            newrepository.deleteArticle(article)
        }
    }

    fun getArticles()=newrepository.getArticle()

}