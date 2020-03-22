package io.github.hyuwah.muslimcompanionapp.Contract

import io.github.hyuwah.muslimcompanionapp.Model.Entity.Ayah

interface AyahFetcherContract {
    interface View {
        fun fetchSuccess()
        fun fetchFailed()
        fun showLoading()
        fun showResult(ayah: Ayah.Data)
        fun toggleShareButton(toggle: Boolean)
        fun toggleFavButton(toggle: Boolean)
    }

    interface Presenter {
        fun fetchRandomAyah()
        fun fetchAyah(id: Int)
        fun setAyahEdition(edition: String)
    }
}