package application.com.car.service;


import com.squareup.okhttp.OkHttpClient;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Zahit Talipov on 18.01.2016.
 */
public class ApiClientGoogleFactory {
    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static String URL = "https://maps.googleapis.com/";

    public static ApiGoogleInterface getApiGoogle() {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create()).build().create(ApiGoogleInterface.class);
    }
}
