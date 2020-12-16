package com.example.getnearbyrestrarunt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.getnearbyrestrarunt.Adapters.CollectionAdapter;
import com.example.getnearbyrestrarunt.Adapters.RestaurantItemAdapter;
import com.example.getnearbyrestrarunt.Models.CollectionModels.CollectionData;
import com.example.getnearbyrestrarunt.Models.CollectionModels.CollectionList;
import com.example.getnearbyrestrarunt.Models.LocationSearchData.LocationData;
import com.example.getnearbyrestrarunt.Models.LocationSearchData.LocationDataList;
import com.example.getnearbyrestrarunt.Models.RestraruntsList;
import com.example.getnearbyrestrarunt.Models.SearchRestrarunts;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar;

    JsonPlaceHolder jsonPlaceHolder;
    TextView tvName, tvAddress, tvCusines;
    EditText etLocation;
    ImageView btnSearch, btnSearchCusine;
    EditText etSearchString;
    RecyclerView recyclerView;
    RecyclerView recyclerViewHorizontal;

    TextView tvInfo1,tvInfo2;

    private double LATITUDE;
    private double LONGITUDE;
    private int ENTITY_ID;
    private int CITY_ID;
     LinearLayout linearLayout;

     String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvName = findViewById(R.id.tvName);
        tvAddress = findViewById(R.id.tvTiming);
        tvCusines = findViewById(R.id.tvCusines);
        etLocation = findViewById(R.id.etLocation);
        btnSearch = findViewById(R.id.btnSearchLocaton);
        recyclerView = findViewById(R.id.recyclerView1);

        recyclerViewHorizontal = findViewById(R.id.recyclerViewHorizontal);
        tvInfo1 = findViewById(R.id.tvInfo1);
        tvInfo2 = findViewById(R.id.tvInfo2);
        tvInfo1.setVisibility(View.INVISIBLE);
        tvInfo2.setVisibility(View.INVISIBLE);

         progressBar = findViewById(R.id.progressBar);


        linearLayout = findViewById(R.id.queryLayout);
        linearLayout.setVisibility(View.INVISIBLE);
        etSearchString = findViewById(R.id.etSearchString);
        btnSearchCusine = findViewById(R.id.btnSearchCusne);
        progressBar.setVisibility(View.INVISIBLE);


        etLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSearch.setVisibility(View.VISIBLE);
            }
        });



        btnSearch.setOnClickListener(v -> {
            btnSearch.setVisibility(View.INVISIBLE);
            if(!etLocation.getText().toString().trim().isEmpty()){
                String location = etLocation.getText().toString().trim().toLowerCase();
                linearLayout.setVisibility(View.VISIBLE);
                getLocation(location);
            }else{
                Toast.makeText(MainActivity.this, "Location Not Found", Toast.LENGTH_SHORT).show();
            }
        });
        btnSearchCusine.setOnClickListener(v -> {

            tvInfo1.setVisibility(View.INVISIBLE);
            tvInfo2.setVisibility(View.INVISIBLE);
            if(!etSearchString.getText().toString().isEmpty()){
                progressBar.setVisibility(View.VISIBLE);
                tvInfo1.setVisibility(View.VISIBLE);
                tvInfo2.setVisibility(View.VISIBLE);

                String searchQuery = etSearchString.getText().toString().trim().toLowerCase();
                Toast.makeText(this, searchQuery, Toast.LENGTH_SHORT).show();
                setCollections(CITY_ID);
                getRestaurantsData(searchQuery);
            }else{
                Toast.makeText(this, "Opps !", Toast.LENGTH_SHORT).show();
            }
        });


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://developers.zomato.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolder = retrofit.create(JsonPlaceHolder.class);





    }

    public void getRestaurantsData(String query) {
        this.query = query;
        Call<SearchRestrarunts> call = jsonPlaceHolder.getRestaurants(query,LATITUDE,LONGITUDE,ENTITY_ID);

        call.enqueue(new Callback<SearchRestrarunts>() {
            @Override
            public void onResponse(Call<SearchRestrarunts> call, Response<SearchRestrarunts> response) {
                SearchRestrarunts searchRestrarunts = response.body();
                progressBar.setVisibility(View.INVISIBLE);
                if(!response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Error in Search Rest"+response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<RestraruntsList> lists = searchRestrarunts.getRestraruntsLists();
                recyclerView.setHasFixedSize(true);
                if(lists.size() != 0 && !lists.isEmpty()){

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                    recyclerView.setLayoutManager(linearLayoutManager);

                    RestaurantItemAdapter itemAdapter = new RestaurantItemAdapter(MainActivity.this,lists);
                    recyclerView.setAdapter(itemAdapter);

                }else{
                    Toast.makeText(MainActivity.this, "Empyt List"+lists.size(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SearchRestrarunts> call, Throwable t) {

            }
        });
    }

    private void getLocation(String location) {
        Call<LocationData> call = jsonPlaceHolder.getLocationCords(location);

        call.enqueue(new Callback<LocationData>() {
            @Override
            public void onResponse(Call<LocationData> call, Response<LocationData> response) {
                progressBar.setVisibility(View.INVISIBLE);
                if(!response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Location Not Found "+response.code(), Toast.LENGTH_SHORT).show();
                }
                LocationData Data = response.body();
                List<LocationDataList> lists = Data.getLocation_suggestions();

                for (int i=0;i< lists.size();i++){
                    LocationDataList locationDataList = lists.get(i);
                    LATITUDE = locationDataList.getLatitude();
                    LONGITUDE = locationDataList.getLongitude();
                    ENTITY_ID = locationDataList.getEntity_id();
                    CITY_ID = locationDataList.getCity_id();

                }
            }
            @Override
            public void onFailure(Call<LocationData> call, Throwable t) {
                Toast.makeText(MainActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setCollections(int city_id){
        recyclerViewHorizontal.setHasFixedSize(true);
        LinearLayoutManager layoutManager =new LinearLayoutManager(MainActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        Call<CollectionData> call1 = jsonPlaceHolder.getCollection(city_id);

        call1.enqueue(new Callback<CollectionData>() {
            @Override
            public void onResponse(Call<CollectionData> call, Response<CollectionData> response) {
                progressBar.setVisibility(View.INVISIBLE);
                if(!response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Error"+response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                CollectionData collectionData = response.body();
                List<CollectionList> lists= collectionData.getCollectionLists();


                recyclerViewHorizontal.setLayoutManager(layoutManager);
                CollectionAdapter adapter = new CollectionAdapter(MainActivity.this,lists);
                recyclerViewHorizontal.setAdapter(adapter);


            }

            @Override
            public void onFailure(Call<CollectionData> call, Throwable t) {

            }
        });


    }






}