package info.hxq.materialcalendar.fragment;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.orhanobut.logger.Logger;
import com.skyicons.library.CloudHvRainView;
import com.skyicons.library.CloudRainView;
import com.skyicons.library.CloudSnowView;
import com.skyicons.library.CloudThunderView;
import com.skyicons.library.CloudView;
import com.skyicons.library.MoonView;
import com.skyicons.library.SunView;
import com.skyicons.library.WeatherTemplateView;
import com.skyicons.library.WindView;

import org.json.JSONObject;

import java.util.GregorianCalendar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import info.hxq.materialcalendar.R;
import info.hxq.materialcalendar.base.BaseFragment;
import info.hxq.materialcalendar.entity.Day;
import info.hxq.materialcalendar.entity.Taboo;
import info.hxq.materialcalendar.entity.Weather;
import info.hxq.materialcalendar.proxy.TabooProxy;
import info.hxq.materialcalendar.proxy.WeatherProxy;
import info.hxq.materialcalendar.tool.CalendarAdapter;
import me.drakeet.materialdialog.MaterialDialog;


/**
 * @author haoxiqiang
 */
public class CalendarFragment extends BaseFragment {

    private static int sensibility;
    /**
     * 每次滑动，增加或减去一个月,默认为0（即显示当前月）
     */
    private static int jumpMonth = 0;
    /**
     * 当前的年月，现在日历顶端
     */
    @InjectView(R.id.Gregorianum)
    TextView Gregorianum;
    @InjectView(R.id.Xia)
    TextView Xia;
    @InjectView(R.id.calendar_flipper)
    ViewFlipper mFlipper;

    @InjectView(R.id.titleCity)
    TextView titleCity;
    @InjectView(R.id.titleWeather)
    TextView titleWeather;
    @InjectView(R.id.skyicons)
    FrameLayout skyicons;

    @InjectView(R.id.day)
    TextView day;
    @InjectView(R.id.lunarDay)
    TextView lunarDay;
    @InjectView(R.id.holiday)
    TextView holiday;
    @InjectView(R.id.yi)
    TextView yi;
    @InjectView(R.id.ji)
    TextView ji;
    @InjectView(R.id.lunarContainer)
    View lunarContainer;

    private GestureDetector gestureDetector = null;
    private CalendarAdapter calV = null;
    private GridView gridView = null;
    private int year_c = 0;
    private int month_c = 0;


