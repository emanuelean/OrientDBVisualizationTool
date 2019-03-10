/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.orientdbvisualizationtool.View;

import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 *
 * @author emanuelnae
 */
public class Node extends Rectangle {
    
    private Text label;
    private boolean selected;
    private final Color DEFAULT_COLOR = Color.LIGHTGRAY;
    private MainFrame mainFrame;
    private CenterPane centerPane;

    public Node(String nodeName, MainFrame mainFrame, CenterPane centerPane) {
       this.label = new Text(nodeName);
       this.selected = false;

       setWidth(nodeName.length() * 10.0f);
       setHeight(40.0f);

       //rounded rectangle
       setArcWidth(30.0);
       setArcHeight(20.0);

       setFill(DEFAULT_COLOR);
       setStroke(Color.DARKGREY);
       this.centerPane = centerPane;
       this.mainFrame = mainFrame;

       setMouseListenerProperties();
   }

   private void setMouseListenerProperties() {
       this.setOnMousePressed(e -> {
                   if (!this.isSelected()) {
                       this.setSelected(true);
                   } else {
                       this.setSelected(false);
                   }
               }
       );


       Text nodeLabel = this.getLabel();


       nodeLabel.setOnMousePressed(e -> {
                   if (!this.isSelected()) {
                       this.setSelected(true);
                   } else {
                       this.setSelected(false);
                   }
               }
       );

       this.setOnMouseReleased(e -> mainFrame.getScene().setCursor(Cursor.DEFAULT));
       nodeLabel.setOnMouseReleased(e -> mainFrame.getScene().setCursor(Cursor.DEFAULT));
   }

   public Text getLabel() {
       return label;
   }

   public void setLocation(double x, double y) {
       this.setTranslateX(x);
       this.setTranslateY(y);
       label.setTranslateX(x);
       label.setTranslateY(y);
   }

   public void addToPane(StackPane centerPane) {
       centerPane.getChildren().add(this);
       centerPane.getChildren().add(this.getLabel());
   }

   public boolean isSelected() {
       return selected;
   }

   public void setSelected(boolean selected) {
       this.selected = selected;
       if (selected) {
           this.setFill(Color.LAVENDER);
       } else {
           this.setFill(DEFAULT_COLOR);
       }
   }

   private void moveSeveralNodes(MouseEvent e) {
       double tempDiffX = centerPane.getMouseSourceX() - e.getSceneX();
       double tempDiffY = centerPane.getMouseSourceY() - e.getSceneY();
       for (Node node1 : centerPane.getNodes()) {
           if (node1.isSelected()) {
               node1.setLocation(node1.getTranslateX() - tempDiffX, node1.getTranslateY() - tempDiffY);
               centerPane.setMouseSourceX(e.getSceneX());
               centerPane.setMouseSourceY(e.getSceneY());
           }
       }
   }
}
