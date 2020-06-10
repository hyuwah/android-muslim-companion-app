package io.github.hyuwah.muslimcompanionapp.presentation.prayertimes

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import io.github.hyuwah.muslimcompanionapp.R
import io.github.hyuwah.muslimcompanionapp.data.remote.model.PrayerTimeResponse
import io.github.hyuwah.muslimcompanionapp.databinding.FragmentPrayerTimesBinding
import io.github.hyuwah.muslimcompanionapp.presentation.base.UIState
import io.github.hyuwah.muslimcompanionapp.presentation.base.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

class PrayerTimesFragment : Fragment(R.layout.fragment_prayer_times) {

    private val binding by viewBinding(FragmentPrayerTimesBinding::bind)
    private val viewModel by viewModel<PrayerTimesViewModel>()
    private val adapter by lazy { PrayerTimesAdapter() }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup Adapter
        binding.rvPrayerTimes.adapter = adapter
        binding.tvDate.text = LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE, dd MMM yyyy"))

        viewModel.prayerTimes.state.observe(viewLifecycleOwner, {
            when (it) {
                UIState.Loading -> showLoading()
                is UIState.Success -> {
                    hideLoading()
                    setData(it.data.data.timings)
                }
                is UIState.Failure -> {
                    showToast(it.exception.message.orEmpty())
                }
            }
        })
        viewModel.prayerTimes.load()
    }

    private fun showLoading() {
        showToast("Loading")
    }

    private fun hideLoading() {

    }

    private fun setData(timings: PrayerTimeResponse.Data.Timings) {
        val prayerTimes = mutableListOf<PrayerTimeModel>()
        with(timings) {
            prayerTimes.add(PrayerTimeModel(R.drawable.ic_imsak, "Imsak", time = imsak))
            prayerTimes.add(PrayerTimeModel(R.drawable.ic_shubuh, "Subuh", fajr))
            prayerTimes.add(PrayerTimeModel(R.drawable.ic_sunrise, "Sunrise", time = sunrise))
            prayerTimes.add(PrayerTimeModel(R.drawable.ic_dhuhr, "Dhuhr", time = dhuhr))
            prayerTimes.add(PrayerTimeModel(R.drawable.ic_asr, "Asr", time = asr))
            prayerTimes.add(PrayerTimeModel(R.drawable.ic_sunset, "Maghrib", time = maghrib))
            prayerTimes.add(PrayerTimeModel(R.drawable.ic_sunset, "Sunset", time = sunset))
            prayerTimes.add(PrayerTimeModel(R.drawable.ic_isha, "Isya", time = isha))
        }
        adapter.setItem(prayerTimes)
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

}