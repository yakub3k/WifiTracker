package com.yakub.wifitracker;


import android.util.Log;

import java.io.File;

public class Logger {

    public static Logger getLogger(){
        if (INSTANCE == null)
            INSTANCE = new Logger("points.log");
        return INSTANCE;
    }
    private static Logger INSTANCE;

    private final File file;

    private Logger(String filename) {
        this.file = new File(filename);
    }

    public void write(String text){

    };
}
