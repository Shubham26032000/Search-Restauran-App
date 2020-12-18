package com.example.getnearbyrestrarunt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
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

public class MainActivity extends AppCompatActivity implements CollectionAdapter.OnItemClick{
    ProgressBar progressBar;

    JsonPlaceHolder jsonPlaceHolder;
    TextView tvName, tvAddress, tvCusines;


    RecyclerView recyclerView;
    RecyclerView recyclerViewHorizontal;
    ImageView ivHandler;
    TextView tvHandler;

    androidx.appcompat.widget.SearchView searchLocation;
    androidx.appcompat.widget.SearchView searchCusineView;

    TextView tvInfo1,tvInfo2;

    private double LATITUDE;
    private double LONGITUDE;
    private int ENTITY_ID;
    private int CITY_ID;
     LinearLayout linearLayout;

     String query;

    List<CollectionList> lists;
    LinearLayout internetLayout;
    LinearLayoutCompat layoutCompat;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvName = findViewById(R.id.tvName);
        tvAddress = findViewById(R.id.tvTiming);
        tvCusines = findViewById(R.id.tvCusines);

        searchLocation = findViewById(R.id.searchLocation);
        searchCusineView = findViewById(R.id.searchCusineView);
        recyclerView = findViewById(R.id.recyclerView1);
        layoutCompat = findViewById(R.id.mainLayout);

        ivHandler = findViewById(R.id.ivHandler);
        tvHandler = findViewById(R.id.tvHandler);

        recyclerViewHorizontal = findViewById(R.id.recyclerViewHorizontal);
        tvInfo1 = findViewById(R.id.tvInfo1);
        tvInfo1.setVisibility(View.GONE);
        tvInfo2 = findViewById(R.id.tvInfo2);
        tvInfo2.setVisibility(View.GONE);
        tvInfo1.setVisibility(View.INVISIBLE);
        tvInfo2.setVisibility(View.INVISIBLE);

        internetLayout = findViewById(R.id.internet_layout);

         progressBar = findViewById(R.id.progressBar);
        isConnected();

        linearLayout = findViewById(R.id.queryLayout);
        linearLayout.setVisibility(View.INVISIBLE);

        progressBar.setVisibility(View.INVISIBLE);

