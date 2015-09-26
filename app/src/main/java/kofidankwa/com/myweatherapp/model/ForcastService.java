package kofidankwa.com.myweatherapp.model;



/**
 * Created by kofidankwa on 16/09/15.
 */
public class ForcastService {

    private double mLongitude;
    private double mLatitude;
    private String mApiKey = "insert your api key here";

  // I'll use data from the geolocation api to setup the forcast api, then I'll run the forcast api on the main activity.



    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    public String getApiKey() {
        return mApiKey;
    }

    public void setApiKey(String apiKey) {
        mApiKey = apiKey;
    }

    public String getApiRequestKey(double lat, double lng ){
        return "https://api.forecast.io/forecast/" + mApiKey + "/" + lat + ", " + lng;
    }



}
