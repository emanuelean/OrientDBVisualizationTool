package com.mycompany.orientdbvisualizationtool.database;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;
import com.mycompany.orientdbvisualizationtool.model.Entity;
import com.mycompany.orientdbvisualizationtool.model.places.Place;

/**
 * Responsible for retrieving specific information about entities from the
 * database
 *
 * @author Niels, Carlos, Albert
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
        boolean isCategory = false;
        List<Vertex> entities = new ArrayList<>();

        //we initialize the nodes to explore
        Vertex startVertex = getVertexById("V_category.id", "sensor");
        Set<Vertex> bufferVertex = Sets.newHashSet();
        bufferVertex.add(startVertex);

        //buffer for the connections
        Iterable<Edge> bufferConnections;

        while (!bufferVertex.isEmpty()) {
            //while we still have elements to explore
            Vertex v = bufferVertex.iterator().next();
            bufferVertex.remove(v);
            bufferConnections = v.getEdges(Direction.IN, "is-a");

            if (bufferConnections != null) {
                for (Edge e : bufferConnections) {
                    //we add all the nodes connected with "is-a" to the explored node to the set of nodes to be explored
                    bufferVertex.add(e.getVertex(Direction.OUT));
                }
            }

            bufferConnections = v.getEdges(Direction.IN, "instance-of");

            for (Edge e : bufferConnections) {
                isCategory = true;
                bufferVertex.add(e.getVertex(Direction.OUT));
            }
            if (isCategory) {
                //we know that a sensor will be an instance of the Vertex 
                //(or an instance of the Vertex) so we add it to the list of entities
                entities.add(v);
                isCategory = false;
            }
        }
        sensorInitialization = entities;
    }

    @Override
    public void refresh(String id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @return A list of sensor entities
     */
    public static List<Vertex> getSensorEntities() {
        return sensorInitialization;
    }

    /**
     * Returns all the sensors from a location
     *
     * @param id the id of the location
     * @return a list with all the sensors
     */
    public List<Vertex> getSensorsFromLocation(String id) {
        Vertex currentVertex = getVertexById("v_location.id", id);
        if (currentVertex == null) {
            return null;
        }

        Iterable<Edge> connections;
        List<Vertex> sensors = new ArrayList<>();
        connections = currentVertex.getEdges(Direction.IN, "has-a");

        for (Edge e : connections) {
            Vertex s = e.getVertex(Direction.OUT);
            //second statement is a temporary fix to prevent the place itself to
            //occur in the list
            if (isSensor(s) && !((String) s.getProperty("id")).equals(id)) {
                sensors.add(s);
            }
        }
        return sensors;
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
     * Retrieves the attributes of a certain entity
     * 
     * @param entity The place we want to retrieve the attributes from
     * @return A class containing all the attributes
     */
    public EntityAttributes getAttributes(Entity entity) {
        Vertex v = getVertexById("V_instance.id", entity.getId());
        return new EntityAttributes(v);
    }
}
