package com.example.mynewsapp.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mynewsapp.ui.NewReopsitory
import com.example.mynewsapp.ui.Newsviewmodel

class NewsviewmodelFactory (val newsrepository:NewReopsitory): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return Newsviewmodel(newsrepository) as T
    }


}