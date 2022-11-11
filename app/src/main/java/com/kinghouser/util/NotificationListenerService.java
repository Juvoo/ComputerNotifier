package com.kinghouser.util;

import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.service.notification.StatusBarNotification;


public class NotificationListenerService extends android.service.notification.NotificationListenerService {

    public static NotificationRelay notificationRelay;

    @Override
    public IBinder onBind(Intent intent) {
        prepareNotificationRelayServer();
        return super.onBind(intent);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            System.out.println("Received notification");
            notificationRelay.relayNotification(sbn.getNotification());
        }
    }

    private static void prepareNotificationRelayServer() {
        notificationRelay = new NotificationRelay();
        notificationRelay.start();
    }
}