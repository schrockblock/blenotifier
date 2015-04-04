package com.rndapp.blenotifier.models;

import android.content.Context;

import com.rndapp.blenotifier.R;

/**
 * Created by ell on 12/26/14.
 */
public class Rule {
    private int id;
    private String appName;
    private String packageName;
    private int color;

    public static final int RED = 0x00ffff;
    public static final int GREEN = 0xff00ff;
    public static final int BLUE = 0xffff00;
    public static final int PURPLE = 0x00ff00;
    public static final int BROWN = 0x0b3b6f;
    public static final int ORANGE = 0x0088ff;
    public static final int YELLOW = 0x0000ff;
    public static final int PINK = 0x006622;

    public enum RuleType {
        RuleTypePackage
    }

    public Rule(){}

    public Rule(int id, String appName, String packageName, int color) {
        this.id = id;
        this.appName = appName;
        this.packageName = packageName;
        this.color = color;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isMatchForPackage(String packageName){
        return packageName.equals(this.packageName);
    }

    public int getId(){
        return id;
    }

    public int getColor(Context context){
        switch (color){
            case Rule.RED:
                return context.getResources().getColor(R.color.red);
            case Rule.GREEN:
                return context.getResources().getColor(R.color.green);
            case Rule.BLUE:
                return context.getResources().getColor(R.color.blue);
            case Rule.PURPLE:
                return context.getResources().getColor(R.color.purple);
            case Rule.ORANGE:
                return context.getResources().getColor(R.color.orange);
            case Rule.YELLOW:
                context.getResources().getColor(R.color.yellow);
            case Rule.BROWN:
                return context.getResources().getColor(R.color.brown);
            case Rule.PINK:
                return context.getResources().getColor(R.color.pink);
        }
        return -1;
    }

    public int getColorEnum(){
        return color;
    }

    public String getAppName() {
        return appName;
    }

    public String getPackageName() {
        return packageName;
    }
}
