package com.kinghouser.util;

import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.service.notification.StatusBarNotification;
import com.google.android.material.snackbar.Snackbar;


public class NotificationListenerService extends android.service.notification.NotificationListenerService {

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            System.out.println("Received notification: " + sbn.getNotification().tickerText + " with timeout " + sbn.getNotification().when);
        }
        System.out.println(Build.VERSION.SDK_INT);
    }
}