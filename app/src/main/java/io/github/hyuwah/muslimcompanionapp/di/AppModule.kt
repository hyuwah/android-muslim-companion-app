package io.github.hyuwah.muslimcompanionapp.di

import io.github.hyuwah.muslimcompanionapp.data.RepositoryImpl
import io.github.hyuwah.muslimcompanionapp.domain.AlQuranCloudRepository
import io.github.hyuwah.muslimcompanionapp.domain.MuslimSalatRepository
import org.koin.dsl.module

val repoModule = module {

    single { RepositoryImpl(get()) }

    single { get<RepositoryImpl>() as AlQuranCloudRepository }
    single { get<RepositoryImpl>() as MuslimSalatRepository }

}