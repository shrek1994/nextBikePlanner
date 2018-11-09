package com.maciejwozny.nextbikeplanner.graph;

import com.maciejwozny.nextbikeplanner.net.IStation;
import com.maciejwozny.nextbikeplanner.net.Station;

import org.jgrapht.Graph;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GraphBuilderTest {
    private GraphBuilder sut = new GraphBuilder();

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void shouldCorrectCreateGraph() {
        List<Station> stationList = new ArrayList<>();
        stationList.add(new Station("a", 0 ,0 ,0, 0.1, 0.1));
        stationList.add(new Station("b", 0 ,0 ,0, 0.3, 0.3));
        stationList.add(new Station("c", 0 ,0 ,0, 0.5, 0.5));
        stationList.add(new Station("d", 0 ,0 ,0, 0.7, 0.7));

        Graph<IStationVertex, IStationEdge> graph = sut.build(stationList);
        assertEquals(4, graph.vertexSet().size());
        assertEquals(6, graph.edgeSet().size());
    }

}