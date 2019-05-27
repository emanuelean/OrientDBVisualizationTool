package com.mycompany.orientdbvisualizationtool.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import com.mycompany.orientdbvisualizationtool.model.Organization;
import com.mycompany.orientdbvisualizationtool.model.places.Location;
import com.mycompany.orientdbvisualizationtool.model.places.Place;

public class OrganizationTest {
	private Organization org;
	private Location test1;
	private Location test2;
	private List<Place> list;

	
	@Before
	public void setUp() {
		org = new Organization("test");
		test1 = new Location("L", "L");
		test2 = new Location("L2", "L2");
		list = new ArrayList<>();
	}
	
	public void addAll() {
		org.addPlace(test1);
		org.addPlace(test2);
		list.add(test1);
		list.add(test2);
	}
	
	/**
	 * Test for the getId method of class Organization
	 */
	@Test
	public void testGetId() {
		assertEquals(org.getId(), "test");
	}
	
	/**
	 * Test for the addPlace and getPlaces methods of class Organization
	 */
	@Test
	public void testAddGetPlace() {
		addAll();		
		assertEquals(org.getPlaces(),list);
	}
	
	/**
	 * Test for the dereferenceAll method of class Organization
	 */
	@Test
	public void testDereferenceAll() {
		addAll();
		org.dereferenceAll();
		assertEquals(org.getPlaces(), null);
	}
}
