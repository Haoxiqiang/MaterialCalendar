package info.hxq.materialcalendar.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hxq on 15/3/20.
 */
public class Memo implements Parcelable {

    public static final Creator<Memo> CREATOR
            = new Creator<Memo>() {
        public Memo createFromParcel(Parcel source) {
            Memo memo = new Memo();
            try {
                memo.readFromParcel(source);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return memo;
        }

        public Memo[] newArray(int size) {
            return new Memo[size];
        }
    };
    public String date;
    public String type;
    public String title;
    public String timestamp;
    public JSONObject alarm = new JSONObject();
    public String remarks;

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(type);
        dest.writeString(title);
        dest.writeString(timestamp);
        dest.writeString(alarm.toString());
        dest.writeString(remarks);
    }

    private void readFromParcel(Parcel source) throws JSONException {
        date = source.readString();
        type = source.readString();
        title = source.readString();
        timestamp = source.readString();
        alarm = new JSONObject(source.readString());
        remarks = source.readString();
    }
}
