package com.example.mynewsapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.example.mynewsapp.R
import com.example.mynewsapp.databinding.ArticlenewsfragmentBinding
import com.example.mynewsapp.model.Article
import com.example.mynewsapp.ui.MainActivity
import com.example.mynewsapp.ui.Newsviewmodel
import com.google.android.material.snackbar.Snackbar


class ArticlenewsFragment : Fragment() {

    lateinit var viewModel: Newsviewmodel
    private lateinit var binding:ArticlenewsfragmentBinding
    private val args:ArticlenewsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= ArticlenewsfragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=(activity as MainActivity).viewmodel

        val articles=args.article
        binding.webview.apply {
            webViewClient= WebViewClient()
            articles?.let {
                it.url?.let { it1 -> loadUrl(it1) }
            }
        }
        articles?.let {articles->
            setfloatingAtionbutton(articles,view)
        }

    }


    fun setfloatingAtionbutton(article: Article,view: View)
    {
        binding.btFbadd.setOnClickListener {
        viewModel.savedArtiles(article )
            Snackbar.make(view," Article saved successfully",Snackbar.LENGTH_SHORT).show()
        }
    }
}