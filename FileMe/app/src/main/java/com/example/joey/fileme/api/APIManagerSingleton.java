package com.example.joey.fileme.api;

/**
 * Created by Joey on 13-Nov-16.
 */

public class APIManagerSingleton {

    private static APIManagerSingleton instance = new APIManagerSingleton();

    private APIManagerSingleton() {}

    public static APIManagerSingleton getInstance() {
        return instance;
    }
    
}
