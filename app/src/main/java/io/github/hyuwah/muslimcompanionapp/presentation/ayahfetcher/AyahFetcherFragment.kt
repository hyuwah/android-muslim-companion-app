package io.github.hyuwah.muslimcompanionapp.presentation.ayahfetcher

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.google.android.material.snackbar.Snackbar
import io.github.hyuwah.muslimcompanionapp.R
import io.github.hyuwah.muslimcompanionapp.data.SharedPrefsManager
import io.github.hyuwah.muslimcompanionapp.data.remote.model.AyahResponse
import io.github.hyuwah.muslimcompanionapp.databinding.FragmentAyahFetcherBinding
import io.github.hyuwah.muslimcompanionapp.presentation.base.viewBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class AyahFetcherFragment : Fragment(R.layout.fragment_ayah_fetcher) {

    private val binding by viewBinding(FragmentAyahFetcherBinding::bind)
    private val viewModel by viewModel<AyahFetcherViewModel>()
    private val prefs by inject<SharedPrefsManager>()

    override fun onViewCreated(fview: View, savedInstanceState: Bundle?) {
        super.onViewCreated(fview, savedInstanceState)
        requireActivity().title = "Ayat Fetcher"
        setHasOptionsMenu(true)

        initObserver()

        viewModel.fetchAyah(prefs.getInt(SharedPrefsManager.Key.CURRENT_AYAH_ID_INT))

        binding.btnFetchRandom.setOnClickListener {
            showLoading()
            viewModel.fetchRandomAyah()
        }
        binding.btnFavorite.setOnClickListener { view: View? ->
            if (binding.btnFavorite.text.toString() == "+ Favorite") {
                binding.btnFavorite.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
                binding.btnFavorite.text = "- Favorite"
                Snackbar.make(requireView(), "Favorited", Snackbar.LENGTH_SHORT).show()
            } else {
                binding.btnFavorite.setTextColor(ContextCompat.getColor(requireContext(), R.color.textSecondary))
                binding.btnFavorite.text = "+ Favorite"
                Snackbar.make(requireView(), "Unfavorited", Snackbar.LENGTH_SHORT).show()
            }
        }
        binding.btnShare.setOnClickListener { view: View? ->
            var message = binding.tvAyahText.text.toString() + "\n"
            message += "-- " + binding.tvSurahName.text.toString() + " (" + binding.tvSurahNameTranslation.text
                    .toString() + ") " + binding.tvAyahNumber.text.toString()
            message += "\n\nConvey, even if it is one verse\nShared via Muslim Companion Apps"
            val sendIntent = Intent(Intent.ACTION_SEND)
            sendIntent.putExtra(Intent.EXTRA_TEXT, message)
            sendIntent.type = "text/plain"
            val chooser = Intent.createChooser(sendIntent, "Convey, even if it is one verse")
            if (sendIntent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(chooser)
            }
        }
    }

    private fun initObserver() {
        viewModel.uiState.observe(viewLifecycleOwner, {
            when (it) {
                AyahFetcher.UiState.Loading -> showLoading()
                is AyahFetcher.UiState.Success -> {
                    showResult(it.ayah.data)
                }
                is AyahFetcher.UiState.Failed -> {
                    fetchFailed()
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.ayah_fetcher, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_ed_default -> {
                viewModel.setAyahEdition("quran-simple")
                true
            }
            R.id.action_ed_en_transliteration -> {
                viewModel.setAyahEdition("en.transliteration")
                true
            }
            R.id.action_ed_id_indonesian -> {
                viewModel.setAyahEdition("id.indonesian")
                true
            }
            R.id.action_ed_id_muntakhab -> {
                viewModel.setAyahEdition("id.muntakhab")
                true
            }
            R.id.action_ed_en_ahmedali -> {
                viewModel.setAyahEdition("en.ahmedali")
                true
            }
            R.id.action_ed_en_asad -> {
                viewModel.setAyahEdition("en.asad")
                true
            }
            R.id.action_ed_en_hilali -> {
                viewModel.setAyahEdition("en.hilali")
                true
            }
            R.id.action_ed_en_pickthall -> {
                viewModel.setAyahEdition("en.pickthall")
                true
            }
            R.id.action_ed_en_sahih -> {
                viewModel.setAyahEdition("en.sahih")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun fetchFailed() {
        Snackbar.make(requireView(), "Network error", Snackbar.LENGTH_SHORT).show()
        binding.tvAyahText.text = "Please check your connection & try again..."
    }

    private fun showLoading() {
        binding.tvAyahText.text = "Loading..."
    }

    private fun showResult(ayah: AyahResponse.Data) {
        binding.tvAyahText.text = ayah.text
        binding.tvSurahName.text = ayah.surah.englishName
        binding.tvSurahNameTranslation.text = ayah.surah.englishNameTranslation
        binding.tvAyahNumber.text = "[" + ayah.surah.number + "] : " + ayah.numberInSurah + " of " + ayah
                .surah.numberOfAyahs
        binding.tvEdition.text = ayah.edition.englishName
    }

    fun toggleShareButton(toggle: Boolean) {
        binding.btnShare.isEnabled = toggle
    }

    fun toggleFavButton(toggle: Boolean) {
        binding.btnFavorite.isEnabled = toggle
    }
}