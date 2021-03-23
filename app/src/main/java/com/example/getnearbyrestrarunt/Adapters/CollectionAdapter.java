package com.example.getnearbyrestrarunt.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.getnearbyrestrarunt.Models.CollectionModels.CollectionList;
import com.example.getnearbyrestrarunt.Models.CollectionModels.CollectionMainData;
import com.example.getnearbyrestrarunt.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CollectionAdapter  extends RecyclerView.Adapter<CollectionAdapter.CollectionViewHolder> {


    OnItemClick activity;
    public interface OnItemClick{
         void onItemClick(int position);
    }
    private Context context;
    private List<CollectionList> list;


    public CollectionAdapter(Context context, List<CollectionList> list) {
        this.activity = (OnItemClick) context;
        this.list = list;
    }

    public class CollectionViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCollection;
        TextView tvTitle;
        public CollectionViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCollection = itemView.findViewById(R.id.ivCollection);
            tvTitle = itemView.findViewById(R.id.tvEvent);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        activity.onItemClick(getAdapterPosition());
                }
            });
        }
    }


    @NonNull
    @Override
    public CollectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.collections_item,parent,false);
        return new CollectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CollectionViewHolder holder, int position) {
        CollectionList collectionList = list.get(position);
        CollectionMainData collectionMainData = collectionList.getCollections();
        holder.tvTitle.setText(collectionMainData.getTitle());

        if(!collectionMainData.getImage_url().isEmpty()){
            Picasso.with((Context) activity).load(collectionMainData.getImage_url()).into(holder.ivCollection);
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }





}
