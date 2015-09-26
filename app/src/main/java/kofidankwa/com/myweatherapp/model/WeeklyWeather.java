package kofidankwa.com.myweatherapp.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import kofidankwa.com.myweatherapp.R;

/**
 * Created by kofidankwa on 19/09/15.
 */

//properties for the daily weather reports

public class WeeklyWeather {
    private  String mIcon;
    private  long mTime;
    private  double mTemperatureMin;
    private  double mTemperatureMax;
    private  double mHumidity;
    private  double mPrecipChance;
    private  String mSummary;
    private  String mMainSummary;
    private  String mMainIcon;

    public  int getMainIconId(){

        int iconId = R.drawable.clear_day;

        if (mMainIcon.equals("clear-day")) {
            iconId = R.drawable.clear_day;
        }
        else if (mMainIcon.equals("clear-night")) {
            iconId = R.drawable.clear_night;
        }
        else if (mMainIcon.equals("rain")) {
            iconId = R.drawable.rain;
        }
        else if (mMainIcon.equals("snow")) {
            iconId = R.drawable.snow;
        }
        else if (mMainIcon.equals("sleet")) {
            iconId = R.drawable.sleet;
        }
        else if (mMainIcon.equals("wind")) {
            iconId = R.drawable.wind;
        }
        else if (mMainIcon.equals("fog")) {
            iconId = R.drawable.fog;
        }
        else if (mMainIcon.equals("cloudy")) {
            iconId = R.drawable.cloudy;
        }
        else if (mMainIcon.equals("partly-cloudy-day")) {
            iconId = R.drawable.partly_cloudy;
        }
        else if (mMainIcon.equals("partly-cloudy-night")) {
            iconId = R.drawable.cloudy_night;
        }
        return iconId;
    }

    public  int getIconId(){

        int iconId = R.drawable.clear_day;

        if (mIcon.equals("clear-day")) {
            iconId = R.drawable.clear_day;
        }
        else if (mIcon.equals("clear-night")) {
            iconId = R.drawable.clear_night;
        }
        else if (mIcon.equals("rain")) {
            iconId = R.drawable.rain;
        }
        else if (mIcon.equals("snow")) {
            iconId = R.drawable.snow;
        }
        else if (mIcon.equals("sleet")) {
            iconId = R.drawable.sleet;
        }
        else if (mIcon.equals("wind")) {
            iconId = R.drawable.wind;
        }
        else if (mIcon.equals("fog")) {
            iconId = R.drawable.fog;
        }
        else if (mIcon.equals("cloudy")) {
            iconId = R.drawable.cloudy;
        }
        else if (mIcon.equals("partly-cloudy-day")) {
            iconId = R.drawable.partly_cloudy;
        }
        else if (mIcon.equals("partly-cloudy-night")) {
            iconId = R.drawable.cloudy_night;
        }
        return iconId;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public int getTemperatureMin() {
        return (int)Math.round((mTemperatureMin - 32)/1.8);
    }

    public void setTemperatureMin(double temperatureMin) {
        mTemperatureMin = temperatureMin;
    }

    public double getHumidity() {
        return mHumidity;
    }

    public void setHumidity(double humidity) {
        mHumidity = humidity;
    }

    public String getMainSummary() {
        return mMainSummary;
    }

    public void setMainSummary(String mainSummary) {
        mMainSummary = mainSummary;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public int getTemperatureMax() {
        return (int)Math.round((mTemperatureMax - 32)/1.8);
    }

    public void setTemperatureMax(double temperatureMax) {
        mTemperatureMax = temperatureMax;
    }

    public double getPrecipChance() {
        double precipPercentage = mPrecipChance * 100;
        return (int)Math.round(precipPercentage);
    }

    public void setPrecipChance(double precipChance) {
        mPrecipChance = precipChance;
    }

    public String getMainIcon() {
        return mMainIcon;
    }

    public void setMainIcon(String mainIcon) {
        mMainIcon = mainIcon;
    }



  /*  public String getFormattedTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
        formatter.setTime(TimeZone.getTimeZone(getTime()));
        Date dateTime = new Date(getTime() * 1000);
        String timeString = formatter.format(dateTime);

        return timeString;
    }
*/


}
