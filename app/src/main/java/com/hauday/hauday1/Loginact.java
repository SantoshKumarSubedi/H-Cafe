package com.hauday.hauday1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hauday.hauday1.Helper.GlobalState;
import com.hauday.hauday1.json_connecter.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;

/**
 * Created by Hauday on 7/4/2016.
 */
public class Loginact extends AppCompatActivity {
    ProgressDialog dilogue;
    Button mLoginbutton, mSignupbutton;
    TextView mReset;
    EditText mEmailEditText,mPasswordEditText;
    String sEmail,sPassword;
    JsonParser json;
    int flag;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        mLoginbutton = (Button) findViewById(R.id.login_login_button);
        mSignupbutton = (Button) findViewById(R.id.login_sing_up_button);
        mEmailEditText=(EditText) findViewById(R.id.username_activity);
        mPasswordEditText=(EditText) findViewById(R.id.password_activity);
        mLoginbutton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                sEmail=mEmailEditText.getText().toString();
                sPassword=mPasswordEditText.getText().toString();
                if(mEmailEditText.getText().length()<1 && mPasswordEditText.getText().length()<1){
                    Toast.makeText(Loginact.this, "please fill all the field.", Toast.LENGTH_SHORT).show();
                }else if(mEmailEditText.getText().length()>0 && mPasswordEditText.getText().length()<1){
                    Toast.makeText(Loginact.this,"please enter Password",Toast.LENGTH_SHORT).show();
                }else if(mEmailEditText.getText().length()<1 && mPasswordEditText.getText().length()>0){
                    Toast.makeText(Loginact.this,"please enter email",Toast.LENGTH_SHORT).show();
                }
                else{
                        new loginOnline().execute();

                }
            }
        }
    );
                mSignupbutton.setOnClickListener(new View.OnClickListener() {

                                                     @Override
                                                     public void onClick(View v) {
                                                         Intent intent= new Intent(Loginact.this,Signup.class);
                                                         startActivity(intent);
                                                     }
                                                 }


                );
        mReset=(TextView) findViewById(R.id.Reset_Activity);
        mReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Loginact.this,"Wait for a minute",Toast.LENGTH_SHORT).show();
            }
        });

    }
class loginOnline extends AsyncTask<String,String,String >{
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dilogue=new ProgressDialog(Loginact.this);
        dilogue.setMessage("Loading");
        dilogue.setCancelable(false);
        dilogue.show();
    }

    @Override
    protected String doInBackground(String... params) {
       json=new JsonParser();
        HashMap<String,String> loginhasmap=new HashMap<>();
        loginhasmap.put("email",sEmail);
        loginhasmap.put("password",sPassword);
        JSONObject jsonObject=json.performPostCI("http://kinbech.6te.net/ResturantFoods/api/login",loginhasmap);
try {
    if (jsonObject == null) {
            flag=1;
    } else if (jsonObject.getString("status").equals("success")) {
            flag=2;
    } else {
            flag=3;
    }
}catch(JSONException e){

}

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    if (flag==1){
        Toast.makeText(Loginact.this,"server/network issue",Toast.LENGTH_SHORT).show();
    }else if (flag==2){
        Toast.makeText(Loginact.this,"successful",Toast.LENGTH_SHORT).show();
        Intent intent= new Intent(Loginact.this,MainActivity.class);
        GlobalState globalState=new GlobalState().singleton;
        globalState.setPrefscheckedLogin("true",1);
        startActivity(intent);
    }else{
        Toast.makeText(Loginact.this, "Please try again", Toast.LENGTH_SHORT).show();

        }
        dilogue.dismiss();
    }
}

}
