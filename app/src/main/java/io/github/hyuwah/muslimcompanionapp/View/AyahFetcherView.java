package io.github.hyuwah.muslimcompanionapp.View;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.hyuwah.muslimcompanionapp.Contract.AyahFetcherContract;
import io.github.hyuwah.muslimcompanionapp.Model.Entity.Ayah.Data;
import io.github.hyuwah.muslimcompanionapp.Model.SharedPrefsManager;
import io.github.hyuwah.muslimcompanionapp.Model.SharedPrefsManager.Key;
import io.github.hyuwah.muslimcompanionapp.Presenter.AyahFetcherPresenter;
import io.github.hyuwah.muslimcompanionapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AyahFetcherView extends Fragment implements AyahFetcherContract.View {

  @BindView(R.id.tv_ayah_text)
  TextView tvAyahText;
  @BindView(R.id.tv_surah_name)
  TextView tvSurahName;
  @BindView(R.id.tv_surah_name_translation)
  TextView tvSurahNameTranslation;
  @BindView(R.id.tv_ayah_number)
  TextView tvAyahNumber;
  @BindView(R.id.tv_edition)
  TextView tvEdition;
  @BindView(R.id.btn_fetch_random)
  Button btnFetchRandom;
  @BindView(R.id.btn_favorite)
  Button btnFavorite;
  @BindView(R.id.btn_share)
  Button btnShare;

  Unbinder unbinder;

  private Context mContext;
  private AyahFetcherContract.Presenter presenter;

  public AyahFetcherView() {
    // Required empty public constructor
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    mContext = context;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    getActivity().setTitle("Ayat Fetcher");

    SharedPrefsManager.getInstance(getActivity().getApplicationContext());

    // Create presenter
    presenter = new AyahFetcherPresenter(this);

    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.view_ayah_fetcher, container, false);

    unbinder = ButterKnife.bind(this, view);

    setHasOptionsMenu(true);

    return view;
  }

  @Override
  public void onViewCreated(@NonNull View fview, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(fview, savedInstanceState);

    // Fetch random if no previous fetched ayah
    if(SharedPrefsManager.getInstance().getInt(Key.CURRENT_AYAH_ID_INT,0)!=0){
      presenter.fetchAyah(SharedPrefsManager.getInstance().getInt(Key.CURRENT_AYAH_ID_INT));
    }else {
      presenter.fetchRandomAyah();
    }

    // Button OnClick Listener
    btnFetchRandom.setOnClickListener(view -> {
      showLoading();
      presenter.fetchRandomAyah();
    });

    btnFavorite.setOnClickListener(view->{

      if (btnFavorite.getText().toString().equals("+ Favorite")) {
        btnFavorite.setTextColor(getActivity().getColor(R.color.colorPrimary));
        btnFavorite.setText("- Favorite");
        Snackbar.make(getView(), "Favorited", Snackbar.LENGTH_SHORT).show();
      } else {
        btnFavorite.setTextColor(getActivity().getColor(R.color.textSecondary));
        btnFavorite.setText("+ Favorite");
        Snackbar.make(getView(), "Unfavorited", Snackbar.LENGTH_SHORT).show();
      }

    });

    btnShare.setOnClickListener(view->{
      String message = tvAyahText.getText().toString() + "\n";
      message += "-- " + tvSurahName.getText().toString() + " (" + tvSurahNameTranslation.getText().toString() + ") " + tvAyahNumber.getText().toString();
      message += "\n\nConvey, even if it is one verse\nShared via Muslim Companion Apps";

      Intent sendIntent = new Intent(Intent.ACTION_SEND);
      sendIntent.putExtra(Intent.EXTRA_TEXT, message);
      sendIntent.setType("text/plain");
      Intent chooser = Intent.createChooser(sendIntent, "Convey, even if it is one verse");

      if(sendIntent.resolveActivity(getActivity().getPackageManager())!=null){
        startActivity(chooser);
      }
    });

  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.ayah_fetcher,menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int currentAyahId = SharedPrefsManager.getInstance().getInt(Key.CURRENT_AYAH_ID_INT,0);

    switch (item.getItemId()){
      case R.id.action_ed_default:

        presenter.setAyahEdition("quran-simple");

        if(currentAyahId!=0){
          presenter.fetchAyah(currentAyahId);
        }

        return true;
      case R.id.action_ed_en_transliteration:

        presenter.setAyahEdition("en.transliteration");

        if(currentAyahId!=0){
          presenter.fetchAyah(currentAyahId);
        }

        return true;
      case R.id.action_ed_id_indonesian:

        presenter.setAyahEdition("id.indonesian");

        if(currentAyahId!=0) {
          presenter.fetchAyah(currentAyahId);
        }

        return true;
      case R.id.action_ed_id_muntakhab:

        presenter.setAyahEdition("id.muntakhab");

        if(currentAyahId!=0) {
          presenter.fetchAyah(currentAyahId);
        }

        return true;
      case R.id.action_ed_en_ahmedali:

        presenter.setAyahEdition("en.ahmedali");

        if(currentAyahId!=0) {
          presenter.fetchAyah(currentAyahId);
        }

        return true;
      case R.id.action_ed_en_asad:

        presenter.setAyahEdition("en.asad");

        if(currentAyahId!=0) {
          presenter.fetchAyah(currentAyahId);
        }

        return true;
      case R.id.action_ed_en_hilali:

        presenter.setAyahEdition("en.hilali");

        if(currentAyahId!=0) {
          presenter.fetchAyah(currentAyahId);
        }

        return true;
      case R.id.action_ed_en_pickthall:

        presenter.setAyahEdition("en.pickthall");

        if(currentAyahId!=0) {
          presenter.fetchAyah(currentAyahId);
        }

        return true;
      case R.id.action_ed_en_sahih:

        presenter.setAyahEdition("en.sahih");

        if(currentAyahId!=0) {
          presenter.fetchAyah(currentAyahId);
        }

        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override
  public void fetchSuccess() {

  }

  @Override
  public void fetchFailed() {
//    Toast.makeText(mContext, "Network error", Toast.LENGTH_SHORT).show();
    Snackbar.make(getView(), "Network error", Snackbar.LENGTH_SHORT).show();
    tvAyahText.setText("Please check your connection & try again...");
  }

  @Override
  public void showLoading() {
    tvAyahText.setText("Loading...");
  }

  @Override
  public void showResult(Data ayah) {
    tvAyahText.setText(ayah.getText());
    tvSurahName.setText(ayah.getSurah().getEnglishName());
    tvSurahNameTranslation.setText(ayah.getSurah().getEnglishNameTranslation());
    tvAyahNumber.setText("["+ayah.getSurah().getNumber()+"] : "+ayah.getNumberInSurah()+" of "+ayah.getSurah().getNumberOfAyahs());
    tvEdition.setText(ayah.getEdition().getEnglishName());
  }

  @Override
  public void toggleShareButton(boolean toggle) {
    btnShare.setEnabled(toggle);
  }

  @Override
  public void toggleFavButton(boolean toggle) {
    btnFavorite.setEnabled(toggle);
  }
}
