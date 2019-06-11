package com.mycompany.orientdbvisualizationtool.controller.CenterPaneActions;

import com.mycompany.orientdbvisualizationtool.controller.MainController;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

/**
 * Action taken when mouse dragged event occurs in Center_Anchor_Pane
 */
public class CenterPaneDraggedAction implements EventHandler<MouseEvent> {

    private AnchorPane Center_Anchor_Pane;
    private Rectangle selectionArea;
    private MainController controller;
    private Double mouseSourceX;
    private Double mouseSourceY;

    /**
     * constructor
     *
     * @param Center_Anchor_Pane anchor to which the mouse drag event occurs
     * @param selectionArea rectangle drawn to represent an area selection
     * @param controller mainController class containing the source mouse
     * pressed x and y properties
     */
    public CenterPaneDraggedAction(AnchorPane Center_Anchor_Pane, Rectangle selectionArea, MainController controller) {
        this.Center_Anchor_Pane = Center_Anchor_Pane;
        this.selectionArea = selectionArea;
        this.mouseSourceX = controller.getMouseSourceX();
        this.mouseSourceY = controller.getMouseSourceY();
        this.controller = controller;
    }

    /**
     * Action taken in the anchor pane in the center. Defines how a selection
     * area rectangle is drawn to select several nodes.
     *
     * @param event Mouse event for mouse dragged
     */
    @Override
    public void handle(MouseEvent event) {
        mouseSourceX = controller.getMouseSourceX();
        mouseSourceY = controller.getMouseSourceY();
        //area selection using blue rectangle
        if (event.isControlDown()) {
            selectionArea.setVisible(true);
            selectionArea.setLayoutX(mouseSourceX);
            selectionArea.setLayoutY(mouseSourceY);

            double selectionAreaWidth = event.getX() - mouseSourceX;
            double selectionAreaHeight = event.getY() - mouseSourceY;

            if (selectionAreaHeight < 0 && selectionAreaWidth < 0) {
                selectionArea.setLayoutX(event.getX());
                selectionArea.setLayoutY(event.getY());
                selectionArea.setWidth(-selectionAreaWidth);
                selectionArea.setHeight(-selectionAreaHeight);
            } else if (selectionAreaHeight >= 0 && selectionAreaWidth < 0) {
                selectionArea.setLayoutX(event.getX());
                selectionArea.setLayoutY(mouseSourceY);
                selectionArea.setWidth(-selectionAreaWidth);
                selectionArea.setHeight(selectionAreaHeight);
            } else if (selectionAreaHeight < 0 && selectionAreaWidth >= 0) {
                selectionArea.setLayoutX(mouseSourceX);
                selectionArea.setLayoutY(event.getY());
                selectionArea.setWidth(selectionAreaWidth);
                selectionArea.setHeight(-selectionAreaHeight);
            } else if (selectionAreaHeight >= 0 && selectionAreaWidth >= 0) {
                selectionArea.setWidth(selectionAreaWidth);
                selectionArea.setHeight(selectionAreaHeight);
            }
            Center_Anchor_Pane.getScene().setCursor(Cursor.CROSSHAIR);
        }
    }
}
