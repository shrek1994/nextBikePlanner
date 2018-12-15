package com.maciejwozny.nextbikeplanner;

import com.maciejwozny.nextbikeplanner.graph.EdgeReader;
import com.maciejwozny.nextbikeplanner.graph.GraphBuilder;
import com.maciejwozny.nextbikeplanner.graph.IStationEdge;
import com.maciejwozny.nextbikeplanner.graph.IStationVertex;
import com.maciejwozny.nextbikeplanner.station.IStation;
import com.maciejwozny.nextbikeplanner.station.StationFactory;

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
    private DEPRECATED_MainActivity activity;

    @Before
    public void setUp() {
        ShadowLog.stream = System.out;
        activity = Robolectric.buildActivity(DEPRECATED_MainActivity.class)
                .create()
                .resume()
                .get();

//        StationParser stationParser = new StationParser();
//        StationDownloader stationDownloader = new StationDownloader(new DataDownloader(), stationParser);
//        StationReader stationReader = new StationReader(activity, stationParser);
    }

    @Test
    public void shouldCorrectReadFromFileLikeFromInternet() {
        ArrayList<IStation> stationList = new StationFactory(activity).createStationList();

        GraphBuilder builder = new GraphBuilder(activity, new EdgeReader(activity));
        Graph<IStationVertex, IStationEdge> graph
                = builder.buildGraph(stationList);

        builder.saveGraphEdges(graph);

        System.err.println("saved !");

        try {
            Thread.sleep(1000 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}