package com.maciejwozny.nextbikeplanner.station;

import java.util.ArrayList;

public class StationListBuilder {
    private StationDownloader stationDownloader;
    private StationReader stationReader;

    public StationListBuilder(StationDownloader stationDownloader, StationReader stationReader) {
        this.stationDownloader = stationDownloader;
        this.stationReader = stationReader;
    }

    public ArrayList<IStation> create() {
        ArrayList<IStation> stationList = stationReader.readStation();
        ArrayList<IStation> newestStationList = stationDownloader.downloadStations();
        if (newestStationList != null) {
            for (IStation station: newestStationList) {
                if (! stationList.contains(station)) {
                    stationList.add(station);
                }
            }
        }
        return stationList;
    }
}
