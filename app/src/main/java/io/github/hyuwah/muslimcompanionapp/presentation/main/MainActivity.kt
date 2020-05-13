package io.github.hyuwah.muslimcompanionapp.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.hyuwah.muslimcompanionapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navAdapter = MainNavigationAdapter(this)
        binding.mainViewpager.apply {
            adapter = navAdapter
        }
        binding.mainNav.setupWithViewPager2(binding.mainViewpager)
    }
}



