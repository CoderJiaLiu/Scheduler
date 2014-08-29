package com.lucas.scheduler.utilities;

import android.database.Cursor;

public class CursorLoaderUtil {
    public static float getfloat(Cursor c, String columnName) {
        return c.getFloat(c.getColumnIndex(columnName));
    }

    public static int getInt(Cursor c, String columnName) {
        return c.getInt(c.getColumnIndex(columnName));
    }

    public static String getString(Cursor c, String columnName) {
        return c.getString(c.getColumnIndex(columnName));
    }

    public static long getLong(Cursor c, String columnName) {
        return c.getLong(c.getColumnIndex(columnName));
    }

    public static byte[] getBlob(Cursor c, String columnName) {
        return c.getBlob(c.getColumnIndex(columnName));
    }

    public static short getShort(Cursor c, String columnName) {
        return c.getShort(c.getColumnIndex(columnName));
    }

    public static double getDouble(Cursor c, String columnName) {
        return c.getDouble(c.getColumnIndex(columnName));
    }

    public static int getType(Cursor c, String columnName) {
        return c.getType(c.getColumnIndex(columnName));
    }
}
