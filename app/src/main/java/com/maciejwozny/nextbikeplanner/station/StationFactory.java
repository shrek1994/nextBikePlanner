package com.maciejwozny.nextbikeplanner.station;

import android.content.Context;

import com.maciejwozny.nextbikeplanner.net.DataDownloader;

import java.util.ArrayList;

public class StationFactory implements IStationFactory {
    private StationParser parser;
    private IStationsBuilder firstBuilder;

    public StationFactory(Context context) {
        parser = new StationParser();
        DataDownloader dataDownloader = new DataDownloader();
        IStationsBuilder downloader = new StationDownloader(null, dataDownloader);
        firstBuilder = new StationReader(downloader, context);
    }

    @Override
    public ArrayList<IStation> createStationList() {
        String jsonText = firstBuilder.createJsonWithStationList();
        return parser.parse(jsonText);
    }
}
