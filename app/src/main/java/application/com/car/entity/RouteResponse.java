package application.com.car.entity;

import java.util.List;

/**
 * Created by Zahit Talipov on 18.01.2016.
 */
public class RouteResponse {

    public List<Route> routes;

    public String getPoints() {
        return this.routes.get(0).overview_polyline.points;
    }

    class Route {
        OverviewPolyline overview_polyline;
    }

    class OverviewPolyline {
        String points;
    }
}
