package com.mycompany.orientdbvisualizationtool.View;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Niels
 */
public class VisApplication extends Application {

    private View mainView;
    private View menuView;
    private Stage primaryStage;
    private static VisApplication singletonInstance;

    public static VisApplication getInstance() {
        if (singletonInstance == null) {
            singletonInstance = new VisApplication();
        }
        return singletonInstance;
    }

    public void changeToMain() {
        mainView.start();
        primaryStage.setScene(mainView.getScene());
        primaryStage.show();
    }

    public void changeToMenu() {
        menuView.start();
        primaryStage.setScene(menuView.getScene());
        primaryStage.show();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.getIcons().add(new Image("icons/sb-icon.png"));
        primaryStage.setTitle("Sustainable Buildings orientDB Visualizing Tool");
        mainView = new MainView();
        menuView = new MainMenuView();
        changeToMenu();
    }
}
