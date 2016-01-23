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

    public String getStartAddress() {
        return this.routes.get(0).legs.get(0).start_address;
    }

    public String getEndAddress() {
        return this.routes.get(0).legs.get(0).end_address;
    }

    class Route {
        OverviewPolyline overview_polyline;
        List<Legs> legs;
    }

    class OverviewPolyline {
        String points;
    }

    class Legs {
        String end_address;
        String start_address;
    }
}
