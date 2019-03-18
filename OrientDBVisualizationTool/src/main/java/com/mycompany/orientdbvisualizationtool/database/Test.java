package com.mycompany.orientdbvisualizationtool.database;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientEdge;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;

public class Test {
    //public Test() {
    public static void main(String[] args) {

        /*
         * Antal, Test
         */

        System.out.println("Trying to Connect to Test database");

        OrientGraph graph = new OrientGraph("plocal:test");
        //OrientGraph graph = new OrientGraph(new ODatabaseDocumentTx("plocal:test"));

        System.out.println("Connect to Test database");



        graph.createVertexType("Person");
        graph.createVertexType("Address");
        System.out.println("Added two classes");


        Vertex vPerson = graph.addVertex("class:Person");
        vPerson.setProperty("firstName", "John");
        vPerson.setProperty("lastName", "Doe");
        System.out.println("Created a Person: " + vPerson.getId());

        Vertex vAddress = graph.addVertex("class:Address");
        vAddress.setProperty("street", "Here street");
        vAddress.setProperty("city", "There city");
        vAddress.setProperty("state", "Somewhere");
        System.out.println("Added a Address: " + vAddress.getId());



        //OrientEdge


        try {
            long amount_vertices = graph.countVertices();
            System.out.println("amount_vertices: " + amount_vertices);
/*
            for (Vertex v : graph.getVertices()) {
                System.out.println("-  " + v.getProperty("firstName") + "   " + v.getProperty("street"));
            };
*/
            for (Vertex v : graph.getVertices()) {
                System.out.println("-  " + v);
            };

        } finally {
            graph.shutdown();
        }
    }

}

