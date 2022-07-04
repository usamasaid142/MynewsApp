package com.example.mynewsapp.model


import androidx.databinding.BindingAdapter
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bumptech.glide.Glide
import com.example.mynewsapp.R
import com.google.android.material.imageview.ShapeableImageView
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "Articles")
data class Article(
    @PrimaryKey(autoGenerate = true)
    var id:Int?,
    @SerializedName("author")
    val author: String?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("publishedAt")
    val publishedAt: String?,
    @SerializedName("source")
    val source: Source?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("urlToImage")
    val urlToImage: String?
):Serializable

@BindingAdapter("load_image")
fun loadImage(view: ShapeableImageView, url: String?){

    url.let {
        Glide.with(view.context).load(it).placeholder(R.drawable.ic_favorite)
            .into(view)
    }

}