package io.github.hyuwah.muslimcompanionapp.Presenter

import io.github.hyuwah.muslimcompanionapp.Contract.AyahFetcherContract
import io.github.hyuwah.muslimcompanionapp.Contract.AyahFetcherContract.Presenter
import io.github.hyuwah.muslimcompanionapp.Model.Entity.Ayah
import io.github.hyuwah.muslimcompanionapp.Model.Network.AlquranCloudService
import io.github.hyuwah.muslimcompanionapp.Model.Network.ServiceGenerator
import io.github.hyuwah.muslimcompanionapp.Model.SharedPrefsManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AyahFetcherPresenter(private var view: AyahFetcherContract.View?) : Presenter {

    override fun fetchRandomAyah() {
        val id = Math.floor(Math.random() * (6236 - 1 + 1 + 1)).toInt() // Generate id from 1 to 6236
        fetchAyah(id)
    }

    override fun fetchAyah(id: Int) {
        val service = ServiceGenerator.createService(AlquranCloudService::class.java)
        val edition = SharedPrefsManager.getInstance().getString(SharedPrefsManager.Key.EDITION_KEY, "en.asad")
        val call = service.getAyahByEdition(id, edition)
        view?.showLoading() // Show loading
        call.enqueue(object : Callback<Ayah?> {
            override fun onResponse(call: Call<Ayah?>, response: Response<Ayah?>) {
                view?.fetchSuccess()
                if (response.body() != null) {
                    view?.showResult(response.body()!!.data)
                    // save current ayah id to prefs
                    SharedPrefsManager.getInstance().put(SharedPrefsManager.Key.CURRENT_AYAH_ID_INT, response.body()!!.data.number)
                } else {
                    view?.fetchFailed()
                }
            }

            override fun onFailure(call: Call<Ayah?>, t: Throwable) {
                view?.fetchFailed()
            }
        })
    }

    override fun setAyahEdition(edition: String) {
        SharedPrefsManager.getInstance().put(SharedPrefsManager.Key.EDITION_KEY, edition)
    }

    fun detachView() {
        view = null
    }

}