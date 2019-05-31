/**
 *
 */
package com.mycompany.orientdbvisualizationtool.database;

import java.util.ArrayList;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

/**
 * @author Carlos
 *
 */
public class EntityAttributes {

    private ArrayList<String> types;

    public EntityAttributes(Vertex v) {
        Iterable<Edge> parents;
        types = new ArrayList();
        parents = v.getEdges(Direction.OUT, "instance-of");
        for (Edge e : parents) {
            if(hasProperty(e)) {
                types.add(e.getVertex(Direction.IN).getProperty("type-id"));
            }
        }
    }

    private boolean hasProperty(Edge edge) {
        try {
            String value = edge.getVertex(Direction.IN).getProperty("type-id");
            if (value == null) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * @return the types
     */
    public ArrayList<String> getTypes() {
        return types;
    }

}
