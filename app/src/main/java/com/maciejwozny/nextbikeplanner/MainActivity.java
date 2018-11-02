package com.maciejwozny.nextbikeplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.maciejwozny.nextbikeplanner.graph.GraphBuilder;
import com.maciejwozny.nextbikeplanner.graph.IStationEdge;
import com.maciejwozny.nextbikeplanner.graph.IStationVertex;
import com.maciejwozny.nextbikeplanner.net.DataDownloader;
import com.maciejwozny.nextbikeplanner.net.IStation;
import com.maciejwozny.nextbikeplanner.net.StationDownloader;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private List<IStation> stationList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        StationDownloader stationDownloader = new StationDownloader(new DataDownloader());
        stationList = stationDownloader.downloadStations();
        List<String> stationStrings = new ArrayList<>();

        for(IStation station : stationList) {
            stationStrings.add(station.getName());
//            Log.d(TAG, "'" + station.getName() + "'");
        }

        AutoCompleteTextView start = findViewById(R.id.autoCompleteStart);
        AutoCompleteTextView end = findViewById(R.id.autoCompleteEnd);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, stationStrings);
        start.setAdapter(adapter);
        end.setAdapter(adapter);

        Button calculate = findViewById(R.id.calculateButton);
        calculate.setOnClickListener(view -> {
            Graph<IStationVertex, IStationEdge> graph = new GraphBuilder().build(stationList);

            Log.d(TAG, graph.toString());
            Log.d(TAG, "number of vertex: " + graph.vertexSet().size());
            Log.d(TAG, "number of edges: " + graph.edgeSet().size());
            IStationVertex destination = null, source = null;
            for (IStationVertex vertex : graph.vertexSet()) {
                if (start.getText().toString().equals(vertex.getName())) {
                    source = vertex;
                }
                if (end.getText().toString().equals(vertex.getName())) {
                    destination = vertex;
                }
            }

            if (source == null) {
                Toast.makeText(this, "select correct start !", Toast.LENGTH_LONG).show();
                return;
            }

            if (destination == null) {
                Toast.makeText(this, "select correct end !", Toast.LENGTH_LONG).show();
                return;
            }

            GraphPath<IStationVertex, IStationEdge> path
                    = DijkstraShortestPath.findPathBetween(graph, source, destination);
            List<IStationEdge> stationEdges = path.getEdgeList();

            String pathString = "";
            for (IStationEdge edge: stationEdges) {
                pathString += edge.getDestination().getName() + " -> " + edge.getSource().getName()
                        + " = " + edge.getTime() + " meters\n";
            }
            Log.d(TAG, pathString);
            TextView text = MainActivity.this.findViewById(R.id.outputText);
            text.setText(pathString);
        });


        Button map = findViewById(R.id.mapButton);
        map.setOnClickListener(view -> {
            startActivity(new Intent(this, MapActivity.class));
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
