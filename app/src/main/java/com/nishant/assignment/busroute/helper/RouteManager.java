package com.nishant.assignment.busroute.helper;

import com.nishant.assignment.busroute.models.Route;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by nishant on 06-09-2017.
 */

public class RouteManager {

    public static final String TAG_ROUTES = "routes";
    public static final String TAG_ROUTE_ID = "id";
    public static final String TAG_ROUTE_NAME = "name";
    public static final String TAG_STOPS = "stops";
    public static final String TAG_STOP_NAME = "name";
    public static final String TAG_DESCIRIPTION = "description";
    public static final String TAG_ACCESSIBLE = "accessible";
    public static final String TAG_IMAGE = "image";


    private static RouteManager routeManager = null;      // Singleton instance.
    private static ArrayList<Route> routes = null;

    public static RouteManager getRouteManager() {
        if (routeManager == null) {
            routeManager = new RouteManager();
        }
        return routeManager;
    }

    /*
    addRoute will add a Route to our ArrayList of Routes, unless we're supposed to hide it.
     */
    public void addRoute(Route route) {
            routes.add(route);
    }

    public ArrayList<Route> getAllRoutes() {
        return routes;
    }


    public Route getRouteByID(String id) {
        if (routes != null) {
            for (Route route : routes) {
                if (route.getRouteId().equals(id)) {
                    return route;
                }
            }
        }
        return null;
    }

    public Route getRouteByIndex(int idx) {
        if (routes != null && idx <= routes.size() - 1) {
            return routes.get(idx);
        }
        return null;
    }

    public void createNewRouteList() {
        routes = new ArrayList<>();
    }
}
