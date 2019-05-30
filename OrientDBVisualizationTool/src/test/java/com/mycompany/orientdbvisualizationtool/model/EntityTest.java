package com.mycompany.orientdbvisualizationtool.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import com.mycompany.orientdbvisualizationtool.model.Entity;

public class EntityTest {
	private Entity ent;
	
	/**
	 * Test for the class entity 
	 */
	@Test
	public void testEntity() {
		ent = new Entity("test");
		assertEquals(ent.getId(), "test");
	}
}
