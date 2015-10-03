package kofidankwa.com.myweatherapp.ui;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;


import butterknife.Bind;
import butterknife.ButterKnife;
import kofidankwa.com.myweatherapp.R;
import kofidankwa.com.myweatherapp.model.CurrentWeather;
import kofidankwa.com.myweatherapp.model.ForcastService;
import kofidankwa.com.myweatherapp.model.HourlyWeather;
import kofidankwa.com.myweatherapp.model.WeeklyWeather;


public class MainActivity extends Activity implements ConnectionCallbacks,
        OnConnectionFailedListener{


    //Log TAG
    public final static String TAG = MainActivity.class.getSimpleName();

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    public Location mLocation;

    public ForcastService mForcastService = new ForcastService();

    // google client that will interact with google api
    private GoogleApiClient mGoogleApiClient;

    public CurrentWeather mCurrentWeather;

    public HourlyWeather[] mHourlyWeather;

    public WeeklyWeather[] mWeeklyWeather;


    @Bind(R.id.timeLabel) TextView mTimeLabel;
    @Bind(R.id.temperatureLabel) TextView mTemperatureLabel;
    @Bind(R.id.humidityValue) TextView mHumidityValue;
    @Bind(R.id.precipValue) TextView mPrecipValue;
    @Bind(R.id.summaryLabel) TextView mSummaryLabel;
    @Bind(R.id.iconImageView) ImageView mIconImageView;
    @Bind(R.id.refreshImageView) ImageView mRefreshImageView;
    @Bind(R.id.nextScreenButton) Button mNextButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        if(checkPlayServices()) {
            buildGoogleClient();
        }

        mRefreshImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
                isReady();

            }
        });


    }

    /*private int BackgroundSetter(){
        return 0;
    }*/
