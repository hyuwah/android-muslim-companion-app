package io.github.hyuwah.muslimcompanionapp.Contract;

import io.github.hyuwah.muslimcompanionapp.Model.Entity.Ayah;

public interface AyahFetcherContract {


  interface View {

    void fetchSuccess();

    void fetchFailed();

    void showLoading();

    void showResult(Ayah.Data ayah);

    void toggleShareButton(boolean toggle);

    void toggleFavButton(boolean toggle);

  }


  interface Presenter {

    void fetchRandomAyah();

    void fetchAyah(int id);

    void setAyahEdition(String edition);

  }



}
