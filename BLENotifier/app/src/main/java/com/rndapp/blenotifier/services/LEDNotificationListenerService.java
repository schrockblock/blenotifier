package com.rndapp.blenotifier.services;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import com.rndapp.blenotifier.models.ModelController;

/**
 * Created by ell on 12/25/14.
 */
public class LEDNotificationListenerService extends NotificationListenerService {

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);

        ModelController.handleNotificationPosted(getApplicationContext(), sbn);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);

        ModelController.handleNotificationRemoved(getApplicationContext(), sbn);
    }
}
