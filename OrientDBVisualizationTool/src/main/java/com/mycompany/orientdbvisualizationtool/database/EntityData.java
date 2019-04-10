package com.mycompany.orientdbvisualizationtool.database;

import com.mycompany.orientdbvisualizationtool.model.Entity;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Niels
 */
public class EntityData extends Database {

    /**
     * constructor
     * 
     * @param graph The graph we want to load the data from
     */
    public EntityData(OrientGraph graph) {
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

    /**
     * Generates a list of entities with randomized ids for testdata This is
     * only temporary, this will later be changed to query actual entities from
     * the database
     *
     * @return A list of entities
     */
    public ArrayList<Entity> queryEntities() {
        Random random = new Random();
        ArrayList<Entity> entities = new ArrayList<>();
        for (int i = 0; i < random.nextInt(100); i++) {
            Entity newEntity = new Entity("long_name_for_testdata_sensor_stuff_" + i);
            entities.add(newEntity);
        }
        return entities;
    }

}
