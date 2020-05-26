package io.github.hyuwah.muslimcompanionapp

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import io.github.hyuwah.muslimcompanionapp.di.dataModule
import io.github.hyuwah.muslimcompanionapp.di.fragmentModule
import io.github.hyuwah.muslimcompanionapp.di.networkModule
import io.github.hyuwah.muslimcompanionapp.di.repoModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MuslimCompanionApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        startKoin {
            androidContext(this@MuslimCompanionApp)
            modules(
                    networkModule,
                    repoModule,
                    dataModule,
                    fragmentModule
            )
        }
    }
}