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
public class EntityAttributes{
	private ArrayList<String> types;
	
	public EntityAttributes (Vertex v){
		Iterable<Edge> parents;
		types = new ArrayList();
		parents = v.getEdges(Direction.OUT, "instance-of");
		for(Edge e : parents) {
			try {
				types.add(e.getVertex(Direction.IN).getProperty("type-id"));
			} catch (Exception ex) {
				continue;
			}
		}
	}

	/**
	 * @return the types
	 */
	public ArrayList<String> getTypes() {
		return types;
	}

}
