package com.lucas.scheduler.utilities;

public class Log {
    public static final int FLAG_FRAMEWORK = 0x00001;
    public static final int FLAG_UI = 0x00002;
    public static final int FLAG_TRANSATION = 0x00004;
    public static final int FLAG_DATA = 0x00008;

    private static final String SIGN_FRAMEWORK = "!!";
    private static final String SIGN_UI = "@@";
    private static final String SIGN_TRANSATION = "##";
    private static final String SIGN_DATA = "**";
    private static final int FLAG_MASK = 0xFFFFF;
    private static final String TAG = "Scheduler";

    private static final String getSign(int flag) {
        String sign = "~~~";
        if ((flag & FLAG_FRAMEWORK) != 0) {
            sign += SIGN_FRAMEWORK;
        } else {
            sign += "~~";
        }
        if ((flag & FLAG_UI) != 0) {
            sign += SIGN_UI;
        } else {
            sign += "~~";
        }
        if ((flag & FLAG_TRANSATION) != 0) {
            sign += SIGN_TRANSATION;
        } else {
            sign += "~~";
        }
        if ((flag & FLAG_DATA) != 0) {
            sign += SIGN_DATA;
        } else {
            sign += "~~";
        }
        sign += " ";
        return sign;
    }

    private static String getReverse(String src) {
        StringBuilder sb = new StringBuilder(src);
        return sb.reverse().toString();
    }

    public static void v(int flag, String msg) {
        if ((flag & FLAG_MASK) != 0) {
            android.util.Log.v(TAG, getSign(flag) + msg
                    + getReverse(getSign(flag)));
        }

    }

    public static void d(int flag, String msg) {
        if ((flag & FLAG_MASK) != 0) {
            android.util.Log.d(TAG, getSign(flag) + msg
                    + getReverse(getSign(flag)));
        }

    }

    public static void i(int flag, String msg) {
        if ((flag & FLAG_MASK) != 0) {
            android.util.Log.i(TAG, getSign(flag) + msg
                    + getReverse(getSign(flag)));
        }

    }

    public static void w(int flag, String msg) {
        if ((flag & FLAG_MASK) != 0) {
            android.util.Log.w(TAG, getSign(flag) + msg
                    + getReverse(getSign(flag)));
        }

    }

    public static void e(int flag, String msg) {
        if ((flag & FLAG_MASK) != 0) {
            android.util.Log.e(TAG, getSign(flag) + msg
                    + getReverse(getSign(flag)));
        }

    }

    public static void e(int flag, String msg,Exception e) {
        if ((flag & FLAG_MASK) != 0) {
            android.util.Log.e(TAG, getSign(flag) + msg
                    + getReverse(getSign(flag)),e);
        }

    }
    
    public static void framwork(String msg) {
        i(FLAG_FRAMEWORK, msg);
    }

    public static void transation(String msg) {
        i(FLAG_TRANSATION, msg);
    }

    public static void ui(String msg) {
        d(FLAG_UI, msg);
    }

    public static void dataOperation(String msg) {
        d(FLAG_DATA, msg);
    }
}
