package io.github.hyuwah.muslimcompanionapp.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.hyuwah.muslimcompanionapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navAdapter = MainNavigationAdapter(this)
        binding.mainViewpager.apply {
            adapter = navAdapter
        }
        binding.mainNav.setupWithViewPager2(binding.mainViewpager)
    }
}



