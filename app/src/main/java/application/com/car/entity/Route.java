package application.com.car.entity;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by Zahit Talipov on 17.01.2016.
 */
public class Route {
    public static boolean isExistStartPoint = false;
    private static boolean isSelectedTime = false;
    private static boolean isSelectedDate = false;
    private static boolean isSelectedRoute = false;
    private static LatLng startPoint;
    private static List<LatLng> route;
    private static String startAddress;
    private static String endAddress;
    private static int minute;
    private static int hour;
    private static int month;
    private static LatLng finishPoint;
    private static int dayOfMonth;

    public static int getDayOfMonth() {
        return dayOfMonth;
    }

    public static void setDate(int dayOfMonth, int month) {
        Route.dayOfMonth = dayOfMonth;
        Route.month = month;
        isSelectedDate = true;
    }

    public static int getMonth() {
        return month;
    }

    public static int getHour() {
        return hour;
    }

    public static void setTime(int hour, int minute) {
        Route.hour = hour;
        Route.minute = minute;
        isSelectedTime = true;
    }

    public static int getMinute() {
        return minute;
    }


    public static LatLng getStartPoint() {
        return startPoint;
    }

    public static void setStartPoint(LatLng startPoint) {
        Route.startPoint = startPoint;
    }


    public static String getStartPointToString() {
        return startPoint.latitude + "," + startPoint.longitude;
    }




    public static LatLng getFinishPoint() {
        return finishPoint;
    }

    public static void setFinishPoint(LatLng finishPoint) {
        Route.finishPoint = finishPoint;
    }

    public static String getFinishPointToString() {
        return finishPoint.latitude + "," + finishPoint.longitude;
    }

    public static void clean() {
        isSelectedRoute = isSelectedDate = isSelectedTime = false;
    }

    public static void isReadyForCreate() throws Exception {
        if (!isSelectedRoute)
            throw new Exception("Выберите маршрут поездки");
        if (!isSelectedDate || !isSelectedTime)
            throw new Exception("Выберите дату и время поездки");
    }

    public static String getStartAddress() {
        return startAddress;
    }

    public static void setStartAddress(String startAddress) {
        Route.startAddress = startAddress;
    }

    public static String getEndAddress() {
        return endAddress;
    }

    public static void setEndAddress(String endAddress) {
        Route.endAddress = endAddress;
    }


    public static String string() {
        return String.format("от: %s \n до: %s",startAddress,endAddress);
    }

    public static List<LatLng> getRoute() {
        return route;
    }

    public static void setRoute(List<LatLng> route) {
        Route.route = route;
        isSelectedRoute = true;
    }
}
