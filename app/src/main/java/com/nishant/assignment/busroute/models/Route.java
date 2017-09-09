package com.nishant.assignment.busroute.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.nishant.assignment.busroute.helper.RouteManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nishant on 05-09-2017.
 */

public class Route {

    private String routeId;
    private String routeName;
    private List<String> stopsInRoute;
    private String routeDescription;
    private boolean isRouteAccessible;
    private String imageURL;

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    private Bitmap imageBitmap;

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public List<String> getStopsInRoute() {
        return stopsInRoute;
    }

    public void setStopsInRoute(List<String> stopsInRoute) {
        this.stopsInRoute = stopsInRoute;
    }

    public String getRouteDescription() {
        return routeDescription;
    }

    public void setRouteDescription(String routeDescription) {
        this.routeDescription = routeDescription;
    }

    public boolean isRouteAccessible() {
        return isRouteAccessible;
    }

    public void setRouteAccessible(boolean routeAccessible) {
        isRouteAccessible = routeAccessible;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }


    public static void parseJSON(JSONObject routesJson) throws JSONException {
        JSONArray jRoutes = new JSONArray();
        RouteManager sharedManager = RouteManager.getRouteManager();
         ArrayList<String> stops;
        Route route;
        if (routesJson != null) {
            jRoutes = routesJson.getJSONArray(sharedManager.TAG_ROUTES);
            sharedManager.createNewRouteList();
        }

        for (int j = 0; j < jRoutes.length(); j++) {
            route = new Route();
            JSONObject routeObject = jRoutes.getJSONObject(j);

            String routeName = routeObject.getString(sharedManager.TAG_ROUTE_NAME);
            route.setRouteName(routeName);

            String routeID = routeObject.getString(sharedManager.TAG_ROUTE_ID);
            route.setRouteId(routeID);

            JSONArray stopsArray = routeObject.getJSONArray(sharedManager.TAG_STOPS);
            if (stopsArray != null) {
                stops = new ArrayList();
                for (int i = 0; i < stopsArray.length(); i++) {
                    JSONObject stopObject = stopsArray.getJSONObject(i);
                    stops.add(stopObject.getString(sharedManager.TAG_ROUTE_NAME));
                }
                route.setStopsInRoute(stops);
            }
            String routeDescription = routeObject.getString(sharedManager.TAG_DESCIRIPTION);
            route.setRouteDescription(routeDescription);

            String routeAccessible = routeObject.getString(sharedManager.TAG_ACCESSIBLE);
            route.setRouteAccessible(Boolean.valueOf(routeAccessible));

            String routeImageURL = routeObject.getString(sharedManager.TAG_IMAGE);
            route.setImageURL(routeImageURL);
            route.setImageBitmap(getBitmapfromUrl(routeImageURL));

            sharedManager.addRoute(route);
        }
    }

    public static Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }
    }
}
