package com.mycompany.orientdbvisualizationtool.controller.TableViewAction;

import com.mycompany.orientdbvisualizationtool.model.Entity;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import javafx.event.EventHandler;
import javafx.stage.Stage;

/**
 * Action performed when Table View Item is clicked
 */
public class TableViewItemClickedAction implements EventHandler<MouseEvent> {

    private TableView Table_View;

    /**
     * constructor
     *
     * @param table_View the table on which action is performed on
     */
    public TableViewItemClickedAction(TableView table_View) {
        Table_View = table_View;
    }

    /**
     * Entity details are displayed as an entity item is double clicked in table
     * view.
     *
     * @param event mouse event for action
     */
    @Override
    public void handle(MouseEvent event) {
        if (event.getClickCount() == 2) {
            Entity entity = (Entity) Table_View.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.getDialogPane().setMinWidth(800);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("/icons/sb-icon.png"));
            alert.setTitle("Entity Information");
            alert.setHeaderText("Entity details");
            alert.setContentText("Entity ID:\n" + entity.getId() + "\n\nEntity Description:"
                    + getEntityDescription(entity));
            ImageView imageView = new ImageView(new Image("/icons/sensor-icon.png"));
            imageView.setFitHeight(64);
            imageView.setFitWidth(64);
            alert.setGraphic(imageView);
            alert.showAndWait();
        }
    }

    private String getEntityDescription(Entity entity) {
        String entityDescription = "";
        entity.loadAttributes();
        for (String s : entity.getAttributes().getTypes()) {
            entityDescription += "\n" + s;
        }
        return entityDescription;
    }

}
