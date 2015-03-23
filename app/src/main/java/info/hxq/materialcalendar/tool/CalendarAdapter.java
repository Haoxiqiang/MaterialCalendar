package info.hxq.materialcalendar.tool;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringSystem;
import com.facebook.rebound.SpringUtil;

import java.util.Locale;
import java.util.TimeZone;

import butterknife.ButterKnife;
import butterknife.InjectView;
import info.hxq.materialcalendar.R;
import info.hxq.materialcalendar.entity.Day;

public class CalendarAdapter extends BaseAdapter {

    private final int normalDayColor = Color.parseColor("#FF222222");
    private final int otherDayColor = Color.parseColor("#FFBBBBBB");
    private Context context;
    private int daysOfMonth = 0; // 某月的天数
    private int dayOfWeek = 0; // 具体某一天是星期几
    private int lastDaysOfMonth = 0; // 上一个月的总天数
    private Day[] dayNumber = new Day[35]; // 一个gridview中的日期存入此数组中
    private LunarCalendar lc = null;
    private Day[] schDateTagFlag = null; // 存储当月所有的日程日期
    // 系统当前时间
    private Day sysDay;
    private Day showDay;
    private Drawable normalSignedDrawable;
    private Drawable otherSignedDrawable;

    private CalendarExtend mCalendarExtend = CalendarExtend.getInstance(TimeZone.getTimeZone("GMT+5"), Locale.CHINA);

    public CalendarAdapter(Context _context) {
        this.context = _context;

        lc = new LunarCalendar();

        Time time = new Time();
        time.setToNow();
        sysDay = new Day();
        sysDay.year = time.year;
        sysDay.month = time.month + 1;
        sysDay.monthDay = time.monthDay;


        final float radius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, context.getResources().getDisplayMetrics());

