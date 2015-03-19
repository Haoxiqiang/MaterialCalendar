package info.hxq.materialcalendar;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * 日历显示activity
 */
public class CalendarActivity extends BaseActivity {

    private GestureDetector gestureDetector = null;
    private CalendarAdapter calV = null;
    private GridView gridView = null;

    private static int sensibility;

    /**
     * 每次滑动，增加或减去一个月,默认为0（即显示当前月）
     */
    private static int jumpMonth = 0;
    private int year_c = 0;
    private int month_c = 0;
    /**
     * 当前的年月，现在日历顶端
     */

    @InjectView(R.id.Gregorianum)
    TextView Gregorianum;
    @InjectView(R.id.Xia)
    TextView Xia;

    @InjectView(R.id.calendar_toolbar)
    Toolbar mToolBar;
    @InjectView(R.id.calendar_flipper)
    ViewFlipper mFlipper;
    @InjectView(R.id.titleDivider)
    View titleDivider;
    @InjectView(R.id.weekDivider)
    View weekDivider;

    public CalendarActivity() {
        Time time = new Time();
        time.setToNow();
        year_c = time.year;
        month_c = time.month + 1;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        ButterKnife.inject(this);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        sensibility = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, displayMetrics);

        setSupportActionBar(mToolBar);

        getSupportActionBar().setTitle("日历");

        mToolBar.setLogoDescription("日历");
        mToolBar.setTitleTextColor(Color.WHITE);
//        mToolBar.setNavigationIcon(R.drawable.zuojiantou);

        final GradientDrawable titleDividerBackground = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{0x40000000, 0x10000000,0x00000000});
        titleDividerBackground.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        titleDivider.setBackgroundDrawable(titleDividerBackground);
        weekDivider.setBackgroundDrawable(titleDividerBackground);


        gestureDetector = new GestureDetector(this, new MyGestureListener());
        mFlipper.removeAllViews();
        calV = new CalendarAdapter(this, jumpMonth, year_c, month_c);
        addGridView();
        gridView.setAdapter(calV);
        mFlipper.addView(gridView, 0);

        addTextToTopTextView();
    }

    private class MyGestureListener extends SimpleOnGestureListener {
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

    /**
     * 移动到下一个月
     */
    public void enterNextMonth(View v) {
        addGridView(); // 添加一个gridView
        jumpMonth++; // 下一个月
        calV = new CalendarAdapter(this, jumpMonth, year_c, month_c);
        gridView.setAdapter(calV);
        addTextToTopTextView(); // 移动到下一月后，将当月显示在头标题中


        mFlipper.addView(gridView, mFlipper.getChildCount());
        mFlipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in_left));
        mFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_out_left));
        mFlipper.showNext();
        mFlipper.removeViewAt(0);
    }

    /**
     * 移动到上一个月
     */
    public void enterPrevMonth(View v) {
        addGridView(); // 添加一个gridView
        jumpMonth--; // 上一个月
        calV = new CalendarAdapter(this, jumpMonth, year_c, month_c);
        gridView.setAdapter(calV);
        addTextToTopTextView(); // 移动到上一月后，将当月显示在头标题中

        mFlipper.addView(gridView, mFlipper.getChildCount());
        mFlipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in_right));
        mFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_out_right));
        mFlipper.showPrevious();
        mFlipper.removeViewAt(0);
    }

    /**
     * 添加头部的年份,夏历等信息
     */
    public void addTextToTopTextView() {
        StringBuilder textDate = new StringBuilder();
        Day showDay = calV.getShowDay();
        textDate.append(showDay.year).append("年").append(showDay.month).append("月").append("\t");
        Gregorianum.setText(textDate.toString());
        StringBuilder lunarDate = new StringBuilder();
        lunarDate.append(showDay.cyclical).append(showDay.animalsYear).append("年").append(showDay.xiaMonth).append(showDay.lunar);
        Xia.setText(lunarDate.toString());
    }

    private void addGridView() {
        gridView = new GridView(this);
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
                return CalendarActivity.this.gestureDetector.onTouchEvent(event);
            }
        });

        gridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                // 点击任何一个item，得到这个item的日期(排除点击的是周日到周六(点击不响应))
                int startPosition = calV.getStartPositon();
                int endPosition = calV.getEndPosition();
                if (startPosition <= position + 7 && position <= endPosition - 7) {
                    Day clickDay = calV.getItem(position);
                    ILog.i("clickDay: " + clickDay.toString());
//                    Toast.makeText(CalendarActivity.this, clickDay.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        gridView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    }

}