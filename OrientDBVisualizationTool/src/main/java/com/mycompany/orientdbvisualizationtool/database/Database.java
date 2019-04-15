package com.mycompany.orientdbvisualizationtool.database;

import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;

/**
 *
 * @author Niels
 */
public abstract class Database {

    /**
     * The graph that is used to load data
     */
    protected OrientGraph graph;
    
    /**
     * constructor
     * 
     * @param graph The graph we want to use to load data
     */
    public Database(OrientGraph graph) {
        this.graph = graph;
    }
    
    /**
     * Loads the graph with the specific data
     * 
     * @param id The id of the vertex we want to refresh
     */
    public abstract void refresh(String id);
    
    /**
     * Retrieves the category of a certain vertex
     * 
     * @param vertex The vertex we want the category of
     * @return The category of the vertex
     */
    protected String getCategory(Vertex vertex) {
        Iterable<Vertex> vertices;
        vertices = vertex.getVertices(Direction.OUT, "instance-of");
        for (Vertex v : vertices) {
            return v.getProperty("id");
        }
        return "default";
    }
    
    /**
     * retrieves a vertex based on a key and a value
     * 
     * @param key The table and field name we want to compare
     * @param value The value we want to find
     * @return The first vertex that is found based on this key and value
     */
    protected Vertex getVertexById(String key, String value) {
        Iterable<Vertex> vertices = graph.getVertices(key, value);
        for (Vertex v : vertices) {
            return v;
        }
        return null;
    }
    
    /**
     * retrieves a list of edges based on a key and a value
     * 
     * @param key The table and field name we want to compare
     * @return The list of edges that is found based on this key and value
     */
    protected Iterable<Edge> getEdgesByKey(String key) {
        return graph.getEdgesOfClass(key);
    }
    
    /**
     * Retrieves a list of vertices based on a specific query
     * 
     * @param query The sql query we want to execute
     * @return The list of vertices that match the query
     */
    protected Iterable<Vertex> queryVertices(String query) {
        return (Iterable<Vertex>) graph.command(new OCommandSQL(query)).execute();
    }
    
    /**
     * Retrieves a list of edges based on a specific query
     * 
     * @param query The sql query we want to execute
     * @return The list of edges that match the query
     */
    protected Iterable<Edge> queryEdges(String query) {
        return (Iterable<Edge>) graph.command(new OCommandSQL(query)).execute();
    }
}
