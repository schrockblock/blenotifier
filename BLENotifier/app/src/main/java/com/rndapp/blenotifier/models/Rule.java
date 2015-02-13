package com.rndapp.blenotifier.models;

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

    public int getColor() {
        return color;
    }

    public String getAppName() {
        return appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public boolean isMatchForPackage(String packageName){
        return packageName.equals(this.packageName);
    }

    public Rule(int id, String appName, String packageName, int color) {
        this.id = id;
        this.appName = appName;
        this.packageName = packageName;
        this.color = color;
    }
}
