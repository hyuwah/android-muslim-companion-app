package io.github.hyuwah.muslimcompanionapp.presentation.prayertimes

import androidx.lifecycle.viewModelScope
import io.github.hyuwah.muslimcompanionapp.data.remote.model.PrayerTimeResponse
import io.github.hyuwah.muslimcompanionapp.domain.AladhanRepository
import io.github.hyuwah.muslimcompanionapp.presentation.base.ActionStateLiveData
import io.github.hyuwah.muslimcompanionapp.presentation.base.BaseViewModel
import kotlinx.coroutines.Dispatchers

interface PrayerTimes {
    val prayerTimes: ActionStateLiveData<PrayerTimeResponse>
    fun loadPrayerTimes()
}

class PrayerTimesViewModel(
        private val repo: AladhanRepository
) : BaseViewModel(), PrayerTimes {

    override val prayerTimes = ActionStateLiveData(viewModelScope.coroutineContext + Dispatchers.IO) {
        repo.getTodayPrayerTime()
    }

    override fun loadPrayerTimes() {
        prayerTimes.load()
    }

}