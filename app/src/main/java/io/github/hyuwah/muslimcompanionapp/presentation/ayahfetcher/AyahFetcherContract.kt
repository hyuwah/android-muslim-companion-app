package io.github.hyuwah.muslimcompanionapp.presentation.ayahfetcher

import io.github.hyuwah.muslimcompanionapp.data.remote.model.AyahResponse

interface AyahFetcherContract {
    interface View {
        fun fetchFailed()
        fun showLoading()
        fun showResult(ayah: AyahResponse.Data)
        fun toggleShareButton(toggle: Boolean)
        fun toggleFavButton(toggle: Boolean)
    }

    interface Presenter {
        fun fetchRandomAyah()
        fun fetchAyah(id: Int)
        fun setAyahEdition(edition: String)
    }
}