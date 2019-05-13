package com.mycompany.orientdbvisualizationtool.View;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Responsible for the main application
 * 
 * @author Niels, Albert
 */
public class VisApplication extends Application {

    private View mainView;
    private View menuView;
    private Stage primaryStage;
    private static VisApplication singletonInstance;

    /**
     * Retrieves a singleton instance of VisApplication
     * 
     * @return 
     */
    public static VisApplication getInstance() {
        if (singletonInstance == null) {
            singletonInstance = new VisApplication();
        }
        return singletonInstance;
    }
    
    /**
     * Initialises stuff for a new application
     * 
     * @param primaryStage 
     */
    public void startUp(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.getIcons().add(new Image("icons/sb-icon.png"));
        primaryStage.setTitle("Sustainable Buildings orientDB Visualizing Tool");
        mainView = new MainView();
        menuView = new MainMenuView();
        changeToMenu();
    }

    /**
     * Switches to the the tree view
     */
    public void changeToMain() {
        mainView.start();
        primaryStage.setScene(mainView.getScene());
        primaryStage.show();
    }

    /**
     * switches to the main menu
     */
    public void changeToMenu() {
        menuView.start();
        primaryStage.setScene(menuView.getScene());
        primaryStage.show();
    }

    /**
     * Starts a new application
     * 
     * @param primaryStage The stage that this application 
     * @throws Exception Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        VisApplication vis = VisApplication.getInstance();
        vis.startUp(primaryStage);
    }
}