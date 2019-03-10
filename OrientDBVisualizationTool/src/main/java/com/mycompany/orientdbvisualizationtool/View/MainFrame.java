/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.orientdbvisualizationtool.View;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * @author emanuelnae
 */
public class MainFrame extends Application {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = WIDTH * 9 / 16;
    private Scene scene;
    private Node node1;
    private Node node2;
    private Edge edge;


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Main View");
        BorderPane borderPane = new BorderPane();


        //top

        //center

        CenterPane centerPane = new CenterPane(scene);

        borderPane.setCenter(centerPane);


        scene = new Scene(borderPane, WIDTH, HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
        this.node1 = new Node("node1", this, centerPane);
        this.node2 = new Node("node2", this, centerPane);
        node1.setLocation(-100, 0);
        node2.setLocation(100, 0);
        centerPane.getChildren().add(edge);
        node1.addToPane(centerPane);
        node2.addToPane(centerPane);
        this.edge = new Edge(node1, node2);
        edge.setStartX(node1.getTranslateX());
        edge.setStartY(node1.getTranslateY());
        edge.setEndX(node2.getTranslateX());
        edge.setEndY(node2.getTranslateY());

    }


    public Scene getScene() {
        return scene;
    }

}
