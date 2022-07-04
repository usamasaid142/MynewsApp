package com.example.mynewsapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynewsapp.R
import com.example.mynewsapp.adapter.NewsAdapter
import com.example.mynewsapp.databinding.SavednewsfragmentBinding
import com.example.mynewsapp.model.Article
import com.example.mynewsapp.ui.MainActivity
import com.example.mynewsapp.ui.Newsviewmodel
import com.google.android.material.snackbar.Snackbar


class SavedNewsFragment:Fragment(),NewsAdapter.onArticleItemclick{


    lateinit var viewModel: Newsviewmodel
    private lateinit var binding:SavednewsfragmentBinding
    private lateinit var newsAdapter: NewsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= SavednewsfragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=(activity as MainActivity).viewmodel
        setUpRecylerviw()
        deleteArticle(view)
    }

    fun setUpRecylerviw(){
        newsAdapter= NewsAdapter(this)
        binding.rvSaved.apply {
            adapter=newsAdapter
            layoutManager=LinearLayoutManager(activity)
            setHasFixedSize(true)
        }
        viewModel.getArticles().observe(viewLifecycleOwner, Observer {
            newsAdapter.subList(it)
        })
        newsAdapter.notifyDataSetChanged()
    }


    fun deleteArticle(view: View)
    {
        val itemtouchelper=object :ItemTouchHelper.SimpleCallback(

            ItemTouchHelper.UP or  ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position=viewHolder.adapterPosition
                val article=newsAdapter.differ.currentList[position]
                viewModel.deleteArtile(article)
                Snackbar.make(view,"Article deleted successfully",Snackbar.LENGTH_SHORT).apply {
                    setAction("undo"){
                        viewModel.savedArtiles(article)
                    }
                    show()
                }
            }

        }

        ItemTouchHelper(itemtouchelper).apply {
            attachToRecyclerView(binding.rvSaved)
        }
    }

    override fun onClick(article: Article) {
        TODO("Not yet implemented")
    }

}