package com.mycompany.orientdbvisualizationtool.database;

import javax.persistence.*;
import java.util.*;

/**
 *
 * @author Niels
 */
public class DatabaseManager {

    private EntityManagerFactory factory;
    private EntityManager manager;

    private String fileName;

    /**
     * constructor
     *
     * @param fileName the filename of the file we want to create
     */
    public DatabaseManager(String fileName) {
        this.fileName = fileName;
        openDataSource();
        closeDataSource();
    }
    
    /**
     * opens the datasource with a specific filename
     */
    private void openDataSource() {
        factory = Persistence.createEntityManagerFactory("db/" + fileName + ".odb");
        manager = factory.createEntityManager();
    }

    /**
     * closes the datasource
     */
    private void closeDataSource() {
        manager.close();
        factory.close();
    }

}
