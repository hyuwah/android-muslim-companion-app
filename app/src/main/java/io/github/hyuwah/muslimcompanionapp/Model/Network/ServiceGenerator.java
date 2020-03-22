package io.github.hyuwah.muslimcompanionapp.Model.Network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

  private static final String BASE_URL = "https://api.alquran.cloud/";

  private static Retrofit.Builder builder = new Builder().baseUrl(BASE_URL).addConverterFactory(
      GsonConverterFactory.create());

  private static Retrofit retrofit = builder.build();

  private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(Level.BODY);

  private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

  public static <S> S createService(Class<S> serviceClass) {
    if(!httpClient.interceptors().contains(logging)) {
      httpClient.addInterceptor(logging);
      builder.client(httpClient.build());
      retrofit = builder.build();
    }

    return retrofit.create(serviceClass);
  }

}
