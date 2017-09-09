package com.nishant.assignment.busroute.helper;

import android.content.Context;
import android.os.AsyncTask;

import com.nishant.assignment.busroute.RouteActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by nishant on 06-09-2017.
 */

public class Downloader extends AsyncTask<String, Void, Boolean> {

    private static Context mContext;
    private final DownloaderHelper helper;

    public Downloader(DownloaderHelper helper, Context context) {
        this.helper = helper;
        mContext = context;
    }

    public static Context getContext() {
        return mContext;
    }

    @Override
    public Boolean doInBackground(String... urls) {
        try {
            helper.parse(new JSONObject(downloadUrl(urls[0])));
            return true;
        } catch (IOException | JSONException e) {
            //Log.e("JSON", "DownloadURL IO error.");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (result) {
            RouteActivity.updateUI(mContext);
        }
    }

    // Given a URL, establishes an HttpUrlConnection and retrieves
    // the web page content as a InputStream, which it returns as
    // a string.
    private String downloadUrl(String myUrl) throws IOException {
        InputStream is = null;

        try {
            URL url = new URL(myUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the QUERY
            conn.connect();
            //int response = conn.getResponseCode();
            //Log.d("JSON", "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            return readIt(is);

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    // Reads an InputStream and converts it to a String.
    private String readIt(InputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "iso-8859-1"), 128);
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }
}
