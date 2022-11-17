package com.kinghouser.util;

import android.service.notification.StatusBarNotification;
import android.widget.Toast;
import com.kinghouser.MainActivity;
import com.kinghouser.ui.home.HomeViewModel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread {

    public ServerSocket serverSocket = null;
    public Socket clientSocket = null;
    public final ArrayList<ClientThread> clientThreads = new ArrayList<>();

    public boolean isRunning = false;

    public void run() {
        try {
            serverSocket = new ServerSocket(0);
            isRunning = true;
            HomeViewModel.mText.postValue("Connect with ip address: " + Utils.getMobileIP() + " and port: " + serverSocket.getLocalPort());
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                clientSocket = serverSocket.accept();
                clientThreads.add(new ClientThread(clientSocket));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void relayNotification(StatusBarNotification statusBarNotification) {
        com.kinghouser.util.Notification customNotification = new com.kinghouser.util.Notification(statusBarNotification.getNotification().extras.getString("android.title"), statusBarNotification.getNotification().extras.getString("android.text"));

        for (ClientThread clientThread : clientThreads) {
            clientThread.sendNotification(customNotification);
        }
    }

    public void relayNotification(com.kinghouser.util.Notification notification) {
        for (ClientThread clientThread : clientThreads) {
            clientThread.sendNotification(notification);
        }
    }

    public void close() {
        try {
            for (ClientThread clientThread : clientThreads) {
                clientThread.clientSocket.close();
                clientThread.stop();
            }
            clientThreads.clear();

            serverSocket.close();
            isRunning = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class ClientThread extends Thread {

    private ObjectInputStream objectInputStream = null;
    private ObjectOutputStream objectOutputStream = null;
    public final Socket clientSocket;

    public ClientThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.start();
    }

    @Override
    public void run() {
        try {
            objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            objectOutputStream.flush();
            objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            while (clientSocket.isConnected()) {
            }
            /*
            objectInputStream.close();
            ps.close();
            clientSocket.close(
            );
             */
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendNotification(Notification notification) {
        try {
            objectOutputStream.writeObject(notification);
            objectOutputStream.flush();

            Toast.makeText(MainActivity.applicationContext, "Sent notification to client", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}