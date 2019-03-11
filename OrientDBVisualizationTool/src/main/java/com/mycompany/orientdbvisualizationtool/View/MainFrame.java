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

import java.util.ArrayList;

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

        scene = new Scene(borderPane, WIDTH, HEIGHT);
        this.node1 = new Node("node1", this);
        this.node2 = new Node("node2", this);
        node1.setLocation(-100, 0);
        node2.setLocation(100, 0);
        ArrayList nodes = new ArrayList();
        nodes.add(node1);
        nodes.add(node2);
        CenterPane centerPane = new CenterPane(scene, nodes);
        borderPane.setCenter(centerPane);
        this.edge = new Edge(node1, node2);
        edge.setStartX(node1.getTranslateX());
        edge.setStartY(node1.getTranslateY());
        edge.setEndX(node2.getTranslateX());
        edge.setEndY(node2.getTranslateY());
        centerPane.getChildren().add(edge);
        node1.addToPane(centerPane);
        node2.addToPane(centerPane);

        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public Scene getScene() {
        return scene;
    }

}
