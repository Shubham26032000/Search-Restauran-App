package com.example.getnearbyrestrarunt.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.getnearbyrestrarunt.Constant.Constant;
import com.example.getnearbyrestrarunt.Models.Restaurants;
import com.example.getnearbyrestrarunt.Models.RestraruntsList;
import com.example.getnearbyrestrarunt.R;
import com.example.getnearbyrestrarunt.ActivityAndInterface.RestaurantDetails;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RestaurantItemAdapter extends RecyclerView.Adapter<RestaurantItemAdapter.ViewHolder> {

    private Context context;
    private List<RestraruntsList> list;

    public RestaurantItemAdapter(Context context, List<RestraruntsList> list) {
        this.context = context;
        this.list = list;
    }

    public class ViewHolder  extends RecyclerView.ViewHolder{
        ImageView ivFeature;
        TextView tvName,tvCusine,tvAddress;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFeature = itemView.findViewById(R.id.ivFeature);
            tvName = itemView.findViewById(R.id.tvName);
            tvAddress  = itemView.findViewById(R.id.tvTiming);
            tvCusine = itemView.findViewById(R.id.tvCusines);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restarunt_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RestraruntsList restraruntsList = list.get(position);
        Restaurants restaurants = restraruntsList.getRestaurants();

        holder.tvName.setText(restaurants.getName());
        holder.tvAddress.setText(restaurants.getPhone_numbers());
        holder.tvCusine.setText(restaurants.getCuisines());
        if(!restaurants.getFeatured_image().isEmpty()){
            Picasso.with(context.getApplicationContext()).load(restaurants.getFeatured_image())
                    .into(holder.ivFeature);
        }else
        {

        }

        holder.itemView.setOnClickListener(v -> {

            Intent intent = new Intent(context, RestaurantDetails.class);
            intent.putExtra(Constant.NAME,restaurants.getName());
            intent.putExtra(Constant.IMAGE_URL,restaurants.getFeatured_image());
            intent.putExtra(Constant.CUISINES,restaurants.getCuisines());
            intent.putExtra(Constant.PHONE_NUMBER,restaurants.getPhone_numbers());
            intent.putExtra(Constant.TIMING,restaurants.getTimings());
            intent.putExtra(Constant.RATING,restaurants.getRating().getAggregate_rating());
            intent.putExtra(Constant.RATING_MESSAGE,restaurants.getRating().getRating_text());
            intent.putExtra(Constant.LATITUDE,restaurants.getLocation().getLatitude());
            intent.putExtra(Constant.LONGITUDE,restaurants.getLocation().getLongitude());
            intent.putExtra(Constant.URL,restaurants.getUrl());
            intent.putExtra(Constant.ADDRESS,restaurants.getLocation().getAddress());

            context.startActivity(intent);
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
