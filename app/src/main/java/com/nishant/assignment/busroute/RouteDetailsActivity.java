package com.nishant.assignment.busroute;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nishant.assignment.busroute.helper.RouteManager;
import com.nishant.assignment.busroute.models.Route;

import java.util.List;

/**
 * Created by nishant on 07-09-2017.
 */

public class RouteDetailsActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route_details);
        int position = getIntent().getIntExtra("position", -1);

        if(position >= 0) {
            Route route = RouteManager.getRouteManager().getRouteByIndex(position);

            ImageView routeImage = (ImageView) findViewById(R.id.routeImage2);
            routeImage.setImageBitmap(route.getImageBitmap());

            TextView routeName = (TextView) findViewById(R.id.routeName2);
            routeName.setText(route.getRouteName());

            TextView routeDetails = (TextView) findViewById(R.id.routeDescription);
            routeDetails.setText(route.getRouteDescription());

            ImageView accessibilty = (ImageView) findViewById(R.id.accessbilityImage);
            if (route.isRouteAccessible() ){
                accessibilty.setVisibility(View.VISIBLE);
            } else {
                accessibilty.setVisibility(View.INVISIBLE);
            }

            List<String> stops = route.getStopsInRoute();
            LinearLayout stopsLayout = (LinearLayout) findViewById(R.id.route_stop_layout);


            for( int i=0; i <= stops.size()-1;i++) {
                String stop = stops.get(i);
                LinearLayout ll = new LinearLayout(this);
                ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                ll.setOrientation(LinearLayout.HORIZONTAL);

                ImageView dotView = new ImageView(this);
                LinearLayout.LayoutParams Params1 = new LinearLayout.LayoutParams(100,100);
                dotView.setLayoutParams(Params1);
                dotView.setImageResource(R.drawable.dot);

                TextView stopNameView = new TextView(this);
                LinearLayout.LayoutParams textViewLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                textViewLayoutParams.setMargins(20, 30, 0, 0);
                stopNameView.setLayoutParams(textViewLayoutParams);
                stopNameView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                stopNameView.setText(stop);

                ll.addView(dotView);
                ll.addView(stopNameView);

                ImageView verticalLine = new ImageView(this);
                LinearLayout.LayoutParams Params2 = new LinearLayout.LayoutParams(100,130);
                verticalLine.setLayoutParams(Params2);
                verticalLine.setImageResource(R.drawable.vertical_line);

                stopsLayout.addView(ll);
                if(i != stops.size()-1)
                    stopsLayout.addView(verticalLine);
            }
        }
    }
}
