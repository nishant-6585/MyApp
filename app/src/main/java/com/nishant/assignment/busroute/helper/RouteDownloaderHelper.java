package com.nishant.assignment.busroute.helper;

import com.nishant.assignment.busroute.models.Route;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by nisha on 06-09-2017.
 */

public class RouteDownloaderHelper implements DownloaderHelper {

    @Override
    public void parse(JSONObject jsonObject) throws JSONException, IOException {
        Route.parseJSON(jsonObject);
    }
}
