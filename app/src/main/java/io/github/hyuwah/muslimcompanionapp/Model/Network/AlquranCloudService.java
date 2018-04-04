package io.github.hyuwah.muslimcompanionapp.Model.Network;

import io.github.hyuwah.muslimcompanionapp.Model.Entity.Ayah;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AlquranCloudService {

  @GET("/ayah/{id}")
  Call<Ayah> getAyah(
      @Path("id") int id
  );

  @GET("/ayah/{id}/{edition}")
  Call<Ayah> getAyahByEdition(
      @Path("id") int id,
      @Path("edition") String edition
  );

}
