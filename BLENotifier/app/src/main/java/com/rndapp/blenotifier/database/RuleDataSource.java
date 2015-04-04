package com.rndapp.blenotifier.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.rndapp.blenotifier.models.Rule;

import java.util.ArrayList;

/**
 * Created by ell on 12/26/14.
 */
public class RuleDataSource {
    // Database fields
    private SQLiteDatabase database;
    private RuleOpenHelper dbHelper;
    private String[] allColumns = { RuleOpenHelper.COLUMN_ID,
            RuleOpenHelper.COLUMN_COLOR,
            RuleOpenHelper.COLUMN_APP_NAME,
            RuleOpenHelper.COLUMN_PACKAGE_NAME};

    public RuleDataSource(Context context) {
        dbHelper = new RuleOpenHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Rule createRule(String appName, String packageName, int color) {
        ContentValues values = new ContentValues();
        values.put(RuleOpenHelper.COLUMN_COLOR, color);
        values.put(RuleOpenHelper.COLUMN_APP_NAME, appName);
        values.put(RuleOpenHelper.COLUMN_PACKAGE_NAME, packageName);
        long insertId = database.insert(RuleOpenHelper.TABLE_NAME, null, values);
        Cursor cursor = database.query(RuleOpenHelper.TABLE_NAME,
                allColumns, RuleOpenHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Rule newRule = cursorToRule(cursor);
        cursor.close();
        return newRule;
    }

    public boolean updateRule(Rule rule){
        ContentValues values = new ContentValues();
        values.put(RuleOpenHelper.COLUMN_COLOR, rule.getColorEnum());
        values.put(RuleOpenHelper.COLUMN_APP_NAME, rule.getAppName());
        values.put(RuleOpenHelper.COLUMN_PACKAGE_NAME, rule.getPackageName());
        String[] whereArgs = {String.valueOf(rule.getId())};
        return rule.getId() == database.update(RuleOpenHelper.TABLE_NAME,
                values, RuleOpenHelper.COLUMN_ID + " = ?", whereArgs);
    }

    public ArrayList<Rule> getAllRules() {
        ArrayList<Rule> rules = new ArrayList<Rule>();

        Cursor cursor = database.query(RuleOpenHelper.TABLE_NAME,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Rule rule = cursorToRule(cursor);
            rules.add(rule);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return rules;
    }

    public Rule getRule(int ruleId) {
        Rule rule = null;

        Cursor cursor = database.query(RuleOpenHelper.TABLE_NAME,
                allColumns,
                RuleOpenHelper.COLUMN_ID + " = " + String.valueOf(ruleId),
                null, null, null, null);

        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            rule = cursorToRule(cursor);
        }

        // Make sure to close the cursor
        cursor.close();
        return rule;
    }

    private Rule cursorToRule(Cursor cursor) {
        return new Rule(
                cursor.getInt(cursor.getColumnIndex(RuleOpenHelper.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(RuleOpenHelper.COLUMN_APP_NAME)),
                cursor.getString(cursor.getColumnIndex(RuleOpenHelper.COLUMN_PACKAGE_NAME)),
                cursor.getInt(cursor.getColumnIndex(RuleOpenHelper.COLUMN_COLOR))
        );
    }
}
