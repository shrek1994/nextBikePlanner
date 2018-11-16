package com.maciejwozny.nextbikeplanner;

import com.maciejwozny.nextbikeplanner.graph.GraphBuilder;
import com.maciejwozny.nextbikeplanner.graph.IStationEdge;
import com.maciejwozny.nextbikeplanner.graph.IStationVertex;
import com.maciejwozny.nextbikeplanner.station.IStation;

import org.jgrapht.Graph;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class ExampleUnitTest {
    private MainActivity activity;

    @Before
    public void setUp() {
        ShadowLog.stream = System.out;
        activity = Robolectric.buildActivity(MainActivity.class)
                .create()
                .resume()
                .get();

//        StationParser stationParser = new StationParser();
//        StationDownloader stationDownloader = new StationDownloader(new DataDownloader(), stationParser);
//        StationReader stationReader = new StationReader(activity, stationParser);
    }

    @Test
    public void shouldCorrectReadFromFileLikeFromInternet() {
        ArrayList<IStation> stationList = null;

        GraphBuilder builder = new GraphBuilder();
        Graph<IStationVertex, IStationEdge> graph
                = builder.build(stationList, activity);

//        builder.saveGraph(graph);

        Graph<IStationVertex, IStationEdge> expectedGraph = builder.readGraph(activity);

        assertEquals(expectedGraph, graph);
    }
}