package com.hauday.hauday1;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothClass;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.UriPermission;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hauday.hauday1.json_connecter.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;

/**
 * Created by Hauday on 7/4/2016.
 */
public class Signup extends AppCompatActivity{
    ProgressDialog progressDialog;
    Button mRegisterbutton;
    ImageButton mFacebook,mgoogle;
    EditText mEmail,mpassword,musername,mconfirmPassword,mNumber,mAddress;
    String sSignupEmail,sSignupPassword,sUsername,sConsformPasswword,sNumber,sAddress;
    JsonParser json1;
    int flag1;
    String pattern;
    String google="http://www.gmail.com",fb="http://www.facebook.com";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
        pattern=".+?@(.+)+";
        mRegisterbutton=(Button)findViewById(R.id.signup_register_button);
        mEmail=(EditText) findViewById(R.id.email_activity);
        mpassword=(EditText) findViewById(R.id.password_activity_signup);
        musername=(EditText) findViewById(R.id.username_activity_signup);
        mconfirmPassword=(EditText) findViewById(R.id.confirm_password_signup);
        mNumber=(EditText) findViewById(R.id.number_activity_signup);
        mAddress=(EditText) findViewById(R.id.address_activity_signup);

        mRegisterbutton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                sNumber=mNumber.getText().toString();
                sSignupEmail=mEmail.getText().toString();
                sSignupPassword=mpassword.getText().toString();
                sUsername=musername.getText().toString();
                sConsformPasswword=mconfirmPassword.getText().toString();
                sAddress=mAddress.getText().toString();

               if(mEmail.getText().length()<1 ||
                       mpassword.getText().length()<1||
                       musername.getText().length()<1 ||
                       mpassword.getText().length()<1 ||
                       mAddress.getText().length()<1||
                       mNumber.getText().length()<1){
                   Toast.makeText(Signup.this, "Please,fill all the form properly.......",Toast.LENGTH_SHORT).show();
               }else if (!mpassword.getText().toString().equals(mconfirmPassword.getText().toString())){
                   Toast.makeText(Signup.this,"password doesn,t match",Toast.LENGTH_SHORT).show();
               }else if(!sSignupEmail.matches(pattern)){
                         Toast.makeText(Signup.this,"Invalid email",Toast.LENGTH_SHORT).show();
                }

                else{
                   Toast.makeText(Signup.this,"you have checked in",Toast.LENGTH_SHORT).show();
                  new OnlineRegister().execute();

               }


            }
        });
        mFacebook=(ImageButton)findViewById(R.id.Facebook_Activity);
        mFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Signup.this,"Redirecting to Facebook",Toast.LENGTH_SHORT).show();
               try {
                   Intent i = new Intent(Intent.ACTION_VIEW);
                   i.setData(Uri.parse(fb));
                   startActivity(i);

               }catch (ActivityNotFoundException e){
                   Toast.makeText(Signup.this,"Browser not found",Toast.LENGTH_SHORT).show();
               }
            }
        });

        mgoogle=(ImageButton)findViewById(R.id.google_Activity);
       mgoogle.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Toast.makeText(Signup.this, "Redirecting to Google", Toast.LENGTH_SHORT).show();
               try {
                   Intent i=new Intent(Intent.ACTION_VIEW);
                   i.setData(Uri.parse(google));
                   startActivity(i);
               }catch(ActivityNotFoundException e){
                   Toast.makeText(Signup.this,"Browser not found",Toast.LENGTH_SHORT).show();

               }
           }
       });

    }


class OnlineRegister extends AsyncTask<String,String,String > {
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(Signup.this);
        progressDialog.setMessage("loading");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        json1 = new JsonParser();
        HashMap<String, String> loginhasmap1 = new HashMap<>();
        loginhasmap1.put("email", sSignupEmail);
        loginhasmap1.put("password", sSignupPassword);
        loginhasmap1.put("name", sUsername);
        loginhasmap1.put("address", sAddress);
        loginhasmap1.put("phone", sNumber);
        JSONObject jsonObject1 = json1.performPostCI("http://kinbech.6te.net/ResturantFoods/api/register", loginhasmap1);
        try {
            if (jsonObject1 == null) {
                flag1 = 1;
            } else if (jsonObject1.getString("status").equals("success")) {
                flag1 = 2;
            } else {
                flag1 = 3;
            }
        } catch (JSONException e) {

        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (flag1==1){
            Toast.makeText(Signup.this,"network error",Toast.LENGTH_SHORT).show();
        }else if(flag1==2){
            Toast.makeText(Signup.this,"successful",Toast.LENGTH_SHORT).show();
            Intent intent1= new Intent(Signup.this,MainActivity.class);
            startActivity(intent1);
        }else{
         Toast.makeText(Signup.this,"Try Again",Toast.LENGTH_SHORT).show();
        }
        progressDialog.dismiss();
    }

}

}