        searchCusineView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                internetLayout.setVisibility(View.INVISIBLE);
                tvInfo1.setVisibility(View.INVISIBLE);
                tvInfo2.setVisibility(View.INVISIBLE);
                if(!query.isEmpty()){
                    progressBar.setVisibility(View.VISIBLE);
                    String searchQuery = query.trim().toLowerCase();
                    Toast.makeText(MainActivity.this, "Searching...", Toast.LENGTH_SHORT).show();
                    setCollections(CITY_ID);
                    getRestaurantsData(searchQuery);
                }else{
                    Toast.makeText(MainActivity.this, "Please search Cuisine/restaurant", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchLocation.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                internetLayout.setVisibility(View.INVISIBLE);
                internetLayout.setVisibility(View.INVISIBLE);
                if(!query.trim().isEmpty()){
                    String location = query.trim().toLowerCase();
                    getLocation(location);
                }else{
                    Toast.makeText(MainActivity.this, "Please Enter Location", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });









        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://developers.zomato.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolder = retrofit.create(JsonPlaceHolder.class);





    }

    public void getRestaurantsData(String query) {
        isConnected();
        this.query = query;
        Call<SearchRestrarunts> call = jsonPlaceHolder.getRestaurants(query,LATITUDE,LONGITUDE,ENTITY_ID);

        call.enqueue(new Callback<SearchRestrarunts>() {
            @Override
            public void onResponse(Call<SearchRestrarunts> call, Response<SearchRestrarunts> response) {
                SearchRestrarunts searchRestrarunts = response.body();
                progressBar.setVisibility(View.INVISIBLE);
                if(!response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Not Found Restaurants!", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<RestraruntsList> lists = searchRestrarunts.getRestraruntsLists();
                recyclerView.setHasFixedSize(true);
                if(lists.size() != 0 && !lists.isEmpty()){
                    tvInfo2.setVisibility(View.VISIBLE);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                    recyclerView.setLayoutManager(linearLayoutManager);

                    RestaurantItemAdapter itemAdapter = new RestaurantItemAdapter(MainActivity.this,lists);
                    recyclerView.setAdapter(itemAdapter);

                }else{
                    resultNotFound();
                }

            }

            @Override
            public void onFailure(Call<SearchRestrarunts> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Restaurants Not found", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void getRestaurantsDeafult(double latitude,double longitude){
        isConnected();
        this.query = query;
        Call<SearchRestrarunts> call = jsonPlaceHolder.getRestaurantsBylocation(LATITUDE,LONGITUDE);

        call.enqueue(new Callback<SearchRestrarunts>() {
            @Override
            public void onResponse(Call<SearchRestrarunts> call, Response<SearchRestrarunts> response) {
                SearchRestrarunts searchRestrarunts = response.body();
                progressBar.setVisibility(View.INVISIBLE);
                if(!response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Not Found Restaurants!", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<RestraruntsList> lists = searchRestrarunts.getRestraruntsLists();
                recyclerView.setHasFixedSize(true);
                if(lists.size() != 0 && !lists.isEmpty()){
                    tvInfo2.setVisibility(View.VISIBLE);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                    recyclerView.setLayoutManager(linearLayoutManager);

                    RestaurantItemAdapter itemAdapter = new RestaurantItemAdapter(MainActivity.this,lists);
                    recyclerView.setAdapter(itemAdapter);

                }else{
                    resultNotFound();
                }

            }

            @Override
            public void onFailure(Call<SearchRestrarunts> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Restaurants Not found", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void getLocation(String location) {
        isConnected();
        Call<LocationData> call = jsonPlaceHolder.getLocationCords(location);

        call.enqueue(new Callback<LocationData>() {
            @Override
            public void onResponse(Call<LocationData> call, Response<LocationData> response) {
                progressBar.setVisibility(View.INVISIBLE);
                if(!response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                    return;
                }
                LocationData Data = response.body();
                List<LocationDataList> lists = Data.getLocation_suggestions();
                if (lists.size() == 0){
                   resultNotFound(0);
                    return;
                }else {
                    linearLayout.setVisibility(View.VISIBLE);
                }
                for (int i=0;i< lists.size();i++){
                    LocationDataList locationDataList = lists.get(i);
                    LATITUDE = locationDataList.getLatitude();
                    LONGITUDE = locationDataList.getLongitude();
                    ENTITY_ID = locationDataList.getEntity_id();
                    CITY_ID = locationDataList.getCity_id();

                }
                getRestaurantsDeafult(LATITUDE,LONGITUDE);
            }
            @Override
            public void onFailure(Call<LocationData> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Sorry! our services not present on this location", Toast.LENGTH_SHORT).show();
            }

        });

    }

    private void setCollections(int city_id){
        isConnected();
        recyclerViewHorizontal.setHasFixedSize(true);
        LinearLayoutManager layoutManager =new LinearLayoutManager(MainActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        Call<CollectionData> call1 = jsonPlaceHolder.getCollection(city_id);

        call1.enqueue(new Callback<CollectionData>() {
            @Override
            public void onResponse(Call<CollectionData> call, Response<CollectionData> response) {
                progressBar.setVisibility(View.INVISIBLE);
                if(!response.isSuccessful()){

                    isConnected();
                    return;
                }
                CollectionData collectionData = response.body();
                lists= collectionData.getCollectionLists();

                if(lists != null) {
                    if(lists.size() != 0) {
                        tvInfo1.setVisibility(View.VISIBLE);
                        recyclerViewHorizontal.setLayoutManager(layoutManager);
                        CollectionAdapter adapter = new CollectionAdapter(MainActivity.this, lists);
                        recyclerViewHorizontal.setAdapter(adapter);
                    }else {
                        resultNotFound();
                    }
                }else {
                    resultNotFound();
                }

            }

            @Override
            public void onFailure(Call<CollectionData> call, Throwable t) {
//                resultNotFound();
                Toast.makeText(MainActivity.this, "In Fails", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void getRestaurantsData(int collection_id) {
        isConnected();

        Call<SearchRestrarunts> call = jsonPlaceHolder.getRestaurants(collection_id);

        call.enqueue(new Callback<SearchRestrarunts>() {
            @Override
            public void onResponse(Call<SearchRestrarunts> call, Response<SearchRestrarunts> response) {
                SearchRestrarunts searchRestrarunts = response.body();
                progressBar.setVisibility(View.INVISIBLE);
                if(!response.isSuccessful()){
                    isConnected();
                    return;
                }
                List<RestraruntsList> lists = searchRestrarunts.getRestraruntsLists();
                recyclerView.setHasFixedSize(true);
                if(lists.size() != 0 && !lists.isEmpty()){
                    tvInfo2.setVisibility(View.VISIBLE);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                    recyclerView.setLayoutManager(linearLayoutManager);

                    RestaurantItemAdapter itemAdapter = new RestaurantItemAdapter(MainActivity.this,lists);
                    recyclerView.setAdapter(itemAdapter);

                }else {
                    resultNotFound();
                }

            }

            @Override
            public void onFailure(Call<SearchRestrarunts> call, Throwable t) {
//                resultNotFound();
                Toast.makeText(MainActivity.this, "Restaurant not found!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onItemClick(int position) {
        CollectionList list = lists.get(position);
        int collection_id = list.getCollections().getCollection_id();

        getRestaurantsData(collection_id);

    }

    public void isConnected(){
        ConnectivityManager cm =
                (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        assert cm != null;
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected){
            internetLayout.setVisibility(View.GONE);
            layoutCompat.setVisibility(View.VISIBLE);
        }else{
            internetLayout.setVisibility(View.VISIBLE);
            layoutCompat.setVisibility(View.GONE);
            tvHandler.setText("Oops make Sure to Connect Internet!");
            ivHandler.setVisibility(View.VISIBLE);
            ivHandler.setImageResource(R.drawable.icons8_without_internet_50);
        }
        return;
    }


    public void resultNotFound(){
        internetLayout.setVisibility(View.VISIBLE);
        layoutCompat.setVisibility(View.GONE);
        tvHandler.setText("Sorry! not matching restaurants to your result");
        ivHandler.setImageResource(R.drawable.ic_error_foreground);
        ivHandler.setVisibility(View.VISIBLE);
    }
    public void resultNotFound(int a){
        internetLayout.setVisibility(View.VISIBLE);
        layoutCompat.setVisibility(View.GONE);
        tvHandler.setText("Sorry For Inconvenience ! Our services are not present for that location");
        ivHandler.setImageResource(R.drawable.ic_error_foreground);
        ivHandler.setVisibility(View.VISIBLE);
    }

}