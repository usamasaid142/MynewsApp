package com.example.mynewsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mynewsapp.databinding.ItemArticleBinding
import com.example.mynewsapp.model.Article

class NewsAdapter( val onitemclick:onArticleItemclick) : RecyclerView.Adapter<NewsAdapter.ArticleViewholder>() {


    inner class ArticleViewholder(val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewholder {
        return ArticleViewholder(
            ItemArticleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ArticleViewholder, position: Int) {
        val articles = differ.currentList[position]
        holder.binding.apply {
            article = articles
//            setonclicklisner {
//                onitemclicklisner?.let {
//                    it(articles)
//                }


            }
        holder.itemView.setOnClickListener {
          onitemclick.onClick(articles)
        }

    }

    override fun getItemCount() = differ.currentList.size

    private val diffconfig = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffconfig)

    fun subList(list: List<Article>) = differ.submitList(list)

//    private var onitemclicklisner:((Article) -> Unit)?=null
//    fun setonclicklisner(lisner:(Article)->Unit){
//        onitemclicklisner=lisner
//    }

//    fun lisnear(onitemclick:onArticleItemclick){
//        this.onItemClickListener=onitemclick
//    }
//    private var onItemClickListener:onArticleItemclick?=null
    interface onArticleItemclick{
       fun onClick(article:Article)
    }

}