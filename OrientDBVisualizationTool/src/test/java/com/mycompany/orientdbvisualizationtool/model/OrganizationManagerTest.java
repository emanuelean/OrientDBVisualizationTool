package com.mycompany.orientdbvisualizationtool.model;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.mycompany.orientdbvisualizationtool.model.managers.OrganizationManager;
import com.mycompany.orientdbvisualizationtool.model.places.Location;

public class OrganizationManagerTest {

    private Organization org1;
    private Organization org2;
    private OrganizationManager manager;
    private Location location;

    @Before
    public void setUp() {

        Field instance;
        try {
            instance = OrganizationManager.class.getDeclaredField("singletonInstance");
            instance.setAccessible(true);
            instance.set(null, null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        manager = OrganizationManager.getInstance();

        org1 = new Organization("test1");
        org2 = new Organization("test2");
        location = new Location("L", "L");
        org1.addPlace(location);
    }

    public void addAll() {
        manager.addOrganization(org1);
        manager.addOrganization(org2);
        manager.addOrganization("test3");

    }

    /**
     * Test of getInstance method, of class OrganizationManager.
     */
    @Test
    public void testGetInstance() {
        OrganizationManager expResult = OrganizationManager.getInstance();
        OrganizationManager result = OrganizationManager.getInstance();
        assertEquals(expResult, result);
    }

    /**
     * Test of addOrganization method, of class OrganizationManager.
     */
    @Test
    public void testGetOrganization() {
        addAll();
        assertEquals(manager.getOrganization("test1"), org1);
    }

    /**
     * Test of addOrganization with Organization argument of class
     * OrganizationManager
     */
    @Test
    public void testAddOrganizationOrg() {
        addAll();
        assertEquals(manager.getOrganization("test2"), org2);
    }

    /**
     * Test of addOrganization with String argument of class OrganizationManager
     */
    @Test
    public void testAddOrganizationString() {
        addAll();
        assertEquals(manager.getOrganization("test3").getId(), "test3");
    }

    /**
     * Test of getOrganizations of class OrganizationManager
     */
    @Test
    public void testGetOrganizations() {
        addAll();

        List<Organization> list = new ArrayList<>();
        list.add(org1);
        list.add(org2);
        list.add(manager.getOrganization("test3"));

        assertEquals(manager.getOrganizations(), list);
    }

    /**
     * Test of emptyOrganizations of class OrganizationManager
     */
    @Test
    public void testEmptyOrganizations() {
        addAll();
        manager.emptyOrganizations();
        List<Organization> emptylist = new ArrayList<>();
        assertEquals(manager.getOrganizations(), emptylist);
    }

}
