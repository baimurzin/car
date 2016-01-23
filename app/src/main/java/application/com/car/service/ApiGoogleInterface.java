package application.com.car.service;

import application.com.car.entity.RouteResponse;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Zahit Talipov on 18.01.2016.
 */
public interface ApiGoogleInterface {
    @GET("maps/api/directions/json")
    public Call<RouteResponse> getRoute(@Query(value = "origin", encoded = false) String origin,
                                        @Query(value = "destination", encoded = false) String destination,
                                        @Query("sensor") boolean sensor,
                                        @Query("api") String api,
                                        @Query("language") String language);
}