        normalSignedDrawable = new ShapeDrawable(new Shape() {
            @Override
            public void draw(Canvas canvas, Paint paint) {
                paint.setColor(Color.parseColor("#FFFFBF00"));
                paint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, paint);
            }
        });
        otherSignedDrawable = new ShapeDrawable(new Shape() {
            @Override
            public void draw(Canvas canvas, Paint paint) {
                paint.setColor(Color.parseColor("#77FFBF00"));
                paint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, paint);
            }
        });
    }

    public CalendarAdapter(Context _context, int jumpMonth, int year_c, int month_c) {
        this(_context);
        int stepYear;
        int stepMonth = month_c + jumpMonth;
        if (stepMonth > 0) {
            // 往下一个月滑动
            if (stepMonth % 12 == 0) {
                stepYear = year_c + stepMonth / 12 - 1;
                stepMonth = 12;
            } else {
                stepYear = year_c + stepMonth / 12;
                stepMonth = stepMonth % 12;
            }
        } else if (stepMonth < 0) {
            // 往上一个月滑动
            stepYear = year_c - 1 + stepMonth / 12;
            stepMonth = stepMonth % 12 + 12;
            if (stepMonth % 12 == 0) {

            }
        } else {
            stepYear = year_c;
        }

        if (showDay == null) {
            showDay = new Day();
        }
        showDay.year = stepYear;
        showDay.month = stepMonth;
        showDay.monthDay = sysDay.monthDay;

        formatDay(showDay);

        getCalendar(stepYear, stepMonth);
    }

    private void formatDay(Day day) {
        day.animalsYear = lc.animalsYear(day.year);
        int leapMonthStatus = lc.leapMonth(day.year);
        day.leapMonth = (((leapMonthStatus == 0) || (leapMonthStatus == -1)) ? "" : lc.getChineseMonth(leapMonthStatus));
        day.cyclical = lc.cyclical(day.year);
        day.lunar = lc.getLunarDate(day.year, day.month, 1, true);
        day.xiaMonth = lc.getLunarMonth();
    }

    @Override
    public int getCount() {
        return dayNumber.length;
    }

    @Override
    public Day getItem(int position) {
        return dayNumber[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {

            convertView =
                    LayoutInflater.from(context).inflate(R.layout.calendar_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        Day itemDay = dayNumber[position];
        if (position < daysOfMonth + dayOfWeek && position >= dayOfWeek) {
            //周日或者周六
            if (position % 7 == 0 || position % 7 == 6) {

            }
            //今天
            holder.dayTV.setTextColor(sysDay.equals(itemDay) ? Color.RED : normalDayColor);
            holder.lunarTV.setTextColor(sysDay.equals(itemDay) ? Color.RED : normalDayColor);
        } else {
            holder.dayTV.setTextColor(otherDayColor);
            holder.lunarTV.setTextColor(otherDayColor);
        }
        holder.dayTV.setText(String.valueOf(itemDay.monthDay));
        holder.lunarTV.setText(String.valueOf(itemDay.lunar));
        holder.lunarTV.setMarqueeRepeatLimit(-1);
        holder.lunarTV.setSelected(true);
        holder.lunarTV.setSingleLine(true);
        holder.lunarTV.setEllipsize(TextUtils.TruncateAt.MARQUEE);

        // Add a spring to the system.
        final View view = convertView;
        SpringSystem mSpringSystem = SpringSystem.create();
        final Spring spring = mSpringSystem.createSpring();
        spring.addListener(new SimpleSpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                float mappedValue = (float) SpringUtil.mapValueFromRangeToRange(spring.getCurrentValue(), 0, 1, 1, 0.5);
                ViewCompat.setScaleX(view, mappedValue);
                ViewCompat.setScaleY(view, mappedValue);
            }
        });

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // When pressed start solving the spring to 1.
                        spring.setEndValue(1);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        // When released start solving the spring to 0.
                        spring.setEndValue(0);
                        break;
                }
                return false;
            }
        });

        return convertView;
    }

    /**
     * 当前月份返回
     *
     * @return
     */
    public Day getShowDay() {
        return showDay;
    }

    // 得到某年的某月的天数且这月的第一天是星期几
    public void getCalendar(int year, int month) {
        daysOfMonth = mCalendarExtend.getDaysOfMonth(year, month); // 某月的总天数
        dayOfWeek = mCalendarExtend.getWeekdayOfMonthFirstDay(year, month); // 某月第一天为星期几
        lastDaysOfMonth = mCalendarExtend.getDaysOfMonth(year, month - 1); // 上一个月的总天数
//        ILog.d("DAY:  isLeapyear:" + isLeapyear + "   daysOfMonth:" + daysOfMonth + "  dayOfWeek:" + dayOfWeek + "  lastDaysOfMonth:" + lastDaysOfMonth);
        getWeek(year, month);
    }

    // 将一个月中的每一天的值添加入数组dayNuber中
    private void getWeek(int year, int month) {
        int j = 1;
        // 得到当前月的所有日程日期(这些日期需要标记)
        for (int i = 0; i < dayNumber.length; i++) {
            Day day = new Day();
            day.year = year;
            if (i < dayOfWeek) { // 前一个月
                int temp = lastDaysOfMonth - dayOfWeek + 1;
                day.lunar = lc.getLunarDate(year, month - 1, temp + i, false);
                day.monthDay = temp + i;
                if (month == 0) {
                    day.year = year - 1;
                    day.month = 11;
                } else {
                    day.month = month - 1;
                }
            } else if (i < daysOfMonth + dayOfWeek) { // 本月
                day.lunar = lc.getLunarDate(year, month, i - dayOfWeek + 1, false);
                day.monthDay = i - dayOfWeek + 1;
                day.month = month;
            } else { // 下一个月
                day.lunar = lc.getLunarDate(year, month + 1, j, false);
                day.monthDay = j;
                if (month == 11) {
                    day.year = year + 1;
                    day.month = 0;
                } else {
                    day.month = month + 1;
                }
                j++;
            }
            dayNumber[i] = day;
        }
    }

    public boolean matchScheduleDate(Day _day) {
        if (_day == null) {
            return false;
        }
        int queryValue = _day.year * 10000 + _day.month * 100 + _day.monthDay;
        return true;
    }

    public void setScheduleDate(Day[] scheduleDates) {
        this.schDateTagFlag = scheduleDates;
    }

    /**
     * 在点击gridView时，得到这个月中第一天的位置
     *
     * @return
     */
    public int getStartPositon() {
        return dayOfWeek + 7;
    }

    /**
     * 在点击gridView时，得到这个月中最后一天的位置
     *
     * @return
     */
    public int getEndPosition() {
        return (dayOfWeek + daysOfMonth + 7) - 1;
    }

    static class ViewHolder {
        @InjectView(R.id.daydesc)
        TextView dayTV;
        @InjectView(R.id.lunardesc)
        TextView lunarTV;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
