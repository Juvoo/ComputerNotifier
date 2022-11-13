package com.kinghouser.util;

import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.service.notification.StatusBarNotification;
import android.widget.Toast;
import com.kinghouser.MainActivity;


public class NotificationListenerService extends android.service.notification.NotificationListenerService {

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Toast.makeText(MainActivity.applicationContext, "Received notification", Toast.LENGTH_SHORT).show();
            MainActivity.server.relayNotification(sbn.getNotification());
        }
    }
}