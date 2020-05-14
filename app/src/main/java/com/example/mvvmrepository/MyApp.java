package com.example.mvvmrepository;

import com.example.corelib.CoreApp;
import com.example.corelib.service.CloudService;

public class MyApp extends CoreApp {

    @Override
    public void onCreate() {
        super.onCreate();
        cloudService = new CloudService();
    }

}
