package io.github.hyuwah.muslimcompanionapp.di

import io.github.hyuwah.muslimcompanionapp.data.RepositoryImpl
import io.github.hyuwah.muslimcompanionapp.data.SharedPrefsManager
import io.github.hyuwah.muslimcompanionapp.domain.AlQuranCloudRepository
import io.github.hyuwah.muslimcompanionapp.domain.AladhanRepository
import org.koin.dsl.module

val dataModule = module {

    single { SharedPrefsManager(get()) }

}

val repoModule = module {

    single { RepositoryImpl(get(), get()) }

    single<AlQuranCloudRepository> { get<RepositoryImpl>() }
    single<AladhanRepository> { get<RepositoryImpl>() }

}