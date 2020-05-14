package io.github.hyuwah.muslimcompanionapp.presentation.ayahfetcher

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import io.github.hyuwah.muslimcompanionapp.data.SharedPrefsManager
import io.github.hyuwah.muslimcompanionapp.data.remote.model.AyahResponse
import io.github.hyuwah.muslimcompanionapp.domain.AlQuranCloudRepository
import io.github.hyuwah.muslimcompanionapp.presentation.ayahfetcher.AyahFetcher.UiState
import io.github.hyuwah.muslimcompanionapp.presentation.base.BaseViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlin.math.floor
import kotlin.math.roundToInt

interface AyahFetcher {
    fun fetchAyah(id: Int)
    fun fetchRandomAyah()
    fun setAyahEdition(edition: String)
    sealed class UiState {
        object Loading : UiState()
        data class Success(val ayah: AyahResponse) : UiState()
        data class Failed(val message: String) : UiState()
    }
}

class AyahFetcherViewModel(
        private val repo: AlQuranCloudRepository,
        private val prefs: SharedPrefsManager
) : BaseViewModel(), AyahFetcher {

    private val _uiState = MutableLiveData<UiState>(UiState.Loading)
    val uiState = _uiState as LiveData<UiState>

    private val defaultErrorHandler = CoroutineExceptionHandler { _, e ->
        _uiState.postValue(UiState.Failed(e.message.orEmpty()))
    }

    override fun fetchAyah(id: Int) {
        if (id == 0) fetchRandomAyah()
        val edition = prefs.getString(SharedPrefsManager.Key.EDITION_KEY, "en.asad")
        viewModelScope.launch(defaultErrorHandler) {
            _uiState.postValue(UiState.Loading)
            val result = repo.getAyatByEdition(id, edition)
            if (result.isSuccessful && result.body() != null) {
                val ayah = result.body()!!
                _uiState.postValue(UiState.Success(ayah))
                prefs.put(SharedPrefsManager.Key.CURRENT_AYAH_ID_INT, ayah.data.number)
            } else {
                _uiState.postValue(UiState.Failed(result.message()))
            }
        }
    }

    override fun fetchRandomAyah() {
        val id = floor(Math.random() * (6236 - 1 + 1 + 1)).roundToInt()
        fetchAyah(id)
    }

    override fun setAyahEdition(edition: String) {
        val currentAyahId = prefs.getInt(SharedPrefsManager.Key.CURRENT_AYAH_ID_INT)
        if (currentAyahId != 0) fetchAyah(currentAyahId)
        prefs.put(SharedPrefsManager.Key.EDITION_KEY, edition)
    }

}