    public CalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View attachView = inflater.inflate(R.layout.fragment_calendar, container, false);
        ButterKnife.inject(this, attachView);
        return attachView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        sensibility = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, displayMetrics);

        Time time = new Time();
        time.setToNow();
        year_c = time.year;
        month_c = time.month + 1;

        gestureDetector = new GestureDetector(this.getActivity(), new MyGestureListener());
        mFlipper.removeAllViews();
        calV = new CalendarAdapter(this.getActivity(), jumpMonth, year_c, month_c);
        addGridView();
        gridView.setAdapter(calV);
        mFlipper.addView(gridView, 0);

        addTextToTopTextView();
        setWeatherValue();
        setTodayLunarInfo(calV.getSysDay());
    }

    private void setTodayLunarInfo(Day _day) {
        Taboo taboo = TabooProxy.getTabooByDate(_day.getDate());
        if (taboo != null) {
            lunarContainer.setVisibility(View.VISIBLE);
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.set(_day.year, _day.month - 1, _day.monthDay);
            day.setText(String.valueOf(_day.monthDay));
            lunarDay.setText(_day.lunar);
            yi.setText(taboo.yi);
            ji.setText(taboo.ji);
        } else {
            lunarContainer.setVisibility(View.GONE);
        }
        holiday.setVisibility(View.GONE);
    }

    private void setWeatherValue() {
        Weather weather = WeatherProxy.getTodayWeather();
        if (weather != null) {
            JSONObject weatherInfo = weather.weather_data;
            titleCity.setText(weather.currentCity + "   " + weatherInfo.optString("weather"));
            titleWeather.setText(weatherInfo.optString("wind") + "   " + weatherInfo.optString("temperature"));

            skyicons.removeAllViews();

            String viewParam = weatherInfo.optString("weather");
            WeatherTemplateView weatherTemplateView = null;
            Time time = new Time();
            time.setToNow();

            Context context = this.getActivity();

            if ("晴".equals(viewParam)) {
                if (time.hour > 19 || time.hour < 5) {
                    weatherTemplateView = new MoonView(context);
                } else {
                    weatherTemplateView = new SunView(context);
                }
            } else if ("多云".equals(viewParam)) {
                weatherTemplateView = new CloudView(context);
            } else if ("雷阵雨".equals(viewParam) || "雷阵雨伴有冰雹".equals(viewParam)) {
                weatherTemplateView = new CloudThunderView(context);
            } else if ("小雨".equals(viewParam) || "中雨".equals(viewParam) || "小雨转中雨".equals(viewParam)) {
                weatherTemplateView = new CloudRainView(context);
            } else if (viewParam != null && viewParam.contains("雪")) {
                weatherTemplateView = new CloudSnowView(context);
            } else if (viewParam != null && (viewParam.contains("大雨") || viewParam.contains("暴雨"))) {
                weatherTemplateView = new CloudHvRainView(context);
            } else {
                weatherTemplateView = new WindView(context);
            }

            if (weatherTemplateView != null) {
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT);
                skyicons.addView(weatherTemplateView, layoutParams);
            }

        } else {
            Object object = titleWeather.getTag();
            if (object == null) {
                titleWeather.setTag(1);
                object = 1;
            } else {
                object = ((Integer) object) + 1;
                titleWeather.setTag(object);
            }
            if ((Integer) object < 5) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setWeatherValue();
                    }
                }, 5000);
            }
        }
    }

    /**
     * 移动到下一个月
     */
    public void enterNextMonth(View v) {
        addGridView(); // 添加一个gridView
        jumpMonth++; // 下一个月
        calV = new CalendarAdapter(this.getActivity(), jumpMonth, year_c, month_c);
        gridView.setAdapter(calV);
        addTextToTopTextView(); // 移动到下一月后，将当月显示在头标题中
        mFlipper.addView(gridView, mFlipper.getChildCount());
        mFlipper.setInAnimation(AnimationUtils.loadAnimation(this.getActivity(), R.anim.slide_in_left));
        mFlipper.setOutAnimation(AnimationUtils.loadAnimation(this.getActivity(), R.anim.slide_out_left));
        mFlipper.showNext();
        mFlipper.removeViewAt(0);
    }

    /**
     * 移动到上一个月
     */
    public void enterPrevMonth(View v) {
        addGridView(); // 添加一个gridView
        jumpMonth--; // 上一个月
        calV = new CalendarAdapter(this.getActivity(), jumpMonth, year_c, month_c);
        gridView.setAdapter(calV);
        addTextToTopTextView(); // 移动到上一月后，将当月显示在头标题中
        mFlipper.setInAnimation(AnimationUtils.loadAnimation(this.getActivity(), R.anim.slide_in_right));
        mFlipper.setOutAnimation(AnimationUtils.loadAnimation(this.getActivity(), R.anim.slide_out_right));
        mFlipper.addView(gridView, mFlipper.getChildCount());
        mFlipper.showPrevious();
        mFlipper.removeViewAt(0);
    }

    /**
     * 添加头部的年份,夏历等信息
     */
    public void addTextToTopTextView() {
        StringBuilder textDate = new StringBuilder();
        Day showDay = calV.getShowDay();
        textDate.append(showDay.year).append("年").append(showDay.month).append("月").append(showDay.monthDay);
        Gregorianum.setText(textDate.toString());


        StringBuilder lunarDate = new StringBuilder();
        lunarDate.append(showDay.cyclical).append(showDay.animalsYear).append("年").append(showDay.xiaMonth).append(showDay.lunar);
        Xia.setText(lunarDate.toString());
//        Taboo taboo = TabooProxy.getTodayTaboo();
    }

    private void addGridView() {
        gridView = new GridView(this.getActivity());
        gridView.setNumColumns(7);
        gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        gridView.setGravity(Gravity.CENTER_VERTICAL);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gridView.setHorizontalScrollBarEnabled(false);
        gridView.setVerticalScrollBarEnabled(false);
//        gridView.setVerticalSpacing(1);
//        gridView.setHorizontalSpacing(1);
        gridView.setOnTouchListener(new View.OnTouchListener() {
            // 将gridview中的触摸事件回传给gestureDetector

            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                // 点击任何一个item，得到这个item的日期(排除点击的是周日到周六(点击不响应))
                int startPosition = calV.getStartPositon();
                int endPosition = calV.getEndPosition();
                if (startPosition <= position + 7 && position <= endPosition - 7) {
                    Day clickDay = calV.getItem(position);
                    Logger.d(clickDay.toString(), 2);
                    setTodayLunarInfo(clickDay);
//                    Toast.makeText(CalendarActivity.this, clickDay.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        gridView.setLayoutParams(new LinearLayout.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (e1.getX() - e2.getX() > sensibility) {
                // 像左滑动
                enterNextMonth(null);
                return true;
            } else if (e1.getX() - e2.getX() < -sensibility) {
                // 向右滑动
                enterPrevMonth(null);
                return true;
            }
            return false;
        }
    }

    @OnClick(R.id.skyicons)
    public void showSkyIcons(View v) {

        View view = LayoutInflater.from(this.getActivity()).inflate(R.layout.dialog_show_weather_view, null);

        final MaterialDialog mMaterialDialog = new MaterialDialog(this.getActivity());
        mMaterialDialog.setContentView(view)
                .setBackground(new ColorDrawable(getResources().getColor(R.color.primary)))
               .setCanceledOnTouchOutside(true);
        mMaterialDialog.show();
    }
}
