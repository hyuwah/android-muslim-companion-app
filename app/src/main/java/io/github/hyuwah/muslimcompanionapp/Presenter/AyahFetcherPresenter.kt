package io.github.hyuwah.muslimcompanionapp.Presenter

import io.github.hyuwah.muslimcompanionapp.Contract.AyahFetcherContract
import io.github.hyuwah.muslimcompanionapp.Contract.AyahFetcherContract.Presenter
import io.github.hyuwah.muslimcompanionapp.Model.SharedPrefsManager
import io.github.hyuwah.muslimcompanionapp.domain.AlQuranCloudRepository
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class AyahFetcherPresenter(private var view: AyahFetcherContract.View?) : KoinComponent, Presenter {

    private val repository: AlQuranCloudRepository by inject()
    private var job: Job? = null

    override fun fetchRandomAyah() {
        val id = Math.floor(Math.random() * (6236 - 1 + 1 + 1)).toInt() // Generate id from 1 to 6236
        fetchAyah(id)
    }

    override fun fetchAyah(id: Int) {
        val edition = SharedPrefsManager.getInstance().getString(SharedPrefsManager.Key.EDITION_KEY, "en.asad")
        view?.showLoading() // Show loading
        job = GlobalScope.launch(Dispatchers.IO) {
            val response = repository.getAyatByEdition(id, edition)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful && response.body() != null) {
                    view?.showResult(response.body()!!.data)
                    // save current ayah id to prefs
                    SharedPrefsManager.getInstance().put(SharedPrefsManager.Key.CURRENT_AYAH_ID_INT, response.body()!!.data.number)
                } else {
                    view?.fetchFailed()
                }
            }
        }
    }

    override fun setAyahEdition(edition: String) {
        SharedPrefsManager.getInstance().put(SharedPrefsManager.Key.EDITION_KEY, edition)
    }

    fun detachView() {
        job?.cancel()
        job = null
        view = null
    }

}