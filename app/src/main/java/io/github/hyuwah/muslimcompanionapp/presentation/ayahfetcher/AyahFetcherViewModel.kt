package io.github.hyuwah.muslimcompanionapp.presentation.ayahfetcher

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import io.github.hyuwah.muslimcompanionapp.data.SharedPrefsManager
import io.github.hyuwah.muslimcompanionapp.data.remote.model.AyahResponse
import io.github.hyuwah.muslimcompanionapp.domain.AlQuranCloudRepository
import io.github.hyuwah.muslimcompanionapp.presentation.base.ActionStateLiveData
import io.github.hyuwah.muslimcompanionapp.presentation.base.BaseViewModel
import kotlin.math.floor
import kotlin.math.roundToInt

interface AyahFetcher {
    val ayah: ActionStateLiveData<AyahResponse>
    fun fetchRandomAyah()
    fun setAyahEdition(edition: String)
}

class AyahFetcherViewModel(
        private val repo: AlQuranCloudRepository,
        private val prefs: SharedPrefsManager
) : BaseViewModel(), AyahFetcher {

    private var edition = prefs.getString(SharedPrefsManager.Key.EDITION_KEY, "en.asad")
    private var id = prefs.getInt(SharedPrefsManager.Key.CURRENT_AYAH_ID_INT)

    override val ayah = ActionStateLiveData(viewModelScope.coroutineContext) {
        repo.getAyatByEdition(id, edition)
    }

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite = _isFavorite as LiveData<Boolean>

    fun toggleFavorite() {
        _isFavorite.postValue(!(_isFavorite.value ?: false))
    }


    init {
        if (id == 0) fetchRandomAyah() else ayah.load()
    }

    override fun fetchRandomAyah() {
        id = floor(Math.random() * (6236 - 1 + 1 + 1)).roundToInt()
        prefs.put(SharedPrefsManager.Key.CURRENT_AYAH_ID_INT, id)
        ayah.load()
    }

    override fun setAyahEdition(edition: String) {
        this.edition = edition
        prefs.put(SharedPrefsManager.Key.EDITION_KEY, edition)
        if (id == 0) fetchRandomAyah() else ayah.load()
    }

}