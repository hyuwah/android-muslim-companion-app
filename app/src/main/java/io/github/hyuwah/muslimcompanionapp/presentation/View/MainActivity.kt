package io.github.hyuwah.muslimcompanionapp.presentation.View

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import io.github.hyuwah.muslimcompanionapp.R
import io.github.hyuwah.muslimcompanionapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Setup Toolbar & Drawer Layout
        setSupportActionBar(binding.appBarMain.toolbar)
        val toggle = ActionBarDrawerToggle(
                this, binding.drawerLayout, binding.appBarMain.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        binding.navView.setNavigationItemSelectedListener(this)
        binding.navView.setCheckedItem(R.id.nav_ayat_fetcher)
        // Set default screen
        supportFragmentManager.beginTransaction().replace(R.id.view_fragment, AyahFetcherView()).commit()
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean { // Handle navigation view item clicks here.
        val fragment: Fragment?
        var fragmentClass: Class<*>? = null
        when (item.itemId) {
            R.id.nav_ayat_fetcher -> fragmentClass = AyahFetcherView::class.java
            R.id.nav_share -> {
                val message = "Download Muslim Companion Apps di [insert link google play]!\nBe a better muslim."
                val share = Intent(Intent.ACTION_SEND)
                share.type = "text/plain"
                share.putExtra(Intent.EXTRA_TEXT, message)
                val chooser = Intent.createChooser(share, "Share this apps")
                Log.i(localClassName, "onNavigationItemSelected: " + "Touchdown!")
                if (share.resolveActivity(packageManager) != null) {
                    startActivity(chooser)
                }
            }
            else -> fragmentClass = AyahFetcherView::class.java
        }
        if (fragmentClass != null) {
            try {
                fragment = fragmentClass.newInstance() as Fragment
                supportFragmentManager.beginTransaction().replace(R.id.view_fragment, fragment).commit()
                item.isChecked = true
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}