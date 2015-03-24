package info.hxq.materialcalendar.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by haoxiqiang on 15/3/10.
 */
public class Day implements Comparable, Parcelable, Cloneable {

    public static final Creator<Day> CREATOR
            = new Creator<Day>() {
        public Day createFromParcel(Parcel source) {
            Day day = new Day();
            day.readFromParcel(source);
            return day;
        }

        public Day[] newArray(int size) {
            return new Day[size];
        }
    };
    public int monthDay = -1;
    public int month = -1;
    public int year = -1;
    /**
     * compareTo doesn't contain lunar
     */
    public String lunar;
    public String animalsYear;
    public String xiaMonth;
    /**
     * 闰哪一个月
     */
    public String leapMonth;
    /**
     * 天干地支
     */
    public String cyclical;

    @Override
    protected Day clone() {
        try {
            return (Day) super.clone();
        } catch (Exception e) {
            e.printStackTrace();
            Day day = new Day();
            day.year = this.year;
            day.month = this.month;
            day.monthDay = this.monthDay;
            day.lunar = this.lunar;
            day.animalsYear = this.animalsYear;
            day.xiaMonth = this.xiaMonth;
            day.leapMonth = this.leapMonth;
            day.cyclical = this.cyclical;
            return day;
        }
    }

    @Override
    public int hashCode() {
        int result = monthDay;
        result = 31 * result + month;
        result = 31 * result + year;
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Day) {
            if (this.hashCode() == o.hashCode()
                    && this.year == ((Day) o).year
                    && this.month == ((Day) o).month
                    && this.monthDay == ((Day) o).monthDay) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return (year != -1 ? "year =" + year : "")
                + (month != -1 ? " month =" + month : "")
                + "  monthDay:" + monthDay
                + "   lunar:" + lunar
                + "   animalsYear:" + animalsYear
                + "   xiaMonth:" + xiaMonth
                + "   leapMonth:" + leapMonth
                + "   cyclical:" + cyclical;
    }

    public String getDate() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(year).append(month < 10 ? "0" + month : month).append(monthDay);
        return stringBuilder.toString();
    }

    @Override
    public int compareTo(@NonNull Object another) {
        //19700101
        if (another instanceof Day) {
            return (year - ((Day) another).year) * 10000 + (month - ((Day) another).month) * 100 + (monthDay - ((Day) another).monthDay);
        }
        return -1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(year);
        dest.writeInt(month);
        dest.writeInt(monthDay);
        dest.writeString(lunar);
        dest.writeString(animalsYear);
        dest.writeString(xiaMonth);
        dest.writeString(leapMonth);
        dest.writeString(cyclical);
    }

    private void readFromParcel(Parcel source) {
        year = source.readInt();
        month = source.readInt();
        monthDay = source.readInt();
        lunar = source.readString();
        animalsYear = source.readString();
        xiaMonth = source.readString();
        leapMonth = source.readString();
        cyclical = source.readString();
    }
}
