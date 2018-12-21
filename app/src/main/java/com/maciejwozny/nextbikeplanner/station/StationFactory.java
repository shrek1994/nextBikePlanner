package com.maciejwozny.nextbikeplanner.station;

import android.content.Context;

import java.util.ArrayList;

public class StationFactory  {
    private StationParser parser;
    private IStationsBuilder firstBuilder;

    public StationFactory(Context context) {
        parser = new StationParser();
        DataDownloader dataDownloader = new DataDownloader();
        IStationsBuilder downloader = new StationDownloader(null, dataDownloader);
        firstBuilder = new StationReader(downloader, context);
    }

    public ArrayList<Station> createStationList() {
        String jsonText = firstBuilder.createJsonWithStationList();
        return parser.parse(jsonText);
    }
}
