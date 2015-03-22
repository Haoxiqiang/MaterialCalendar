package info.hxq.materialcalendar.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hxq on 15/3/20.
 */
public class Taboo implements Parcelable {

    public static final Creator<Taboo> CREATOR
            = new Creator<Taboo>() {
        public Taboo createFromParcel(Parcel source) {
            Taboo taboo = new Taboo();
            taboo.readFromParcel(source);
            return taboo;
        }

        public Taboo[] newArray(int size) {
            return new Taboo[size];
        }
    };
    public String date;
    public String nongli;
    public String ganzhi;
    public String yi;
    public String ji;
    public String jishenyiqu;
    public String xiongshenyiji;
    public String taishenzhanfang;
    public String wuxing;
    public String chong;
    public String pengzubaiji;

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(nongli);
        dest.writeString(ganzhi);
        dest.writeString(yi);
        dest.writeString(ji);
        dest.writeString(jishenyiqu);
        dest.writeString(xiongshenyiji);
        dest.writeString(taishenzhanfang);
        dest.writeString(wuxing);
        dest.writeString(chong);
        dest.writeString(pengzubaiji);
    }

    private void readFromParcel(Parcel source) {
        date = source.readString();
        nongli = source.readString();
        ganzhi = source.readString();
        yi = source.readString();
        ji = source.readString();
        jishenyiqu = source.readString();
        xiongshenyiji = source.readString();
        taishenzhanfang = source.readString();
        wuxing = source.readString();
        chong = source.readString();
        pengzubaiji = source.readString();
    }
}
