package com.mycompany.orientdbvisualizationtool.database;

import com.tinkerpop.blueprints.Vertex;

/**
 * @author carlos
 *
 */
public class PlaceAttributes {

    private String organization;
    private float area;
    private float epeak;
    private String year;
    private String city;
    private String country;
    private String address;
    private String name;
    private String postal;
    private float gpeak;
    private String elabel;
    private String buildingtype;
    private double eindex;
    
    

    /**
     * Creates an object with the attributes of the place
     *
     * @param v the vertex to get the attributes from
     */
    public PlaceAttributes(Vertex v) {
        try {
            this.organization = v.getProperty("organization");
        } catch (Exception e) {
            this.organization = null;
        }
        try {
            this.area = v.getProperty("area");
        } catch (Exception e) {
            this.area = 0;
        }
        try {
            this.epeak = v.getProperty("e-peak");
        } catch (Exception e) {
            this.epeak = 0;
        }
        try {
            this.year = v.getProperty("year");
        } catch (Exception e) {
            this.year = null;
        }
        try {
            this.city = v.getProperty("city");
        } catch (Exception e) {
            this.city = null;
        }
        try {
            this.country = v.getProperty("country");
        } catch (Exception e) {
            this.country = null;
        }
        try {
            this.address = v.getProperty("address");
        } catch (Exception e) {
            this.address = null;
        }
        try {
            this.name = v.getProperty("name");
        } catch (Exception e) {
            this.name = null;
        }
        try {
            this.postal = v.getProperty("postal");
        } catch (Exception e) {
            this.postal = null;
        }
        try {
            this.gpeak = v.getProperty("g-peak");
        } catch (Exception e) {
            this.gpeak = 0;
        }
        try {
            this.elabel = v.getProperty("e-label");
        } catch (Exception e) {
            this.elabel = null;
        }
        try {
            this.buildingtype = v.getProperty("building-type");
        } catch (Exception e) {
            this.buildingtype = null;
        }
        try {
            this.eindex = v.getProperty("e-index");
        } catch (Exception e) {
            this.eindex = 0;
        }
    }

    /**
     * @return the eindex
     */
    public double getEindex() {
        return eindex;
    }

    /**
     * @return the buildingtype
     */
    public String getBuildingtype() {
        return buildingtype;
    }

    /**
     * @return the elabel
     */
    public String getElabel() {
        return elabel;
    }

    /**
     * @return the gpeak
     */
    public float getGpeak() {
        return gpeak;
    }

    /**
     * @return the postal
     */
    public String getPostal() {
        return postal;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @return the year
     */
    public String getYear() {
        return year;
    }

    /**
     * @return the epeak
     */
    public float getEpeak() {
        return epeak;
    }

    /**
     * @return the area
     */
    public float getArea() {
        return area;
    }

    /**
     * @return the organization
     */
    public String getOrganization() {
        return organization;
    }

}
