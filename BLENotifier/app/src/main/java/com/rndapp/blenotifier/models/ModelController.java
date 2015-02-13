package com.rndapp.blenotifier.models;

import android.content.Context;
import android.service.notification.StatusBarNotification;

import com.rndapp.blenotifier.actionexecutors.TinyDuinoCommunicator;

/**
 * Created by ell on 12/26/14.
 */
public class ModelController {

    public static void handleNotificationPosted(Context context, StatusBarNotification notification){
        Rule rule = Matcher.ruleForPackage(context, notification.getPackageName());
        if (rule != null){
            TinyDuinoCommunicator communicator = new TinyDuinoCommunicator(context);
            communicator.sendColor(rule.getColor());
        }
    }

    public static void handleNotificationRemoved(Context context, StatusBarNotification notification){
        Rule rule = Matcher.ruleForPackage(context, notification.getPackageName());
        if (rule != null){
            TinyDuinoCommunicator communicator = new TinyDuinoCommunicator(context);
            communicator.sendColor(0xffffff);
        }
    }

}
