package io.github.hyuwah.muslimcompanionapp.di

import io.github.hyuwah.muslimcompanionapp.presentation.ayahfetcher.AyahFetcherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val fragmentModule = module {
    viewModel { AyahFetcherViewModel(get(), get()) }
}