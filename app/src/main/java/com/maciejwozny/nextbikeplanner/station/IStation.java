package com.maciejwozny.nextbikeplanner.station;

import java.io.Serializable;

public interface IStation extends Serializable {
    String getName();
    int getIdNumber();
    int getBikeNumber();
    int getFreeRacksNumber();
    double getLongitude();
    double getLatitude();
}
