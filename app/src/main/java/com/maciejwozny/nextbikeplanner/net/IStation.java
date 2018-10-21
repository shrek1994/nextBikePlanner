package com.maciejwozny.nextbikeplanner.net;

public interface IStation {
    String getName();
    int getIdNumber();
    int getBikeNumber();
    int getFreeRacksNumber();
    double getLongitude();
    double getLatitude();
}
