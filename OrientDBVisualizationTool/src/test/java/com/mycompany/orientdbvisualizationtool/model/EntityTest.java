package com.mycompany.orientdbvisualizationtool.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests the entities
 * 
 * @author Carlos
 */
public class EntityTest {

    private Entity ent;

    /**
     * Test for the class entity
     */
    @Test
    public void testEntity() {
        ent = new Entity("test");
        assertEquals(ent.getId(), "test");
        ent = new Entity("");
        assertEquals(ent.getId(), "");
        ent = new Entity("very-long-name-let's-hope-this-also-works-with-special-characters124234534645634563456");
        assertEquals(ent.getId(), "very-long-name-let's-hope-this-also-works-with-special-characters124234534645634563456");
    }
}
