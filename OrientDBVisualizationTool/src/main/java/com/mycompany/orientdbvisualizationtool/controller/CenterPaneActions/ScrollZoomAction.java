package com.mycompany.orientdbvisualizationtool.controller.CenterPaneActions;

import javafx.event.EventHandler;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Scale;

/**
 * Action taken when zooming is done by scrolling
 */
public class ScrollZoomAction implements EventHandler<ScrollEvent> {

    private AnchorPane Center_Anchor_Pane;

    /**
     * constructor
     * @param center_Anchor_Pane anchor pane to which the zooming takes place
     */
    public ScrollZoomAction(AnchorPane center_Anchor_Pane) {
        Center_Anchor_Pane = center_Anchor_Pane;
    }

    /**
     * Handle for a scroll event
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

            Scale scale = new Scale();
            scale.setPivotX(event.getX());
            scale.setPivotY(event.getY());
            scale.setX(Center_Anchor_Pane.getScaleX() * zoomFactor);
            scale.setY(Center_Anchor_Pane.getScaleY() * zoomFactor);

            Center_Anchor_Pane.getTransforms().add(scale);

            event.consume();
        }
    }
}
