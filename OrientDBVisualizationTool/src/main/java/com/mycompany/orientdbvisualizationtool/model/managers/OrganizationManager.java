package com.mycompany.orientdbvisualizationtool.model.managers;

import com.mycompany.orientdbvisualizationtool.model.Organization;
import java.util.ArrayList;
import java.util.List;

/**
 * Singleton that is responsible for keeping track of all the organizations
 *
 * @author Niels
 */
public class OrganizationManager {

    private static OrganizationManager singletonInstance;
    private List<Organization> organizations;

    /**
     * constructor
     */
    private OrganizationManager() {
        organizations = new ArrayList<>();
    }

    /**
     * Makes sure only one instance of this class can exist
     *
     * @return the current class
     */
    public static OrganizationManager getInstance() {
        if (singletonInstance == null) {
            singletonInstance = new OrganizationManager();
        }
        return singletonInstance;
    }

    /**
     * Adds a new organization to the list of organizations
     *
     * @param newOrganization The organization we want to add
     */
    public void addOrganization(Organization newOrganization) {
        organizations.add(newOrganization);
    }

    /**
     * Adds a new organization to the list of organizations
     *
     * @param id The id of the organization we want to add
     */
    public void addOrganization(String id) {
        Organization newOrganization = new Organization(id);
        addOrganization(newOrganization);
    }

    /**
     * Destroys all instances of its classes to prevent all things existing at
     * the same time
     */
    public void emptyOrganizations() {
        for (Organization o : organizations) {
            o.dereferenceAll();
        }
        organizations = new ArrayList<>();
    }

    /**
     *
     * @return A list of organizations
     */
    public List<Organization> getOrganizations() {
        return organizations;
    }
}
