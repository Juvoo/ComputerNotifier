package com.kinghouser.util;

import android.app.Notification;

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
            serverSocket = new ServerSocket(5000);
            System.out.println("Connect with port: " + serverSocket.getLocalPort());

            new ClientReceiverThread().start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void relayNotification(Notification notification) {
        if (clientSocket == null) return;
        try {
            OutputStream outputStream = clientSocket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

            objectOutputStream.writeObject(notification);

            objectOutputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}