package com.kinghouser.util;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import com.kinghouser.MainActivity;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static android.content.Context.WIFI_SERVICE;

public class Utils {

    public static String getLocalIpAddress() throws UnknownHostException {
        WifiManager wifiManager = (WifiManager) MainActivity.applicationContext.getApplicationContext().getSystemService(WIFI_SERVICE);
        assert wifiManager != null;
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipInt = wifiInfo.getIpAddress();
        return InetAddress.getByAddress(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(ipInt).array()).getHostAddress();
    }
}
