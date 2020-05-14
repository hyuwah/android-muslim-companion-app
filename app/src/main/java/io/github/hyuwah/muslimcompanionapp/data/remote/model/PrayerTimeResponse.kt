package io.github.hyuwah.muslimcompanionapp.data.remote.model


import com.google.gson.annotations.SerializedName

data class PrayerTimeResponse(
        @SerializedName("code")
        val code: Int = 0,
        @SerializedName("data")
        val data: Data = Data(),
        @SerializedName("status")
        val status: String = ""
) {
    data class Data(
            @SerializedName("date")
            val date: Date = Date(),
            @SerializedName("timings")
            val timings: Timings = Timings()
    ) {
        data class Date(
                @SerializedName("readable")
                val readable: String = "",
                @SerializedName("timestamp")
                val timestamp: String = ""
        )

        data class Timings(
                @SerializedName("Asr")
                val asr: String = "",
                @SerializedName("Dhuhr")
                val dhuhr: String = "",
                @SerializedName("Fajr")
                val fajr: String = "",
                @SerializedName("Imsak")
                val imsak: String = "",
                @SerializedName("Isha")
                val isha: String = "",
                @SerializedName("Maghrib")
                val maghrib: String = "",
                @SerializedName("Midnight")
                val midnight: String = "",
                @SerializedName("Sunrise")
                val sunrise: String = "",
                @SerializedName("Sunset")
                val sunset: String = ""
        )
    }
}