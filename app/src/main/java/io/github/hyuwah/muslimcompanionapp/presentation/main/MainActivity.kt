package io.github.hyuwah.muslimcompanionapp.presentation.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.github.hyuwah.muslimcompanionapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var exitToast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navAdapter = MainNavigationAdapter(this)
        binding.mainViewpager.apply {
            adapter = navAdapter
        }
        binding.mainNav.setupWithViewPager2(binding.mainViewpager)
    }

    override fun onBackPressed() {
        if (exitToast == null || exitToast!!.view == null || exitToast!!.view.windowToken == null) {
            exitToast = Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT)
            exitToast!!.show()
        } else {
            exitToast!!.cancel()
            super.onBackPressed()
        }
    }
}



