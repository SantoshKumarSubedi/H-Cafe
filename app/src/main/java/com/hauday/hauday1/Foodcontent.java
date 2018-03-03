package com.hauday.hauday1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hauday.hauday1.POJO.Food;

import java.net.URI;
import java.net.URL;

/**
 * Created by Hauday on 7/30/2016.
 */
public class Foodcontent extends AppCompatActivity {
    ImageView mFoodImage;
    TextView mName,mPrice,mDetail,mMaterial;
    Food food;
    Button mCall,mMessage,mOrder;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        food = (Food)getIntent().getSerializableExtra("key");


        mFoodImage=(ImageView) findViewById(R.id.content_food_detail_image);
        mName=(TextView) findViewById(R.id.content_food_detail_textview_name);
        mPrice=(TextView) findViewById(R.id.content_food_detail_textview_price);
        mDetail =(TextView) findViewById(R.id.content_food_detail_textview_detail);
        mMaterial=(TextView) findViewById(R.id.content_food_detail_textview_material);


        mName.setText("Name"+food.getName());
        mPrice.setText("Price"+food.getPrice());
        mDetail.setText("Detail"+food.getDetails());
        mMaterial.setText("Materials"+food.getMaterials());
        Glide.with(Foodcontent.this).load(food.getImage()).into(mFoodImage);


        mCall=(Button) findViewById(R.id.food_datail_call_button);
        mMessage=(Button) findViewById(R.id.food_detail_message_button);
        mOrder=(Button) findViewById(R.id.food_detail_order_button);
       mCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent=new Intent(Intent.ACTION_VIEW);
                callIntent.setData(Uri.parse("tel:9805205579"));
                startActivity(callIntent);

            }

        });
        mOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent orderintent=new Intent(Foodcontent.this,order_online.class);
                startActivity(orderintent);

            }
        });
    }


}
