package info.hxq.materialcalendar;


import android.annotation.SuppressLint;
import android.util.Log;

public final class ILog {

    private static final String ILOGTHROWABLE = "IlogThrowable";
    private static final String DEFALUTTIPS = "execute!";
    private static final boolean NEEDLOGS = BuildConfig.DEBUG;
    private static final int FAILURECODE = -1;

    enum LogType {
        V, D, I, W, E, A
    }

    public static boolean isNeedLogs() {
        return NEEDLOGS;
    }

    private ILog() {

    }

    // repeat these type is wtf
    // verbose
    public static int v() {
        return print_log(LogType.V, null, DEFALUTTIPS, new Throwable(ILOGTHROWABLE));
    }

    public static int v(String msg) {
        return print_log(LogType.V, null, msg, new Throwable(ILOGTHROWABLE));
    }

    public static int v(Throwable tr) {
        return print_log(LogType.V, null, null, tr);
    }

    public static int v(String msg, Throwable tr) {
        return print_log(LogType.V, null, msg, tr);
    }

    public static int v(String tag, String msg) {
        return print_log(LogType.V, tag, msg, new Throwable(ILOGTHROWABLE));
    }

    public static int v(String tag, String msg, Throwable tr) {
        return print_log(LogType.V, tag, msg, tr);
    }

    // debug
    public static int d() {
        return print_log(LogType.D, null, DEFALUTTIPS, new Throwable(ILOGTHROWABLE));
    }

    public static int d(String msg) {
        return print_log(LogType.D, null, msg, new Throwable(ILOGTHROWABLE));
    }

    public static int d(Throwable tr) {
        return print_log(LogType.D, null, null, tr);
    }

    public static int d(String msg, Throwable tr) {
        return print_log(LogType.D, null, msg, tr);
    }

    public static int d(String tag, String msg) {
        return print_log(LogType.D, tag, msg, new Throwable(ILOGTHROWABLE));
    }

    public static int d(String tag, String msg, Throwable tr) {
        return print_log(LogType.D, tag, msg, tr);
    }

    // info
    public static int i() {
        return print_log(LogType.I, null, DEFALUTTIPS, new Throwable(ILOGTHROWABLE));
    }

    public static int i(String msg) {
        return print_log(LogType.I, null, msg, new Throwable(ILOGTHROWABLE));
    }

    public static int i(Throwable tr) {
        return print_log(LogType.I, null, null, tr);
    }

    public static int i(String msg, Throwable tr) {
        return print_log(LogType.I, null, msg, tr);
    }

    public static int i(String tag, String msg) {
        return print_log(LogType.I, tag, msg, new Throwable(ILOGTHROWABLE));
    }

    public static int i(String tag, String msg, Throwable tr) {
        return print_log(LogType.I, tag, msg, tr);
    }

    // warn
    public static int w() {
        return print_log(LogType.W, null, DEFALUTTIPS, new Throwable(ILOGTHROWABLE));
    }

    public static int w(String msg) {
        return print_log(LogType.W, null, msg, new Throwable(ILOGTHROWABLE));
    }

    public static int w(Throwable tr) {
        return print_log(LogType.W, null, null, tr);
    }

    public static int w(String msg, Throwable tr) {
        return print_log(LogType.W, null, msg, tr);
    }

    public static int w(String tag, String msg) {
        return print_log(LogType.W, tag, msg, new Throwable(ILOGTHROWABLE));
    }

    public static int w(String tag, String msg, Throwable tr) {
        return print_log(LogType.W, tag, msg, tr);
    }

    // error

    public static int e() {
        return print_log(LogType.E, null, DEFALUTTIPS, new Throwable(ILOGTHROWABLE));
    }

    public static int e(String msg) {
        return print_log(LogType.E, null, msg, new Throwable(ILOGTHROWABLE));
    }

    public static int e(Throwable tr) {
        return print_log(LogType.E, null, null, tr);
    }

    public static int e(String msg, Throwable tr) {
        return print_log(LogType.E, null, msg, tr);
    }

    public static int e(String tag, String msg) {
        return print_log(LogType.E, tag, msg, new Throwable(ILOGTHROWABLE));
    }

    public static int e(String tag, String msg, Throwable tr) {
        return print_log(LogType.E, tag, msg, tr);
    }

    // assert
    public static int wtf() {
        return print_log(LogType.A, null, DEFALUTTIPS, new Throwable(ILOGTHROWABLE));
    }

    public static int wtf(String msg) {
        return print_log(LogType.A, null, msg, new Throwable(ILOGTHROWABLE));
    }

    public static int wtf(Throwable tr) {
        return print_log(LogType.A, null, null, tr);
    }

    public static int wtf(String msg, Throwable tr) {
        return print_log(LogType.A, null, msg, tr);
    }

    public static int wtf(String tag, String msg) {
        return print_log(LogType.A, tag, msg, new Throwable(ILOGTHROWABLE));
    }

    public static int wtf(String tag, String msg, Throwable tr) {
        return print_log(LogType.A, tag, msg, tr);
    }

    @SuppressLint("DefaultLocale")
    private static int print_log(LogType type, String tag, String msg, Throwable thr) {

        if (!isNeedLogs()) {
            return FAILURECODE;
        }

        StackTraceElement[] stackTrace = thr.getStackTrace();

        String className = stackTrace[1].getFileName();
        String methodName = stackTrace[1].getMethodName();
        int lineNumber = stackTrace[1].getLineNumber();

        String tagStr = (tag == null ? className : tag);

        methodName = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);

        StringBuilder prefixBuilder = new StringBuilder();
        prefixBuilder.append("[").append(methodName).append(":").append(lineNumber).append("]  ");

        if (msg != null) {
            prefixBuilder.append(msg);
        }

        String msgStr = prefixBuilder.toString();

        boolean isILogThrowable = thr.getMessage().equals(ILOGTHROWABLE);

        if (type == LogType.V) {
            if (isILogThrowable) {
                return Log.v(tagStr, msgStr);
            } else {
                return Log.v(tagStr, msgStr, thr);
            }
        }

        if (type == LogType.D) {
            if (isILogThrowable) {
                return Log.d(tagStr, msgStr);
            } else {
                return Log.d(tagStr, msgStr, thr);
            }
        }

        if (type == LogType.I) {
            if (isILogThrowable) {
                return Log.i(tagStr, msgStr);
            } else {
                return Log.i(tagStr, msgStr, thr);
            }
        }

        if (type == LogType.W) {
            if (isILogThrowable) {
                return Log.w(tagStr, msgStr);
            } else {
                return Log.w(tagStr, msgStr, thr);
            }
        }

        if (type == LogType.E) {
            if (isILogThrowable) {
                return Log.e(tagStr, msgStr);
            } else {
                return Log.e(tagStr, msgStr, thr);
            }
        }

        if (type == LogType.A) {
            if (isILogThrowable) {
                return Log.wtf(tagStr, msgStr);
            } else {
                return Log.wtf(tagStr, msgStr, thr);
            }
        }

        return FAILURECODE;
    }

}
