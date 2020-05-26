package io.github.hyuwah.muslimcompanionapp.presentation.prayertimes

import androidx.annotation.DrawableRes

data class PrayerTimeModel(
        @DrawableRes val iconRes: Int = -1,
        val title: String,
        val time: String
)