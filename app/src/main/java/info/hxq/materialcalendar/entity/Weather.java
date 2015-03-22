package info.hxq.materialcalendar.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hxq on 15/3/20.
 */
public class Weather implements Parcelable {

    public static final Creator<Weather> CREATOR
            = new Creator<Weather>() {
        public Weather createFromParcel(Parcel source) {
            Weather weather = new Weather();
            try {
                weather.readFromParcel(source);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return weather;
        }

        public Weather[] newArray(int size) {
            return new Weather[size];
        }
    };
    public String date;
    public String currentCity;
    public String pm25;
    public JSONArray index;
    public JSONObject weather_data;

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(currentCity);
        dest.writeString(pm25);
        dest.writeString(index.toString());
        dest.writeString(weather_data.toString());
    }

    private void readFromParcel(Parcel source) throws JSONException {
        date = source.readString();
        currentCity = source.readString();
        pm25 = source.readString();
        index = new JSONArray(source.readString());
        weather_data = new JSONObject(source.readString());
    }
}
