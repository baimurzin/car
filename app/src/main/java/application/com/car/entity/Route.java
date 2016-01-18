package application.com.car.entity;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

/**
 * Created by Zahit Talipov on 17.01.2016.
 */
public class Route {
    private static LatLng startPoint;
    private static LatLng finishPoint;
    private static LatLngBounds latLngBounds;

    public static LatLng getStartPoint() {
        return startPoint;
    }

    public static void setStartPoint(LatLng startPoint) {
        Route.startPoint = startPoint;
    }

    public static LatLng getFinishPoint() {
        return finishPoint;
    }

    public static void setFinishPoint(LatLng finishPoint) {
        Route.finishPoint = finishPoint;
        LatLng northeast;
        LatLng southwest;
        if (startPoint.latitude < finishPoint.latitude) {
            if (startPoint.longitude > finishPoint.longitude) {
                southwest = new LatLng(startPoint.latitude, finishPoint.longitude);    //northwest -к северо-востоку,southwest-к юго-западу
                northeast = new LatLng(finishPoint.latitude, startPoint.longitude);
            } else {
                southwest = new LatLng(startPoint.latitude, startPoint.longitude);
                northeast = new LatLng(finishPoint.latitude, finishPoint.longitude);
            }
        } else {
            if (startPoint.longitude > finishPoint.longitude) {
                southwest = new LatLng(finishPoint.latitude, finishPoint.longitude);
                northeast = new LatLng(startPoint.latitude, startPoint.longitude);
            } else {
                southwest = new LatLng(finishPoint.latitude, startPoint.longitude);
                northeast = new LatLng(startPoint.latitude, finishPoint.longitude);
            }
        }
        latLngBounds = new LatLngBounds(southwest, northeast);
    }

    public static LatLngBounds getLatLngBounds() {
        return latLngBounds;
    }

    public static void setLatLngBounds(LatLngBounds latLngBounds) {
        Route.latLngBounds = latLngBounds;
    }

    public static String getStartPointToString() {
        return startPoint.latitude + "," + startPoint.longitude;
    }

    public static String getFinishPointToString() {
        return finishPoint.latitude + "," + finishPoint.longitude;
    }
}
