package io.github.hyuwah.muslimcompanionapp.presentation.ayahfetcher

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import io.github.hyuwah.muslimcompanionapp.R
import io.github.hyuwah.muslimcompanionapp.data.SharedPrefsManager
import io.github.hyuwah.muslimcompanionapp.data.remote.model.AyahResponse
import io.github.hyuwah.muslimcompanionapp.databinding.FragmentAyahFetcherBinding
import io.github.hyuwah.muslimcompanionapp.presentation.base.viewBinding

class AyahFetcherFragment : Fragment(R.layout.fragment_ayah_fetcher), AyahFetcherContract.View {

    private val binding by viewBinding(FragmentAyahFetcherBinding::bind)

    private var presenter = AyahFetcherPresenter(this)

    override fun onViewCreated(fview: View, savedInstanceState: Bundle?) {
        super.onViewCreated(fview, savedInstanceState)

        requireActivity().title = "Ayat Fetcher"

        SharedPrefsManager.getInstance(requireContext())

        setHasOptionsMenu(true)

        if (SharedPrefsManager.getInstance().getInt(SharedPrefsManager.Key.CURRENT_AYAH_ID_INT, 0) != 0) {
            presenter.fetchAyah(SharedPrefsManager.getInstance().getInt(SharedPrefsManager.Key.CURRENT_AYAH_ID_INT))
        } else {
            presenter.fetchRandomAyah()
        }

        binding.btnFetchRandom.setOnClickListener { view: View? ->
            showLoading()
            presenter.fetchRandomAyah()
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

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.ayah_fetcher, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val currentAyahId = SharedPrefsManager.getInstance().getInt(SharedPrefsManager.Key.CURRENT_AYAH_ID_INT, 0)
        return when (item.itemId) {
            R.id.action_ed_default -> {
                presenter.setAyahEdition("quran-simple")
                if (currentAyahId != 0) {
                    presenter.fetchAyah(currentAyahId)
                }
                true
            }
            R.id.action_ed_en_transliteration -> {
                presenter.setAyahEdition("en.transliteration")
                if (currentAyahId != 0) {
                    presenter.fetchAyah(currentAyahId)
                }
                true
            }
            R.id.action_ed_id_indonesian -> {
                presenter.setAyahEdition("id.indonesian")
                if (currentAyahId != 0) {
                    presenter.fetchAyah(currentAyahId)
                }
                true
            }
            R.id.action_ed_id_muntakhab -> {
                presenter.setAyahEdition("id.muntakhab")
                if (currentAyahId != 0) {
                    presenter.fetchAyah(currentAyahId)
                }
                true
            }
            R.id.action_ed_en_ahmedali -> {
                presenter.setAyahEdition("en.ahmedali")
                if (currentAyahId != 0) {
                    presenter.fetchAyah(currentAyahId)
                }
                true
            }
            R.id.action_ed_en_asad -> {
                presenter.setAyahEdition("en.asad")
                if (currentAyahId != 0) {
                    presenter.fetchAyah(currentAyahId)
                }
                true
            }
            R.id.action_ed_en_hilali -> {
                presenter.setAyahEdition("en.hilali")
                if (currentAyahId != 0) {
                    presenter.fetchAyah(currentAyahId)
                }
                true
            }
            R.id.action_ed_en_pickthall -> {
                presenter.setAyahEdition("en.pickthall")
                if (currentAyahId != 0) {
                    presenter.fetchAyah(currentAyahId)
                }
                true
            }
            R.id.action_ed_en_sahih -> {
                presenter.setAyahEdition("en.sahih")
                if (currentAyahId != 0) {
                    presenter.fetchAyah(currentAyahId)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun fetchFailed() { //    Toast.makeText(mContext, "Network error", Toast.LENGTH_SHORT).show();
        Snackbar.make(requireView(), "Network error", Snackbar.LENGTH_SHORT).show()
        binding.tvAyahText.text = "Please check your connection & try again..."
    }

    override fun showLoading() {
        binding.tvAyahText.text = "Loading..."
    }

    override fun showResult(ayah: AyahResponse.Data) {
        binding.tvAyahText.text = ayah.text
        binding.tvSurahName.text = ayah.surah.englishName
        binding.tvSurahNameTranslation.text = ayah.surah.englishNameTranslation
        binding.tvAyahNumber.text = "[" + ayah.surah.number + "] : " + ayah.numberInSurah + " of " + ayah
                .surah.numberOfAyahs
        binding.tvEdition.text = ayah.edition.englishName
    }

    override fun toggleShareButton(toggle: Boolean) {
        binding.btnShare.isEnabled = toggle
    }

    override fun toggleFavButton(toggle: Boolean) {
        binding.btnFavorite.isEnabled = toggle
    }
}