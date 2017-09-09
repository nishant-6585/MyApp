package com.nishant.assignment.busroute;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.nishant.assignment.busroute.helper.Downloader;
import com.nishant.assignment.busroute.helper.DownloaderHelper;
import com.nishant.assignment.busroute.helper.RouteDownloaderHelper;
import com.nishant.assignment.busroute.helper.RouteManager;
import com.nishant.assignment.busroute.models.Route;

import java.util.ArrayList;

public class RouteActivity extends Activity {

    RecyclerView routeListView;
    private static final Handler UIHandler;
    private static ProgressDialog progressDialog;
    private  static RecyclerView.Adapter routeListAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        routeListView = (RecyclerView) findViewById(R.id.routeList);
        routeListView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        routeListView.setLayoutManager(mLayoutManager);

        RouteListAdapter.RecyclerViewClickListener listener = new RouteListAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(RouteActivity.this, RouteDetailsActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        };

        routeListAdapter = new RouteListAdapter(listener);
        routeListView.setAdapter(routeListAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        downloadRouteDetails();
    }

    private void downloadRouteDetails() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            progressDialog = ProgressDialog.show(this, "Downloading Routes", "Please wait..", true, false);
            new Downloader(new RouteDownloaderHelper(), this).execute(DownloaderHelper.ROUTES_URL);
        }
    }

    public static void updateUI(Context mContext) {
        if (progressDialog != null) {
            runOnUI(new Runnable() {
                @Override
                public void run() {
                    ArrayList<Route> routeList = RouteManager.getRouteManager().getAllRoutes();
                    if (routeList != null) {
                        routeListAdapter.notifyDataSetChanged();
                    }
                    progressDialog.dismiss();
                }
            });
        }
    }

    public static void runOnUI(Runnable runnable) {
        UIHandler.post(runnable);
    }

    static {
        UIHandler = new Handler(Looper.getMainLooper());
    }
}
