package info.hxq.materialcalendar.web;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import info.hxq.materialcalendar.base.MainApplication;

/**
 * Created by hxq on 15/3/22.
 */
public final class RQManager {

    private static RQManager mInstance;
    private RequestQueue mRequestQueue;

    private RQManager() {
        mRequestQueue = getRequestQueue();
    }

    public static synchronized RQManager getInstance() {
        if (mInstance == null) {
            mInstance = new RQManager();
        }
        return mInstance;
    }

    public static void stop() {
        getInstance().getRequestQueue().stop();
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(MainApplication.getApplication());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}
