package com.nishant.assignment.busroute.helper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by nishant on 06-09-2017.
 */

public interface DownloaderHelper {
    String ROUTES_URL = "http://www.mocky.io/v2/5808f00d10000005074c6340";

    void parse(JSONObject jsonObject) throws JSONException, IOException;
}
