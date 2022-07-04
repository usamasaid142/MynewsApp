package com.example.mynewsapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mynewsapp.R
import com.example.mynewsapp.databinding.ActivityMainBinding
import com.example.mynewsapp.dp.Articledatabase
import com.example.mynewsapp.utils.NewsviewmodelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
     lateinit var viewmodel:Newsviewmodel
     private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val newsrepo=NewReopsitory(Articledatabase(this))
        val viewmodelfactory=NewsviewmodelFactory(newsrepo)
     viewmodel=ViewModelProvider(this,viewmodelfactory).get(Newsviewmodel::class.java)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
         navController = navHostFragment.navController
//        val appBarConfiguration= AppBarConfiguration(setOf(R.id.brakingnewsFragment,R.id.savedNewsFragment,R.id.searchnewsFragment))
//        setupActionBarWithNavController(navController,appBarConfiguration)
        binding.bottomNavigation.setupWithNavController(navController)

    }
}