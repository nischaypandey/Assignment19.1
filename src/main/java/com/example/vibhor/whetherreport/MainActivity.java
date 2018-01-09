package com.example.vibhor.whetherreport;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//MainActivity class which is extending AppCompatActivity
public class MainActivity extends AppCompatActivity implements HandleJsonListener {

    //API url stored in string URl
    private String URL = "http://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b1b15e88fa797225412429c1c50c122a1";

    //creating objects of textView of UI components
    private TextView coordmLon,coordmLat;
    private TextView weather_mid,weather_mMain,weather_mdescription,weather_micon;
    private TextView mBase;
    private TextView main_mTemp,main_mPressure,main_mMintemp,main_mMaxtemp,main_mhumidity;
    private TextView mVisibility;
    private TextView wind_mSpeed,wind_mDegree;
    private TextView clouds_mAll;
    private TextView mDt;
    private TextView sys_mType,sys_mId,sys_mMessage,sys_mCountry,sys_mSunrise,sys_mSunset;
    private TextView miD,mName,mcod;

    //onCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setting reference with their ID's
        coordmLon = findViewById(R.id.lon);
        coordmLat = findViewById(R.id.lat);
        weather_mid = findViewById(R.id.weather_id);
        weather_mMain = findViewById(R.id.weather_main);
        weather_mdescription = findViewById(R.id.weather_description);
        weather_micon = findViewById(R.id.weather_icon);
        mBase = findViewById(R.id.base);
        main_mTemp = findViewById(R.id.main_temp);
        main_mPressure = findViewById(R.id.main_pressure);
        main_mMintemp = findViewById(R.id.main_temp_min);
        main_mMaxtemp = findViewById(R.id.main_temp_max);
        main_mhumidity = findViewById(R.id.main_humidity);
        mVisibility = findViewById(R.id.visibility);
        wind_mSpeed = findViewById(R.id.wind_speed);
        wind_mDegree = findViewById(R.id.wind_degree);
        clouds_mAll = findViewById(R.id.clouds_all);
        mDt = findViewById(R.id.dt);
        sys_mType = findViewById(R.id.sys_type);
        sys_mId = findViewById(R.id.sys_id1);
        sys_mMessage = findViewById(R.id.sys_message);
        sys_mCountry = findViewById(R.id.sys_country);
        sys_mSunrise = findViewById(R.id.sys_sunrise);
        sys_mSunset = findViewById(R.id.sys_sunset);
        miD = findViewById(R.id.id);
        mName = findViewById(R.id.name);
        mcod = findViewById(R.id.cod);

        //checking if device is connected to internet or not
        if(isIntenetConnected())
        {
            //creating interface object
            NetworkRequestTask networkRequestTask = new NetworkRequestTask(MainActivity.this,URL,this);

            //executing the interface
            networkRequestTask.execute();
        }
        else
        {
            //displaying toast message
            Toast.makeText(MainActivity.this,"No Intenet connection found",Toast.LENGTH_SHORT).show();
        }
    }

    //checking connection status of the device
    private boolean isIntenetConnected()
    {
        //initialising status of the device whether is connected to internet or not
        boolean isConnected=false;

        //creating connectivityManager object to check connection status
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        //getting network information
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        //if connected to internet then changing its value
        if(networkInfo!=null)
        {
            isConnected = true;
        }

        //returning connection status
        return isConnected;
    }

    //method to handle the json parsing
    @Override
    public void updateData(String json)
    {
        try {

            //creating parent Json object
            JSONObject parentObject = new JSONObject(json);

            //setting values from corresponding fields
            mBase.setText("Base :"+parentObject.getString("base"));
            mVisibility.setText("Visibility :"+parentObject.getString("visibility"));
            mDt.setText("dt :"+parentObject.getString("dt"));
            miD.setText("Id :"+parentObject.getString("id"));
            mName.setText("Name :"+parentObject.getString("name"));
            mcod.setText("Cod :"+parentObject.getString("cod"));

            //getting array of Json objects from name weather
            JSONArray array = parentObject.getJSONArray("weather");

            //creating weatherobject object to access data of weather JSon array
            JSONObject weatherObject = array.getJSONObject(0);

            //setting values from corresponding fields
            weather_mid.setText("Id :"+weatherObject.getString("id"));
            weather_mMain.setText("Main :"+weatherObject.getString("main"));
            weather_mdescription.setText("Description :"+weatherObject.getString("description"));
            weather_micon.setText("Icon :"+weatherObject.getString("icon"));

            //creating coordrobject object to access data of weather JSon array
            JSONObject coordObject = parentObject.getJSONObject("coord");

            //setting values from corresponding fields
            coordmLat.setText("Lat :"+coordObject.getString("lat"));
            coordmLon.setText("Lon :"+coordObject.getString("lon"));

            //creating mainobject object to access data of weather JSon array
            JSONObject mainObject = parentObject.getJSONObject("main");

            //setting values from corresponding fields
            main_mTemp.setText("Temp :"+mainObject.getString("temp"));
            main_mPressure.setText("Pressure :"+mainObject.getString("pressure"));
            main_mMaxtemp.setText("Temp_max :"+mainObject.getString("temp_max"));
            main_mMintemp.setText("Temp_min"+mainObject.getString("temp_min"));
            main_mhumidity.setText("Humidity :"+mainObject.getString("humidity"));

            //creating windobject object to access data of weather JSon array
            JSONObject windObject = parentObject.getJSONObject("wind");

            //setting values from corresponding fields
            wind_mSpeed.setText("Speed :"+windObject.getString("speed"));
            wind_mDegree.setText("Deg :"+windObject.getString("deg"));

            //creating cloudobject object to access data of weather JSon array
            JSONObject cloudObject = parentObject.getJSONObject("clouds");

            //setting values from corresponding fields
            clouds_mAll.setText("All :"+cloudObject.getString("all"));

            //creating sysobject object to access data of weather JSon array
            JSONObject sysObject = parentObject.getJSONObject("sys");

            //setting values from corresponding fields
            sys_mType.setText("Type :"+sysObject.getString("type"));
            sys_mId.setText("Id :"+sysObject.getString("id"));
            sys_mMessage.setText("Message :"+sysObject.getString("message"));
            sys_mCountry.setText("Country :"+sysObject.getString("country"));
            sys_mSunrise.setText("Sunrise :"+sysObject.getString("sunrise"));
            sys_mSunset.setText("Sunset :"+sysObject.getString("sunset"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
