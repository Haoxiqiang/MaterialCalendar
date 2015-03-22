package info.hxq.materialcalendar;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;


/**
 * Created by haoxiqiang on 15/3/19.
 */
public class BaseActivity extends ActionBarActivity {

    protected static final Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
