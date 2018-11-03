package com.maciejwozny.nextbikeplanner.net;

import android.os.Parcelable;

public abstract class IStation implements Parcelable {
    public abstract String getName();
    public abstract int getIdNumber();
    public abstract int getBikeNumber();
    public abstract int getFreeRacksNumber();
    public abstract double getLongitude();
    public abstract double getLatitude();
}