/** called when the user clicks the send button **/
    public void sendMessage(View view){
// do something in response to button
        Intent intent = new Intent(this, HourlyWeatherReport.class);
        intent.putExtra("data", dataBundle());
    }

    public void updateDisplay(){
        mTemperatureLabel.setText((mCurrentWeather.getTemperature() + ""));
        mTimeLabel.setText("At " + mCurrentWeather.getFormattedTime() + " it will be");
        mHumidityValue.setText(mCurrentWeather.getHumidity() + "");
        mPrecipValue.setText(mCurrentWeather.getPrecipChance() + "%");
        mSummaryLabel.setText(mCurrentWeather.getSummary());
        Drawable drawable = ContextCompat.getDrawable(this, mCurrentWeather.getIconId());
        mIconImageView.setImageDrawable(drawable);
    }

    public HashMap<String, Object> dataBundle() {
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("Hour", mHourlyWeather);
        data.put("Week", mWeeklyWeather);
        return data;
    }

    private CurrentWeather getCurrentDetails(String jsonData) throws JSONException {
        JSONObject forcast = new JSONObject(jsonData);
        String timezone = forcast.getString("timezone");
        JSONObject currently = forcast.getJSONObject("currently");
        Log.i(TAG, "FROM JSON: " + timezone);
        Log.i(TAG, currently.toString());
        CurrentWeather currentWeather = new CurrentWeather();
        currentWeather.setSummary(currently.getString("summary"));
        currentWeather.setPrecipChance(currently.getDouble("precipProbability"));
        currentWeather.setHumidity(currently.getDouble("humidity"));
        currentWeather.setTemperature(currently.getDouble("temperature"));
        currentWeather.setTime(currently.getLong("time"));
        currentWeather.setIcon(currently.getString("icon"));
        currentWeather.setTimeZone(timezone);

        Log.d(TAG, currentWeather.getFormattedTime());
        return currentWeather;
    }

    private HourlyWeather[] getHourlyWeatherDetails(String jsonData) throws JSONException {
        JSONObject forcast = new JSONObject(jsonData);
        JSONObject hourly = forcast.getJSONObject("hourly");
        Log.i(TAG, hourly.toString());
        JSONArray data = hourly.getJSONArray("data");
        HourlyWeather[] hourlyWeathers = new HourlyWeather[data.length()];
        for(int i=0; i<data.length(); i++) {
           JSONObject jsonObject = data.getJSONObject(i);
            HourlyWeather hourlyWeather = new HourlyWeather();
            hourlyWeather.setSummary(jsonObject.getString("summary"));
            hourlyWeather.setPrecipChance(jsonObject.getDouble("precipProbability"));
            hourlyWeather.setHumidity(jsonObject.getDouble("humidity"));
            hourlyWeather.setTemperature(jsonObject.getDouble("temperature"));
            hourlyWeather.setTime(jsonObject.getLong("time"));
            hourlyWeather.setIcon(jsonObject.getString("icon"));
            hourlyWeathers[i] = hourlyWeather;
        }
        return hourlyWeathers;
    }

    private WeeklyWeather[] getWeeklyWeatherDetails(String jsonData) throws JSONException {
        JSONObject forcast = new JSONObject(jsonData);
        JSONObject weekly = forcast.getJSONObject("daily");
        Log.i(TAG,weekly.toString());
        JSONArray data = weekly.getJSONArray("data");
        WeeklyWeather[] weeklyWeathers = new WeeklyWeather[data.length()];
        for(int i=0; i<data.length(); i++){
           JSONObject jsonObject = data.getJSONObject(i);
            WeeklyWeather weeklyWeather = new WeeklyWeather();
            weeklyWeather.setSummary(jsonObject.getString("summary"));
            weeklyWeather.setPrecipChance(jsonObject.getDouble("precipProbability"));
            weeklyWeather.setHumidity(jsonObject.getDouble("humidity"));
            weeklyWeather.setTemperatureMin(jsonObject.getDouble("temperatureMin"));
            weeklyWeather.setTemperatureMax(jsonObject.getDouble("temperatureMax"));
            weeklyWeather.setTime(jsonObject.getLong("time"));
            weeklyWeather.setIcon(jsonObject.getString("icon"));
            weeklyWeathers[i] = weeklyWeather;
        }
        return  weeklyWeathers;
    }



    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null) {
            isAvailable = true;
        }
        return isAvailable;

    }




    public void isReady() {

        if (isNetworkAvailable() && mGoogleApiClient.isConnected()) {
            OkHttpClient client = new OkHttpClient();
            mForcastService.setApiKey(getResources().getString(R.string.forcast_api_key));
            Request request = new Request.Builder()
                    .url(mForcastService.getApiRequestKey(mForcastService.getLatitude(), mForcastService.getLongitude()))
                    .build();


            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {

                }

                @Override
                public void onResponse(Response response) throws IOException {


                    try {

                        String jsonData = response.body().string();
                        mCurrentWeather = getCurrentDetails(jsonData);
                        mWeeklyWeather = getWeeklyWeatherDetails(jsonData);
                        mHourlyWeather = getHourlyWeatherDetails(jsonData);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateDisplay();
                            }
                        });


                        if (response.isSuccessful()) {
                            Log.v(TAG, jsonData);
                        }
                    } catch (IOException | JSONException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    }
                }
            });


        }
    }






    /**
         * Creating google api client object
         * */



    @Override
    protected void onStop() {
        super.onStop();
        if(mGoogleApiClient.isConnected()){
            mGoogleApiClient.disconnect();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        if(mGoogleApiClient != null){
            mGoogleApiClient.connect();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        checkPlayServices();

        mGoogleApiClient.connect();

    }



    /**
     * Google api callback methods
     **/

    @Override
    public void onConnected(Bundle bundle) {


    }

    @Override
    public void onConnectionSuspended(int i) {
     mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());
    }


    /**
     * Method to extract long and lat from data retrieved from google api
     **/

    private void getLocation(){
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if(mLocation != null) {
            mForcastService.setLatitude(mLocation.getLatitude());
            mForcastService.setLongitude(mLocation.getLongitude());
        }
    }





    protected synchronized void buildGoogleClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    /**
     * Method to verify if google play services are available on the device
     **/

    private boolean checkPlayServices(){
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if(resultCode != ConnectionResult.SUCCESS){
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(), "This device is not supported.", Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }




}
