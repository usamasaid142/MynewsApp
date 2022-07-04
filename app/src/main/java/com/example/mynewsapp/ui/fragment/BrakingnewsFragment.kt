package com.example.mynewsapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynewsapp.R
import com.example.mynewsapp.adapter.NewsAdapter
import com.example.mynewsapp.databinding.BrakingnewsfragmentBinding
import com.example.mynewsapp.model.Article
import com.example.mynewsapp.ui.MainActivity
import com.example.mynewsapp.ui.Newsviewmodel
import com.example.mynewsapp.utils.Constant.Companion.query_page_size
import com.example.mynewsapp.utils.Resource


class BrakingnewsFragment : Fragment(),NewsAdapter.onArticleItemclick {
    lateinit var viewModel: Newsviewmodel
    private lateinit var binding:BrakingnewsfragmentBinding
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= BrakingnewsfragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=(activity as MainActivity).viewmodel
        setupresyclerview()
        getbreakingnews()
//        onclick()
//        newsAdapter.setonclicklisner {
//            val bundle=Bundle().apply {
//                putSerializable("article",it)
//            }
//
//          val action=BrakingnewsFragmentDirections.actionBrakingnewsFragmentToArticlenewsFragment(it)
//            findNavController().navigate(action)
//           // findNavController().navigate(R.id.action_brakingnewsFragment_to_articlenewsFragment,bundle)
//
//        }
    }

    fun setupresyclerview(){
        newsAdapter= NewsAdapter(this)
        binding.rvBreakingnews.apply {
            adapter=newsAdapter
            layoutManager=LinearLayoutManager(requireContext())
            addOnScrollListener(this@BrakingnewsFragment.scrolllisner)
            setHasFixedSize(true)
            newsAdapter.notifyDataSetChanged()
        }
    }

    fun getbreakingnews()
    {
        viewModel.getbreakingnews()
        viewModel.breakingnews.observe(viewLifecycleOwner, Observer {response->
           when(response){
              is Resource.sucess->{
                   hideprogressbar()
                  response.data?.let {
                      newsAdapter.subList(it.articles.toList())

                      val totalpages=it.totalResults/ query_page_size+2
                      isLastPage=viewModel.pagenumber==totalpages
                      if (isLastPage)
                      {
                          binding.rvBreakingnews.setPadding(0,0,0,0)
                      }
                  }

               }
               is Resource.Error->{
                   hideprogressbar()
                   response.message?.let {
                       Log.e("Breaking news",it)
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

    val scrolllisner = object :RecyclerView.OnScrollListener(){
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
            val istotalmorethanvisible=totalitemcount>=query_page_size
            val shouldpaginate= isnotloadingandisnotlastpage && isAtlastitem && isnotAtthebeging && istotalmorethanvisible && isScrolling
            if(shouldpaginate)
            {
                viewModel.getbreakingnews()
                isScrolling=false
            }

        }

    }


    override fun onClick(article: Article) {

        val bundle=Bundle().apply {
            putSerializable("article",article)
        }
      article?.let {
          val action= BrakingnewsFragmentDirections.actionBrakingnewsFragmentToArticlenewsFragment(it)
          Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
              .navigate(BrakingnewsFragmentDirections.actionBrakingnewsFragmentToArticlenewsFragment(article))
      }



    }

//    fun onclick()
//    {
//        newsAdapter.lisnear(object :NewsAdapter.onArticleItemclick{
//            override fun onClick(article: Article) {
//                val navController= findNavController()
//                val action=BrakingnewsFragmentDirections.actionBrakingnewsFragmentToArticlenewsFragment(article)
//                navController.navigate(action)
//            }
//
//        })
//    }
//


}