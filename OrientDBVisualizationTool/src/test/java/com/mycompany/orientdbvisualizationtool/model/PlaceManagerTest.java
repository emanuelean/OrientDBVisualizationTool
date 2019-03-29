package com.mycompany.orientdbvisualizationtool.model;

import com.mycompany.orientdbvisualizationtool.model.places.*;
import java.lang.reflect.Field;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Niels
 */
public class PlaceManagerTest {

    private PlaceManager manager;

    private Location location;
    private Building buildingA;
    private Building buildingB;
    private Floor floorAA;
    private Floor floorAB;
    private Floor floorBA;
    private Floor floorBB;
    private Room roomAAA;
    private Room roomAAB;
    private Room roomABA;
    private Room roomABB;
    private Room roomBAA;
    private Room roomBAB;
    private Room roomBBA;
    private Room roomBBB;

    public PlaceManagerTest() {
    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {

        Field instance;
        try {
            instance = PlaceManager.class.getDeclaredField("singletonInstance");
            instance.setAccessible(true);
            instance.set(null, null);
        } catch (Exception ex) {
            ex.printStackTrace();
        } 

        manager = PlaceManager.getInstance();

        location = new Location("L", "L");
        buildingA = new Building("L.A", "A");
        buildingB = new Building("L.B", "B");
        floorAA = new Floor("L.A.A", "AA");
        floorAB = new Floor("L.A.B", "AB");
        floorBA = new Floor("L.B.A", "BA");
        floorBB = new Floor("L.B.B", "BB");
        roomAAA = new Room("L.A.A.A", "AAA");
        roomAAB = new Room("L.A.A.B", "AAB");
        roomABA = new Room("L.A.B.A", "ABA");
        roomABB = new Room("L.A.B.B", "ABB");
        roomBAA = new Room("L.B.A.A", "BAA");
        roomBAB = new Room("L.B.A.B", "BAB");
        roomBBA = new Room("L.B.B.A", "BBA");
        roomBBB = new Room("L.B.B.B", "BBB");
    }

    @After
    public void tearDown() {
    }

    /**
     * links all the test classes
     */
    public void addAll() {
        manager.addPlace(location, null);
        manager.addPlace(buildingA, location);
        manager.addPlace(buildingB, location);
        manager.addPlace(floorAA, buildingA);
        manager.addPlace(floorAB, buildingA);
        manager.addPlace(floorBA, buildingB);
        manager.addPlace(floorBB, buildingB);
        manager.addPlace(roomAAA, floorAA);
        manager.addPlace(roomAAB, floorAA);
        manager.addPlace(roomABA, floorAB);
        manager.addPlace(roomABB, floorAB);
        manager.addPlace(roomBAA, floorBA);
        manager.addPlace(roomBAB, floorBA);
        manager.addPlace(roomBBA, floorBB);
        manager.addPlace(roomBBB, floorBB);
    }

    /**
     * Test of getInstance method, of class PlaceManager.
     */
    @Test
    public void testGetInstance() {
        PlaceManager expResult = PlaceManager.getInstance();
        PlaceManager result = PlaceManager.getInstance();
        assertEquals(expResult, result);
    }

    /**
     * Test of linkPlaces method, of class PlaceManager.
     */
    @Test
    public void testLinkPlaces() {
        Place child = buildingA;
        Place parent = location;
        Place grandChild = floorAA;
        manager.linkPlaces(child, parent);
        manager.linkPlaces(grandChild, child);
        assertEquals(parent.getChildren().get(0), child);
        assertEquals(parent, child.getParent());

        assertEquals(grandChild, child.getChildren().get(0));
        assertEquals(grandChild, parent.getChildren().get(0).getChildren().get(0));

        assertEquals(grandChild.getParent(), child);
        assertEquals(grandChild.getParent().getParent(), parent);

        assertEquals(grandChild.getParent(), parent.getChildren().get(0));
    }

    /**
     * Test of getPlace method, of class PlaceManager.
     */
    @Test
    public void testGetPlace() {
        addAll();
        assertEquals(manager.getPlace("L"), location);
        assertEquals(manager.getPlace("L.B"), buildingB);
        assertEquals(manager.getPlace("L.A.B"), floorAB);
        assertEquals(manager.getPlace("L.B.B"), floorBB);
        assertEquals(manager.getPlace("L.A.A.A"), roomAAA);
        assertEquals(manager.getPlace("L.B.A.B"), roomBAB);
    }

    /**
     * Test of addPlace method, of class PlaceManager.
     */
    @Test
    public void testAddPlace_Place_Place() {
        manager.addPlace(buildingA, null);

        assertEquals(manager.getPlace(buildingA.getId()), buildingA);
        assertNull(buildingA.getParent());
        
        manager.addPlace(floorAA, null);
        manager.addPlace(roomAAB, floorAA);
        assertEquals(manager.getPlace(floorAA.getId()), floorAA);
        assertEquals(manager.getPlace(roomAAB.getId()), roomAAB);

        assertEquals(roomAAB.getParent(), floorAA);
        assertEquals(roomAAB, floorAA.getChildren().get(0));
    }

    /**
     * Test of addPlace method, of class PlaceManager.
     */
    @Test
    public void testAddPlace_4args() {
        manager.addPlace("L.A.A", "AA", "floor", buildingB);

        assertNotNull(manager.getPlace("L.A.A"));
        assertEquals(manager.getPlace("L.A.A"), buildingB.getChildren().get(0));
        assertEquals(manager.getPlace("L.A.A").getParent(), buildingB);
    }

    /**
     * Test of emptyPlaces method, of class PlaceManager.
     */
    @Test
    public void testEmptyPlaces() {
        addAll();
        manager.emptyPlaces();
        assertNull(location.getChildren());
        assertNull(buildingA.getChildren());
        assertNull(floorBB.getChildren());
        assertNull(roomABA.getChildren());

        assertNull(location.getParent());
        assertNull(buildingA.getParent());
        assertNull(floorBB.getParent());
        assertNull(roomABA.getParent());

        assertNull(manager.getPlace("L"));
        assertNull(manager.getPlace("L.B"));
        assertNull(manager.getPlace("L.A.B"));
        assertNull(manager.getPlace("L.B.B"));
        assertNull(manager.getPlace("L.A.A.A"));
        assertNull(manager.getPlace("L.B.A.B"));
    }
}
