package com.maciejwozny.nextbikeplanner.station;

public abstract class IStationsBuilder {
    private IStationsBuilder nextStationBuilder;

    public IStationsBuilder(IStationsBuilder nextStationBuilder) {
        this.nextStationBuilder = nextStationBuilder;
    }

    public String createJsonWithStationList() {
        String jsonText = getStation();
        if (jsonText == null && nextStationBuilder != null) {
            return nextStationBuilder.createJsonWithStationList();
        }
        return jsonText;
    }

    abstract String getStation();
}
