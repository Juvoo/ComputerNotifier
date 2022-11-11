package com.kinghouser.util;

import android.app.Notification;
import android.net.ConnectivityManager;
import com.kinghouser.ui.home.HomeViewModel;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class NotificationRelay extends Thread {

    public Socket clientSocket;
    public ServerSocket serverSocket;

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(0);
            HomeViewModel.mText.postValue("Connect with ip address: " + Utils.getMobileIP() + " and port: " + serverSocket.getLocalPort());
            System.out.println("Connect with ip address: " + Utils.getMobileIP() + " and port: " + serverSocket.getLocalPort());

            new ClientReceiverThread().start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void relayNotification(Notification notification) {
        if (clientSocket == null) return;
        try {
            com.kinghouser.util.Notification customNotification = new com.kinghouser.util.Notification("", notification.tickerText.toString());
            OutputStream outputStream = clientSocket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

            objectOutputStream.writeObject(customNotification);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}