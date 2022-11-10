package com.kinghouser.util;

import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.service.notification.StatusBarNotification;
import com.google.android.material.snackbar.Snackbar;

import java.net.ServerSocket;


public class NotificationListenerService extends android.service.notification.NotificationListenerService {

    public static ServerSocket serverSocket;

    @Override
    public IBinder onBind(Intent intent) {
        prepareNotificationRelayServer();
        return super.onBind(intent);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            System.out.println("Received notification");
            NotificationRelay.relayNotification(sbn.getNotification());
        }
        System.out.println(Build.VERSION.SDK_INT);
    }

    private static void prepareNotificationRelayServer() {
        try {
            serverSocket = new ServerSocket(25565);

            while (true) {
                serverSocket.accept();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}