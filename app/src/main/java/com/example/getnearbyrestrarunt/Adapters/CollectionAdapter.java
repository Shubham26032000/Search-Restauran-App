package com.example.getnearbyrestrarunt.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.getnearbyrestrarunt.MainActivity;
import com.example.getnearbyrestrarunt.Models.CollectionModels.CollectionData;
import com.example.getnearbyrestrarunt.Models.CollectionModels.CollectionList;
import com.example.getnearbyrestrarunt.Models.CollectionModels.CollectionMainData;
import com.example.getnearbyrestrarunt.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CollectionAdapter  extends RecyclerView.Adapter<CollectionAdapter.ViewHolder2> {

    private Context context;
    private List<CollectionList> list;


    public CollectionAdapter(Context context, List<CollectionList> list) {
        this.context = context;
        this.list = list;
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder {
        ImageView ivCollection;
        TextView tvTitle;
        public ViewHolder2(@NonNull View itemView) {
            super(itemView);
            ivCollection = itemView.findViewById(R.id.ivCollection);
            tvTitle = itemView.findViewById(R.id.tvEvent);


        }
    }


    @NonNull
    @Override
    public ViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.collections_item,parent,false);
        return new ViewHolder2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder2 holder, int position) {
        CollectionList collectionList = list.get(position);
        CollectionMainData collectionMainData = collectionList.getCollections();
        holder.tvTitle.setText(collectionMainData.getTitle());

        if(!collectionMainData.getImage_url().isEmpty()){
            Picasso.with(context).load(collectionMainData.getImage_url()).into(holder.ivCollection);
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }





}
