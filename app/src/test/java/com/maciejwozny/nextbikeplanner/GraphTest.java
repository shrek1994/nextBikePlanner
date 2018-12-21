package com.maciejwozny.nextbikeplanner;

import com.maciejwozny.nextbikeplanner.graph.RoadReader;
import com.maciejwozny.nextbikeplanner.graph.GraphBuilder;
import com.maciejwozny.nextbikeplanner.graph.StationEdge;
import com.maciejwozny.nextbikeplanner.graph.StationVertex;
import com.maciejwozny.nextbikeplanner.station.Station;
import com.maciejwozny.nextbikeplanner.station.StationFactory;

import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import java.util.ArrayList;
import java.util.Objects;

import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class GraphTest {
    private DEPRECATED_MainActivity context;

    @Before
    public void setUp() {
        ShadowLog.stream = System.out;
        context = Robolectric.buildActivity(DEPRECATED_MainActivity.class)
                .create()
                .resume()
                .get();
    }

    @Test
    public void shouldSaveGraphToFile() {
        ArrayList<Station> stationList = new StationFactory(context).createStationList();

        GraphBuilder builder = new GraphBuilder(context, new RoadReader(context));
        Graph<StationVertex, StationEdge> graph
                = builder.buildGraph(stationList);

        builder.saveGraphEdges(graph);

        try {
            Thread.sleep(60 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldGraphWillBeConnected() {
        ArrayList<Station> stationList = new StationFactory(context).createStationList();

        GraphBuilder builder = new GraphBuilder(context, new RoadReader(context));
        Graph<StationVertex, StationEdge> graph
                = builder.buildGraph(stationList);

        ConnectivityInspector<StationVertex, StationEdge> inspector
                = new ConnectivityInspector<>(graph);
        assertTrue(inspector.isConnected());
    }


    @Test
    public void graphShouldDoesNotHaveEdgeWithTimeMoreThan20Minutes() {
        ArrayList<Station> stationList = new StationFactory(context).createStationList();

        GraphBuilder builder = new GraphBuilder(context, new RoadReader(context));
        Graph<StationVertex, StationEdge> graph
                = builder.buildGraph(stationList);

        for (StationEdge edge: graph.edgeSet()) {
            assertTrue("duration = " + Objects.toString(edge.getRoad().mDuration) +
                            ", for edge=" + edge.toString(),
                    edge.getRoad().mDuration <= 20 * 60);
        }
    }

}