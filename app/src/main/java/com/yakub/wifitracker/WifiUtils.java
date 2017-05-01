package com.yakub.wifitracker;

import android.net.wifi.WifiConfiguration;

public final class WifiUtils {

    public final static String WPA2_PSK_CCMP = "[WPA2-PSK-CCMP]";
    public final static String WPA2_PSK_CCMP_TKIP = "[WPA2-PSK-CCMP+TKIP]";
    public final static String W = "[]";
    public final static String WPS = "[WPS]";
    public final static String ESS = "[ESS]";

    // [WPA-PSK-CCMP+TKIP][WPA2-PSK-CCMP+TKIP][WPS][ESS]
    private WifiUtils() {
    }

    //todo dapisać obsługę opisów szyfrowania
    public static String getCrypt(String capabilites){
        return "";
    }
}
