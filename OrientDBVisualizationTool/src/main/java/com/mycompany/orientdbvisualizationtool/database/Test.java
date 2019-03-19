package com.mycompany.orientdbvisualizationtool.database;

import com.orientechnologies.orient.core.command.OCommandOutputListener;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.db.tool.ODatabaseImport;
import com.orientechnologies.orient.core.exception.OSchemaException;
import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientEdge;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;


public class Test {
    //public Test() {
    public static void main(String[] args) {

        /*
         * Antal & Carlos Test
         */

        System.out.println("Trying to Connect to Test database");

        // Demo, the database given by the client
        //OrientGraph graph = new OrientGraph("plocal:/home/carlos/Desktop/orientdb-community-2.2.36/bin/test6");
        OrientGraph graph = new OrientGraph("plocal:/home/antal/orientdb-community-2.2.20/databases/GratefulDeadConcerts", "root", "6159");
        //OrientGraph graph = new OrientGraph("remote:plocal:Demo", "root", "6159");

        // Test database. Is an almost empty database
        //OrientGraph graph = new OrientGraph("plocal:Test");
        //OrientGraph graph = new OrientGraph("remote:localhost/test5", "root", "root");

        // Doesn't work, but may be useful
        //OrientGraph graph = new OrientGraph(new ODatabaseDocumentTx("plocal:test"));

        System.out.println("Connect to Test database");
        
     
        /*
        // Add some classes to the Test database
		try {
            graph.createVertexType("Person");
            graph.createVertexType("Address");
            System.out.println("Added two classes");
		} catch (OSchemaException e) {
			System.out.println("Classess already exist");
		}
		*/
        /*
        // Add some vertices to the Test database
        Vertex vPerson = graph.addVertex("class:Person");
        vPerson.setProperty("firstName", "John");
        vPerson.setProperty("lastName", "Doe");
        System.out.println("Created a Person: " + vPerson.getId());

        Vertex vAddress = graph.addVertex("class:Address");
        vAddress.setProperty("street", "Here street");
        vAddress.setProperty("city", "There city");
        vAddress.setProperty("state", "Somewhere");
        System.out.println("Added a Address: " + vAddress.getId());
        */

        //
        //OrientEdge


        try {
            // print the amount of vertices
            long amount_vertices = graph.countVertices();
            System.out.println("amount_vertices: " + amount_vertices);
/*
            for (Vertex v : graph.getVertices()) {
                System.out.println("-  " + v.getProperty("firstName") + "   " + v.getProperty("street"));
            };
*/

            // Print all vertices
            for (Vertex v : graph.getVertices()) {
                System.out.println("-  " + v);
            };

        } finally {
            graph.shutdown();
        }
    }

}

