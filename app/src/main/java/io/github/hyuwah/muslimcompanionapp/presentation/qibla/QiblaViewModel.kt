package io.github.hyuwah.muslimcompanionapp.presentation.qibla

import io.github.hyuwah.muslimcompanionapp.presentation.base.BaseViewModel
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

class QiblaViewModel : BaseViewModel() {

    fun getDirection(degree: Float): String {
        return when (degree.roundToInt()) {
            in 22..75 -> "NE"
            in 76..112 -> "E"
            in 113..157 -> "SE"
            in 158..202 -> "S"
            in 203..247 -> "SW"
            in 248..292 -> "W"
            in 293..337 -> "NW"
            else -> "N"
        }
    }

    fun getMeccaDegree(): Float {
        var meccaLat = 21.4225
        var meccaLong = 39.8262
        var userLat = -6.2088
        var userLong = 106.8456
        return bearing(userLat, userLong, meccaLat, meccaLong).toFloat()
    }

    fun bearing(startLat: Double, startLng: Double, endLat: Double, endLng: Double): Double {
        val latitude1 = Math.toRadians(startLat)
        val latitude2 = Math.toRadians(endLat)
        val longDiff = Math.toRadians(endLng - startLng)
        val y = sin(longDiff) * cos(latitude2)
        val x = cos(latitude1) * sin(latitude2) - sin(latitude1) * cos(latitude2) * cos(longDiff)
        return (Math.toDegrees(atan2(y, x)) + 360) % 360
    }

    private val ALPHA = 0.05f
    fun applyLowPassFilter(input: FloatArray, output: FloatArray?): FloatArray? {
        if (output == null) return input
        for (i in input.indices) {
            output[i] = output[i] + ALPHA * (input[i] - output[i])
        }
        return output
    }
}