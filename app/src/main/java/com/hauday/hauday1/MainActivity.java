package com.hauday.hauday1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.hauday.hauday1.Adpater.FoodAdapter;
import com.hauday.hauday1.Helper.GlobalState;
import com.hauday.hauday1.POJO.Food;
import com.hauday.hauday1.json_connecter.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Button mLogout;
    JsonParser json;
    ProgressDialog progressDialog;
    JSONObject jsonObject;
    int flag;
    Food food;
    ArrayList<Food> foodArrayList=new ArrayList<>();
    ListView mlistview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mLogout=(Button) findViewById(R.id.activity_main_logout_button);

        mlistview=(ListView) findViewById(R.id.activity_main_list_view);

        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,Loginact.class);
                GlobalState state=GlobalState.singleton;
                state.setPrefscheckedLogin("false",1);
                startActivity(intent);
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        new List().execute();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_map) {
            Intent intent =new Intent(MainActivity.this,ViewMap.class);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    class List extends AsyncTask<String,String,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Loading");
            progressDialog.setCancelable(false);
            progressDialog.show();
            ArrayList<Food> foodArrayList=new ArrayList<>();
        }

        @Override
        protected String doInBackground(String... params) {
            json=new JsonParser();
            HashMap<String,String> hashMap=new HashMap<>();
            JSONObject jsonObject=json.performPostCI("http://kinbech.6te.net/ResturantFoods/api/showmenulist",hashMap);
          try {
              if (jsonObject == null) {
                flag=1;
              } else if (jsonObject.getString("status").equals("success")) {
                flag=2;
                  JSONArray jsonArray=jsonObject.getJSONArray("data");
                  for(int i=0;i<jsonArray.length();i++){
                      JSONObject dataJsonObject=jsonArray.getJSONObject(i);
                      String name=dataJsonObject.getString("name");
                      String id=dataJsonObject.getString("id");
                      String price=dataJsonObject.getString("price");
                      String detail=dataJsonObject.getString("details");
                      String image=dataJsonObject.getString("image");
                      String materials=dataJsonObject.getString("materials");

                      food=new Food(name,id,price,detail,image,materials);
                      foodArrayList.add(food);
                  }




              }else{
                  flag=3;
              }

          }catch (JSONException e){

          }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if(flag==1){
                Toast.makeText(MainActivity.this,"Network/Server error",Toast.LENGTH_SHORT).show();
            }else if(flag==2){
                Toast.makeText(MainActivity.this,"success",Toast.LENGTH_SHORT).show();
                FoodAdapter foodAdapter=new FoodAdapter(MainActivity.this,foodArrayList);
                mlistview.setAdapter(foodAdapter);
                mlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Food individualFood=foodArrayList.get(position);
                        Intent intent =new Intent(MainActivity.this,Foodcontent.class);
                        intent.putExtra("key",individualFood);
                        startActivity(intent);

                    }
                });



            }else{
                Toast.makeText(MainActivity.this,"Try Again",Toast.LENGTH_SHORT).show();
            }

        }
    }

}

