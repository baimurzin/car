package application.com.car.entity;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by Zahit Talipov on 20.01.2016.
 */
public class ItemRoute {
    static String[] months = {"Января", "Февраля", "Марта", "Апреля", "Мая", "Июня", "Июля", "Августа", "Сентября", "Октября", "Ноября", "Декабря"};
    private List<LatLng> routeList;
    private String addressStart;
    private String addressEnd;
    private int minute;
    private int hour;
    private int month;
    private int dayOfMonth;
    private String numberAuthor="1234567890";

    public ItemRoute(List<LatLng> routeList, String addressStart, String addressEnd, int minute, int hour, int month, int dayOfMonth) {
        this.routeList = routeList;
        this.addressStart = addressStart;
        this.addressEnd = addressEnd;
        this.minute = minute;
        this.hour = hour;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
    }

    public String getTime() {
        if (minute < 10)
            return hour + ":" + "0" + minute;
        else
            return hour + ":" + minute;
    }

    public String getDate() {
        return dayOfMonth + " " + months[month];
    }

    public List<LatLng> getRouteList() {
        return routeList;
    }

    public void setRouteList(List<LatLng> routeList) {
        this.routeList = routeList;
    }

    public String getAddressStart() {
        return addressStart;
    }

    public void setAddressStart(String addressStart) {
        this.addressStart = addressStart;
    }

    public String getAddressEnd() {
        return addressEnd;
    }

    public void setAddressEnd(String addressEnd) {
        this.addressEnd = addressEnd;
    }

    public String getNumberAuthor() {
        return numberAuthor;
    }

    public void setNumberAuthor(String numberAuthor) {
        this.numberAuthor = numberAuthor;
    }
}
