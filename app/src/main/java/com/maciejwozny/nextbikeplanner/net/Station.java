package com.maciejwozny.nextbikeplanner.net;

public class Station implements IStation {
    private String name;
    private int idNumber;
    private int bikeNumber;
    private int freeRacksNumber;
    private double longitude;
    private double latitude;

    public Station(String name, int idNumber, int bikeNumber, int freeRacksNumber, double longitude, double latitude) {
        this.name = name;
        this.idNumber = idNumber;
        this.bikeNumber = bikeNumber;
        this.freeRacksNumber = freeRacksNumber;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getIdNumber() {
        return idNumber;
    }

    @Override
    public int getBikeNumber() {
        return bikeNumber;
    }

    @Override
    public int getFreeRacksNumber() {
        return freeRacksNumber;
    }

    @Override
    public double getLongitude() {
        return longitude;
    }

    @Override
    public double getLatitude() {
        return latitude;
    }
}
