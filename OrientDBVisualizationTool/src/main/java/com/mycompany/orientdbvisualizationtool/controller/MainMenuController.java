package com.mycompany.orientdbvisualizationtool.controller;

import static com.mycompany.orientdbvisualizationtool.View.MainFrame.WIDTH;
import com.mycompany.orientdbvisualizationtool.model.managers.PlaceManager;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * FXML Controller class
 *
 * @author User
 */
public class MainMenuController {
  
    @FXML
    public TextField Node_Name_Text_Field;
    @FXML
    public TextField Node_ID_Text_Field;
    @FXML
    public TextField Node_Type_Text_Field;
    @FXML
    public TreeView Left_Tree_View;
    @FXML
    public TableColumn Table_View_Entity_ID;
    @FXML
    public TableView Table_View;
    @FXML
    public Button Hide_Button;
    @FXML
    public Button Show_All_Button;
    @FXML
    public Label Left_Status_Label;
    @FXML
    public Label Right_Status_Label;
    @FXML
    public CheckBox Dark_Mode_Check_Box;


    /**
     * The main default properties of controller are initialized
     */
    @FXML
    public void initialize() {

        Node_Name_Text_Field.setDisable(true);
        Node_ID_Text_Field.setDisable(true);
        Node_Type_Text_Field.setDisable(true);

    }
}
