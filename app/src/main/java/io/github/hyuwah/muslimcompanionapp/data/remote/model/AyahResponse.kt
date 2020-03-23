package io.github.hyuwah.muslimcompanionapp.data.remote.model


import com.google.gson.annotations.SerializedName

data class AyahResponse(
        @SerializedName("code")
        val code: Int = 0,
        @SerializedName("data")
        val `data`: Data = Data(),
        @SerializedName("status")
        val status: String = ""
) {
    data class Data(
            @SerializedName("edition")
            val edition: Edition = Edition(),
            @SerializedName("hizbQuarter")
            val hizbQuarter: Int = 0,
            @SerializedName("juz")
            val juz: Int = 0,
            @SerializedName("manzil")
            val manzil: Int = 0,
            @SerializedName("number")
            val number: Int = 0,
            @SerializedName("numberInSurah")
            val numberInSurah: Int = 0,
            @SerializedName("page")
            val page: Int = 0,
            @SerializedName("ruku")
            val ruku: Int = 0,
            @SerializedName("sajda")
            val sajda: Boolean = false,
            @SerializedName("surah")
            val surah: Surah = Surah(),
            @SerializedName("text")
            val text: String = ""
    ) {
        data class Edition(
                @SerializedName("direction")
                val direction: String = "",
                @SerializedName("englishName")
                val englishName: String = "",
                @SerializedName("format")
                val format: String = "",
                @SerializedName("identifier")
                val identifier: String = "",
                @SerializedName("language")
                val language: String = "",
                @SerializedName("name")
                val name: String = "",
                @SerializedName("type")
                val type: String = ""
        )

        data class Surah(
                @SerializedName("englishName")
                val englishName: String = "",
                @SerializedName("englishNameTranslation")
                val englishNameTranslation: String = "",
                @SerializedName("name")
                val name: String = "",
                @SerializedName("number")
                val number: Int = 0,
                @SerializedName("numberOfAyahs")
                val numberOfAyahs: Int = 0,
                @SerializedName("revelationType")
                val revelationType: String = ""
        )
    }
}