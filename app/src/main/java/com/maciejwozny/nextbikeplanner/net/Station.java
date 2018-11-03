package com.maciejwozny.nextbikeplanner.net;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Station implements Parcelable {
    private String name;
    private int idNumber;
    private int bikeNumber;
    private int freeRacksNumber;
    private double longitude;
    private double latitude;

    protected Station(Parcel in) {
        name = in.readString();
        idNumber = in.readInt();
        bikeNumber = in.readInt();
        freeRacksNumber = in.readInt();
        longitude = in.readDouble();
        latitude = in.readDouble();
    }

    public static final Creator<Station> CREATOR = new Creator<Station>() {
        @Override
        public Station createFromParcel(Parcel in) {
            return new Station(in);
        }

        @Override
        public Station[] newArray(int size) {
            return new Station[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(idNumber);
        parcel.writeInt(bikeNumber);
        parcel.writeInt(freeRacksNumber);
        parcel.writeDouble(longitude);
        parcel.writeDouble(latitude);
    }

    public Station(String name, int idNumber, int bikeNumber, int freeRacksNumber, double longitude, double latitude) {
        this.name = name;
        this.idNumber = idNumber;
        this.bikeNumber = bikeNumber;
        this.freeRacksNumber = freeRacksNumber;
        this.longitude = longitude;
        this.latitude = latitude;
    }

//    @Override
    public String getName() {
        return name;
    }

//    @Override
    public int getIdNumber() {
        return idNumber;
    }

//    @Override
    public int getBikeNumber() {
        return bikeNumber;
    }

//    @Override
    public int getFreeRacksNumber() {
        return freeRacksNumber;
    }

//    @Override
    public double getLongitude() {
        return longitude;
    }

//    @Override
    public double getLatitude() {
        return latitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
