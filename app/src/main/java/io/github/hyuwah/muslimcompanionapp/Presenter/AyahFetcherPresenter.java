package io.github.hyuwah.muslimcompanionapp.Presenter;

import io.github.hyuwah.muslimcompanionapp.Contract.AyahFetcherContract;
import io.github.hyuwah.muslimcompanionapp.Model.Entity.Ayah;
import io.github.hyuwah.muslimcompanionapp.Model.Network.ServiceGenerator;
import io.github.hyuwah.muslimcompanionapp.Model.Network.AlquranCloudService;
import io.github.hyuwah.muslimcompanionapp.Model.SharedPrefsManager;
import io.github.hyuwah.muslimcompanionapp.Model.SharedPrefsManager.Key;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AyahFetcherPresenter implements AyahFetcherContract.Presenter {

  private AyahFetcherContract.View view;

  public AyahFetcherPresenter(AyahFetcherContract.View mainView) {
    this.view = mainView;
  }

  @Override
  public void fetchRandomAyah() {
    int id = (int) Math.floor(Math.random() * ((6236 - 1 + 1) + 1)); // Generate id from 1 to 6236
    fetchAyah(id);
  }

  @Override
  public void fetchAyah(int id) {
    AlquranCloudService service = ServiceGenerator.createService(AlquranCloudService.class);

    // Check edition from prefs
    String edition = SharedPrefsManager.getInstance().getString(Key.EDITION_KEY, "en.asad");
    Call call = service.getAyahByEdition(id,edition);

    view.showLoading(); // Show loading

    call.enqueue(new Callback<Ayah>() {
      @Override
      public void onResponse(Call<Ayah> call, Response<Ayah> response) {
        view.fetchSuccess();
        if (response.body() != null) {
          view.showResult(response.body().getData());

          // save current ayah id to prefs
          SharedPrefsManager.getInstance().put(Key.CURRENT_AYAH_ID_INT, response.body().getData().getNumber());
        } else {
          view.fetchFailed();
        }
      }

      @Override
      public void onFailure(Call<Ayah> call, Throwable t) {
        view.fetchFailed();
      }
    });
  }

  @Override
  public void setAyahEdition(String edition) {
    SharedPrefsManager.getInstance().put(Key.EDITION_KEY, edition);
  }

  public void detachView() {
    view = null;
  }
}
