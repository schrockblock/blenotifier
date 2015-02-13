package com.rndapp.blenotifier.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ell on 12/26/14.
 */
public class RuleOpenHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "rules";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_APP_NAME = "app_name";
    public static final String COLUMN_PACKAGE_NAME = "package_name";
    public static final String COLUMN_COLOR = "color";

    private static final String DATABASE_NAME = TABLE_NAME + ".db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME + "(" + COLUMN_ID
            + " integer primary key autoincrement, "
            + COLUMN_APP_NAME + " text not null, "
            + COLUMN_PACKAGE_NAME + " text not null, "
            + COLUMN_COLOR + " integer "
            + ");";

    public RuleOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(RuleOpenHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
