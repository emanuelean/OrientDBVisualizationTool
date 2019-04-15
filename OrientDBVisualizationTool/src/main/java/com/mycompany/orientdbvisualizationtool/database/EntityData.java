package com.mycompany.orientdbvisualizationtool.database;

import com.mycompany.orientdbvisualizationtool.model.Entity;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import com.google.common.collect.Sets;

/**
 *
 * @author Niels & Carlos
 */
public class EntityData extends Database {

    private static List<Vertex> sensorInitialization;

    /**
     * Initializes the sensor comparer list
     *
     * @param graph The graph we want to load the data from
     */
    public EntityData(OrientGraph graph) {
        super(graph);
        initSensors();
    }
    
    /**
     * initialises a list of sensors
     */
    private void initSensors() {
        int flag = 0;
        List<Vertex> entities = new ArrayList<>();
        Set<Vertex> bufferVertex = Sets.newHashSet(graph.getVertices("V_category", new String[]{"id"}, new Object[]{"sensor"})); //we initialize the nodes to explore
        Iterable<Edge> bufferConnections; //buffer for the connections

        while (!bufferVertex.isEmpty()) { //while we still have elements to explore
            Vertex v = bufferVertex.iterator().next();
            bufferVertex.remove(v);
            bufferConnections = v.getEdges(Direction.IN, "is-a");

            if (bufferConnections != null) {
                for (Edge e : bufferConnections) {
                    bufferVertex.add(e.getVertex(Direction.OUT)); //we add all the nodes connected with "is-a" to the explored node to the set of nodes to be explored
                }
            }

            bufferConnections = v.getEdges(Direction.IN, "instance-of");

            for (Edge e : bufferConnections) {
                flag = 1;
                bufferVertex.add(e.getVertex(Direction.OUT));
            }
            if (flag == 1) {
                entities.add(v); //we know that a sensor will be an instance of the Vertex (or an instance of the Vertex) so we add it to the list of entities
                flag = 0;
            }
        }
        sensorInitialization = entities;
    }

    @Override
    public void refresh(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    /**
     * 
     * @return A list of sensor entities
     */
    public static List<Vertex> getSensorEntities() {
        return sensorInitialization;
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
                Iterable<Edge> connections;
                List<Vertex> sensors = new ArrayList<>();
                connections = v.getEdges(Direction.IN, "has-a");
                for (Edge e : connections) {
                    Vertex s = e.getVertex(Direction.OUT);
                    if (this.isSensor(s)) {
                        sensors.add(s);
                    }
                }
                return sensors;
            }
        }
        return null;
    }

    /**
     * Auxiliary function to check if a vertex is a sensor
     *
     * @param v The vertex we want to know if it is a sensor or not
     * @return True if it is, false if it isn't
     */
    private boolean isSensor(Vertex v) {
        Iterable<Edge> connections = v.getEdges(Direction.OUT, "instance-of");
        for (Edge e : connections) {
            if (sensorInitialization.contains(e.getVertex(Direction.IN))) {
                return true;
            }
        }
        return false;
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
