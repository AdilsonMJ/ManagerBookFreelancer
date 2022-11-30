package com.example.managerbookfreelancer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.managerbookfreelancer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setUpBottonNavigationView()

    }



    private fun setUpBottonNavigationView() {

        val navHostFragment = getNavHostFragment()
        binding.bottomAppBar.setupWithNavController(navHostFragment.navController)
    }

    private fun getNavHostFragment(): NavHostFragment = supportFragmentManager.findFragmentById(binding.fragmentContainerView.id) as NavHostFragment


}