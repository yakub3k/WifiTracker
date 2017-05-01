package com.yakub.wifitracker;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Narzędzia sieciowe
 */
public class NetUtils {

    public static final String EMPTY_MAC = "00:00:00:00:00:00";
    public static final String PROC_NET_ARP = "/proc/net/arp";

    private NetUtils() {
    }

    /**
     * Adres IP na podstawie MAC z systemu
     */
    public static String getHwAddr(String ip) {
        String result = EMPTY_MAC;
        try {
            FileInputStream file = new FileInputStream(PROC_NET_ARP);
            BufferedReader reader = new BufferedReader(new InputStreamReader(file));

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(ip)) {
                    result = line.substring(0, line.indexOf(" "));
                    break;
                }
            }

            reader.close();
            file.close();
        } catch (java.io.IOException e) {
            return EMPTY_MAC;
        }
        return result;
    }
}
