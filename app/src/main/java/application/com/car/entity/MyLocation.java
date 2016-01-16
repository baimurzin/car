package application.com.car.entity;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Zahit Talipov on 14.01.2016.
 */
public class MyLocation {
    public static double latitude = 0;
    public static double longitude = 0;
    static SharedPreferences preferences;

    public static void save(Context context) {
        preferences = context.getSharedPreferences("car", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("lat", String.valueOf(latitude));
        editor.putString("lon", String.valueOf(longitude));
        editor.commit();
    }
    public static void getLastSaved(Context context){
        preferences = context.getSharedPreferences("car", Context.MODE_PRIVATE);
        latitude=Double.parseDouble(preferences.getString("lat", "0"));
        longitude=Double.parseDouble(preferences.getString("lon","0"));
    }
}
