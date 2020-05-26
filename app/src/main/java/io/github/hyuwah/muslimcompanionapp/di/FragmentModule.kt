package io.github.hyuwah.muslimcompanionapp.di

import io.github.hyuwah.muslimcompanionapp.presentation.ayahfetcher.AyahFetcherViewModel
import io.github.hyuwah.muslimcompanionapp.presentation.prayertimes.PrayerTimesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val fragmentModule = module {
    viewModel { AyahFetcherViewModel(get(), get()) }
    viewModel { PrayerTimesViewModel(get()) }
}