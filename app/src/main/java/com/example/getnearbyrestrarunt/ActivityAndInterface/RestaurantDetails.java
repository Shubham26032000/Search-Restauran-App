package com.example.getnearbyrestrarunt.ActivityAndInterface;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.getnearbyrestrarunt.Constant.Constant;
import com.example.getnearbyrestrarunt.R;
import com.squareup.picasso.Picasso;

public class RestaurantDetails extends AppCompatActivity {

    ImageView ivMainPic,ivlocation,ivphone,ivUrl,ivShare;
    TextView tvTime,tvcusinesData,tvAddress1,tvRating,tvNameRest,tvRatingMessage;

    String latitude="",longitude="", phone="",url="";
    String name="",cuisine="",address="",rating="",timing="",image_url="",ratingMessage="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);
        ivMainPic = findViewById(R.id.ivMainPic);
        ivlocation = findViewById(R.id.location);
        ivphone = findViewById(R.id.phone);
        ivUrl = findViewById(R.id.url);
        tvAddress1 = findViewById(R.id.tvAddress1);
        tvcusinesData = findViewById(R.id.tvcusinesData);
        tvTime = findViewById(R.id.time);
        tvRating = findViewById(R.id.tvRating);
        tvNameRest = findViewById(R.id.tvNameRest);
        tvRatingMessage = findViewById(R.id.tvRatingMessage);
        ivShare = findViewById(R.id.ivShare);
        LoadIntent();

        tvNameRest.setText(name);
        if(!image_url.isEmpty())
          Picasso.with(getApplicationContext()).load(image_url).into(ivMainPic);
        tvAddress1.setText(address);
        tvcusinesData.setText(cuisine);
        tvRating.setText(rating+"/"+"5");
        tvTime.setText(timing);

        ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = url;
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Get Near By Restaurant");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivityForResult(Intent.createChooser(sharingIntent, "Share via"),0);
            }
        });



        tvRatingMessage.setText(ratingMessage);
        ivUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        ivlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+latitude+","+longitude);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        ivphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:" + phone.subSequence(0,14)));
                startActivity(dialIntent);
            }
        });

    }

    public void LoadIntent(){
        Intent intent = getIntent();
        name = intent.getStringExtra(Constant.NAME);
        timing = intent.getStringExtra(Constant.TIMING);
        latitude = intent.getStringExtra(Constant.LATITUDE);
        longitude = intent.getStringExtra(Constant.LONGITUDE);
        phone = intent.getStringExtra(Constant.PHONE_NUMBER);
        url = intent.getStringExtra(Constant.URL);
        cuisine = intent.getStringExtra(Constant.CUISINES);
        address = intent.getStringExtra(Constant.ADDRESS);
        rating = intent.getStringExtra(Constant.RATING);
        image_url = intent.getStringExtra(Constant.IMAGE_URL);
        ratingMessage = intent.getStringExtra(Constant.RATING_MESSAGE);
    }
}