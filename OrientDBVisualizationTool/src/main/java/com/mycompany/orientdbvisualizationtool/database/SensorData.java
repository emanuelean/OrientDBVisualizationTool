package com.mycompany.orientdbvisualizationtool.database;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Niels
 */
public class SensorData extends Database{

    public SensorData(OrientGraph graph) {
        super(graph);
    }

    @Override
    public void refresh(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Returns all the sensors from a location
     *
     * @param name the name of the location
     * @return a list with all the sensors
     */
    public List<Vertex> getSensorsFromLocation(String name) {
        for (Vertex v : graph.getVerticesOfClass("v_location", false)) {
            if (v.getProperty("name").equals(name)) {
                System.out.println("Location found");
                Iterable<Edge> connections;
                List<Vertex> sensors = new ArrayList<>();
                connections = v.getEdges(Direction.IN, "has-a");
                for (Edge e : connections) {
                    sensors.add(e.getVertex(Direction.OUT));
                }
                return sensors;
            }
        }
        System.out.println("Location not found");
        return null;
    }
    
}
