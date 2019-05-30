package com.mycompany.orientdbvisualizationtool.controller.CenterPaneActions;

import javafx.event.EventHandler;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;

/**
 * Action taken when zooming is done by scrolling
 */
public class ScrollZoomAction implements EventHandler<ScrollEvent> {

    private AnchorPane Center_Anchor_Pane;
    private double zoomScale;
    private double maxZoomFactor;
    private double minZoomFactor;

    /**
     * constructor
     *
     * @param center_Anchor_Pane anchor pane to which the zooming takes place
     */
    public ScrollZoomAction(AnchorPane center_Anchor_Pane) {
        Center_Anchor_Pane = center_Anchor_Pane;
        zoomScale = 0.;
        //zoom maximum: 200%
        maxZoomFactor = 1;
        //zoom minimum: 50%
        minZoomFactor = -1;
    }

    /**
     * Handle for a scroll event
     *
     * @param event scroll event
     */
    @Override
    public void handle(ScrollEvent event) {
        if (event.isControlDown()) {
            double zoomFactor = 1.05;
            double deltaY = event.getDeltaY();

            if (deltaY < 0) {
                zoomFactor = 2.0 - zoomFactor;
            }

            zoomScale += (zoomFactor - 1);
            Scale scale = new Scale();

            if (zoomScale > minZoomFactor && zoomScale < maxZoomFactor) {
                VBox vBox = (VBox) Center_Anchor_Pane.getChildren().get(2);
                scale.setX(vBox.getScaleX() * zoomFactor);
                scale.setY(vBox.getScaleY() * zoomFactor);
                vBox.getTransforms().add(scale);
            } else if (zoomScale < minZoomFactor) {
                zoomScale = minZoomFactor;
            } else if (zoomScale > maxZoomFactor) {
                zoomScale = maxZoomFactor;
            }

            event.consume();
        }
    }
}