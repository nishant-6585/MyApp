package com.nishant.assignment.busroute;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nishant.assignment.busroute.helper.RouteManager;
import com.nishant.assignment.busroute.models.Route;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by nishant on 06-09-2017.
 */

class RouteListAdapter extends RecyclerView.Adapter<RouteListAdapter.ViewHolder> {

    private RecyclerViewClickListener mListener;

    RouteListAdapter(RecyclerViewClickListener listener) {
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.route_child_view, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v, mListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ArrayList<Route> routeList = RouteManager.getRouteManager().getAllRoutes();
        holder.routeName.setText(routeList.get(position).getRouteName());
        holder.routeImage.setImageBitmap(routeList.get(position).getImageBitmap());
    }

    @Override
    public int getItemCount() {
        ArrayList<Route> routeList = RouteManager.getRouteManager().getAllRoutes();
        if (routeList != null) {
            return routeList.size();
        }
        return 0;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // each data item is just a string in this case
        public ImageView routeImage;
        public TextView routeName;
        public View layout;
        private RecyclerViewClickListener mListener;

        public ViewHolder(View v, RecyclerViewClickListener listener) {
            super(v);
            mListener = listener;
            v.setOnClickListener(this);
            layout = v;
            routeImage = (ImageView) v.findViewById(R.id.routeImage);
            routeName = (TextView) v.findViewById(R.id.routeName);
        }

        @Override
        public void onClick(View view) {
            mListener.onClick(view, getAdapterPosition());
        }
    }

    public interface RecyclerViewClickListener {

        void onClick(View view, int position);
    }
}


