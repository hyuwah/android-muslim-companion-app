package io.github.hyuwah.muslimcompanionapp.presentation.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import io.github.hyuwah.muslimcompanionapp.presentation.ayahfetcher.AyahFetcherFragment
import io.github.hyuwah.muslimcompanionapp.presentation.prayertimes.PrayerTimesFragment

class MainNavigationAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    private val items = mutableListOf(
            AyahFetcherFragment(),
            PrayerTimesFragment(),
            AyahFetcherFragment()
    )

    override fun getItemCount(): Int {
        return items.size
    }

    override fun createFragment(position: Int): Fragment {
        return items[position]
    }

}