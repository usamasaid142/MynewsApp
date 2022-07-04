package com.example.mynewsapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynewsapp.R
import com.example.mynewsapp.adapter.NewsAdapter
import com.example.mynewsapp.databinding.SearchnewsfragmentBinding
import com.example.mynewsapp.model.Article
import com.example.mynewsapp.ui.MainActivity
import com.example.mynewsapp.ui.Newsviewmodel
import com.example.mynewsapp.utils.Constant
import com.example.mynewsapp.utils.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchnewsFragment : Fragment(),NewsAdapter.onArticleItemclick {

    lateinit var viewModel: Newsviewmodel
    private lateinit var binding:SearchnewsfragmentBinding
    private lateinit var newsAdapter: NewsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= SearchnewsfragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=(activity as MainActivity).viewmodel
        setupresyclerview()
       getSearchnews()
    }



    fun setupresyclerview(){
        newsAdapter= NewsAdapter(this)
        binding.rvSearchnews.apply {
            adapter=newsAdapter
            layoutManager= LinearLayoutManager(requireContext())
            addOnScrollListener(this@SearchnewsFragment.scrolllisner)
            setHasFixedSize(true)
            newsAdapter.notifyDataSetChanged()

        }
    }

    fun getSearchnews()
    {

        var job:Job?=null
        binding.etSearch.addTextChangedListener {
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                it?.let {
                    if(it.isNotEmpty()){
                        viewModel.getSearchNews(it.toString())
                    }
                }

            }
        }



        viewModel.searchnews.observe(viewLifecycleOwner, Observer {response->
            when(response){
                is Resource.sucess->{
                    hideprogressbar()
                    response.data?.let {
                        newsAdapter.subList(it.articles.toList())
                        val totalpages=it.totalResults/ Constant.query_page_size +2
                        isLastPage=viewModel.pagenumber==totalpages
                        if (isLastPage)
                        {
                            binding.rvSearchnews.setPadding(0,0,0,0)
                        }
                    }

                }
                is Resource.Error->{
                    hideprogressbar()
                    response.message?.let {
                        Log.e("Searching news",it)
                    }
                }

                is Resource.Loading->{
                    showprogtessbar()
                }
            }

        })
    }

    private fun showprogtessbar() {
        binding.progressBar.visibility =View.VISIBLE
    isLoading=true
    }

    private fun hideprogressbar() {
        binding.progressBar.visibility =View.INVISIBLE
        isLoading=false
    }


    var isLoading=false
    var isLastPage=false
    var isScrolling=false

    val scrolllisner = object : RecyclerView.OnScrollListener(){
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            if (newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
            {
                isScrolling=true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutmanager= recyclerView.layoutManager as LinearLayoutManager
            val firstvisibleitemposition= layoutmanager.findFirstVisibleItemPosition()
            val visibleitemcount= layoutmanager.childCount
            val totalitemcount= layoutmanager.itemCount

            val isnotloadingandisnotlastpage=!isLoading && !isLastPage
            val isAtlastitem= firstvisibleitemposition+visibleitemcount>=totalitemcount
            val isnotAtthebeging=firstvisibleitemposition>=0
            val istotalmorethanvisible=totalitemcount>= Constant.query_page_size
            val shouldpaginate= isnotloadingandisnotlastpage && isAtlastitem && isnotAtthebeging && istotalmorethanvisible && isScrolling
            if(shouldpaginate)
            {
                viewModel.getSearchNews(binding.etSearch.toString())
                isScrolling=false
            }

        }

    }


    override fun onClick(article: Article) {
        val action= SearchnewsFragmentDirections.actionSearchnewsFragmentToArticlenewsFragment(article)
        findNavController().navigate(action)
    }

}