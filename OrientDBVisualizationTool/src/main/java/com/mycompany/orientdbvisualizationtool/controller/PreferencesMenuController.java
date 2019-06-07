package com.mycompany.orientdbvisualizationtool.controller;

import com.mycompany.orientdbvisualizationtool.View.VisApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;

public class PreferencesMenuController {

    private Scene mainScene;

    //color pickers
    @FXML private ColorPicker Base_Color_Picker;
    @FXML private ColorPicker Accent_Color_Picker;
    @FXML private ColorPicker Focus_Color_Picker;
    @FXML private ColorPicker Faint_Focus_Color_Picker;

    @FXML private ColorPicker CP_Background_Color_Picker;
    @FXML private ColorPicker CP_Grid_Outer_Lines_Color_Picker;
    @FXML private ColorPicker CP_Grid_Inner_Lines_Color_Picker;

    @FXML private ColorPicker Menu_Bar_Color_Picker;
    @FXML private ColorPicker Menu_Item_Color_Picker;

    @FXML private ColorPicker Node_Label_Color_Picker;
    @FXML private ColorPicker Edge_Color_Picker;

    @FXML private ColorPicker Button_Color_Picker;
    @FXML private ColorPicker Button_Text_Color_Picker;

    @FXML private ColorPicker Text_Field_Label_Color_Picker;
    @FXML private ColorPicker Text_Field_Color_Picker;
    @FXML private ColorPicker Text_Field_Text_Color_Picker;

    //sample center pane
    @FXML private AnchorPane Sample_Center_Pane;

    //sample menu bar
    @FXML private MenuBar Sample_Menu_Bar;

    //sample tree
    @FXML private Label Sample_Parent_Entity;
    @FXML private Line Sample_Edge_1;
    @FXML private Line Sample_Edge_2;
    @FXML private Line Sample_Edge_3;
    @FXML private Label Sample_Child_Entity_1;
    @FXML private Label Sample_Child_Entity_2;
    @FXML private Label Sample_Child_Entity_3;

    //text field and buttons
    @FXML private Label Sample_TF_Label;
    @FXML private TextField Sample_Text_Field;
    @FXML private Button Sample_Button_1;
    @FXML private Button Sample_Button_2;
    @FXML private Button Sample_Button_3;

    @FXML private Button Save_Button;
    @FXML private Button Open_Button;
    @FXML private Button Cancel_Button;

    /**
     * sets the properties of color pickers as the preferences window starts
     */
    @FXML public void initialize() {
        setColorPickerActions();
        setButtonActions();
    }

    /**
     * sets the actions for save, choose and cancel button in preferences
     */
    private void setButtonActions() {
        Save_Button.setOnAction(event -> {
            try {
                saveThemeDialog();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Open_Button.setOnAction(this::loadingDialogue);
        Cancel_Button.setOnAction(event -> {
            Alert alert = getAlert("Return to main application without saving?", "");
            if (alert.getResult() == ButtonType.YES) {
                VisApplication.getInstance().changeToMain();
            } else if (alert.getResult() == ButtonType.NO) {
                try {
                    saveThemeDialog();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * dialogue for loading theme files
     *
     * @param event action event for getting stage window
     */
    private void loadingDialogue(ActionEvent event) {
        FileChooser fileChooser = fileChooserWithInitialProperties();
        Window mainStage = ((Node) event.getTarget()).getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(mainStage);
        if (selectedFile != null) {
            mainScene.getStylesheets().add("styles/" + selectedFile.getName());
            VisApplication.getInstance().changeToMain();
        }
    }

    /**
     * opens a file choosing dialog for saving a theme css file
     *
     * @throws IOException for bufferedWriter and creating new file
     */
    private void saveThemeDialog() throws IOException {
        TextInputDialog textInputDialog = getTextInputDialogue();
        Optional<String> text = textInputDialog.showAndWait();
        if (text.isPresent()) {
            String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
            File selectedFile = new File(currentPath + "/OrientDBVisualizationTool/src/main/resources/styles/" + text.get() + ".css");
            if (selectedFile.exists()) {
                Alert alert = getAlert("Replace already existing file?", "");
                if (alert.getResult() == ButtonType.YES) {
                    saveToFile(selectedFile);
                } else if (alert.getResult() == ButtonType.NO) {
                    text = textInputDialog.showAndWait();
                    if (text.isPresent()) {
                        selectedFile = new File(currentPath + "/OrientDBVisualizationTool/src/main/resources/styles/" + text.get() + ".css");
                        saveToFile(selectedFile);
                    }
                }
            } else {
                saveToFile(selectedFile);
            }
        }
    }

    /**
     * shows the save confirmation dialogue
     *
     * @param selectedFile file that is saved.
     */
    private void showSaveConfirmation(File selectedFile) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, selectedFile.getName() + " has been saved successfully.\n\n" +
                selectedFile.getPath() +
                "\n\nTheme file can be applied after the application is restarted.\n" +
                "Program will now return to the Main Tree View.", ButtonType.OK);
        confirm.setHeight(300);
        setStageIcon(confirm);
        confirm.setHeaderText("");
        confirm.setGraphic(getSBIcon());
        confirm.showAndWait();
        VisApplication.getInstance().changeToMain();
    }

    /**
     * gets alert dialog with given content text
     *
     * @param contentText displayed as content for alert
     * @param headerText  displayed as header text for alert
     * @return alert with contentText
     */
    private Alert getAlert(String contentText, String headerText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, contentText,
                ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        setStageIcon(alert);
        alert.setGraphic(getSBIcon());
        alert.setHeaderText(headerText);
        alert.showAndWait();
        return alert;
    }

    /**
     * sets the stage icon for an alert
     * @param alert which is needs an icon
     */
    private void setStageIcon(Dialog alert) {
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/icons/sb-icon.png"));
    }

    /**
     * gets the text input dialogue for saving
     *
     * @return text input dialogue for saving
     */
    private TextInputDialog getTextInputDialogue() {
        TextInputDialog textInputDialog = new TextInputDialog("Untitled_Theme");
        textInputDialog.setGraphic(getSBIcon());
        textInputDialog.setTitle("Save As");
        textInputDialog.setContentText("File name: ");
        textInputDialog.setHeaderText("Do you want to save changes to Untitled_Theme?");
        setStageIcon(textInputDialog);
        Stage stage = (Stage) textInputDialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/icons/sb-icon.png"));
        return textInputDialog;
    }

    /**
     * gets the sb icon with ImageView wrapper
     *
     * @return the SB-icon within image view
     */
    private ImageView getSBIcon() {
        ImageView imageView = new ImageView(new Image("/icons/sb-icon.png"));
        imageView.setFitHeight(60);
        imageView.setFitWidth(80);
        return imageView;
    }

    /**
     * fileChooser is created with a starting directory and title
     *
     * @return fileChooser
     */
    private FileChooser fileChooserWithInitialProperties() {
        FileChooser fileChooser = new FileChooser();
        String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
        File initialDirectory = new File(currentPath + "/OrientDBVisualizationTool/src/main/resources/styles");
        fileChooser.setTitle("Open Theme File");
        if (initialDirectory.exists()) {
            fileChooser.setInitialDirectory(initialDirectory);
        }
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSS Files", "*.css"));
        return fileChooser;
    }

    /**
     * writes to selected file
     *
     * @param selectedFile file to which contents are written in
     * @throws IOException for bufferedWriter
     */
    private void saveToFile(File selectedFile) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(selectedFile));
        bufferedWriter.write(getNewCssContent());
        bufferedWriter.close();
        showSaveConfirmation(selectedFile);
        VisApplication.getInstance().changeToMain();
    }

    /**
     * Updates the appropriate sample entities displayed in the preferences window
     * following the changes made in color pickers
     */
    private void setColorPickerActions() {
        CP_Background_Color_Picker.setOnAction(event -> Sample_Center_Pane.setStyle(getNewCenterPaneStyle()));
        CP_Grid_Outer_Lines_Color_Picker.setOnAction(event -> Sample_Center_Pane.setStyle(getNewCenterPaneStyle()));
        CP_Grid_Inner_Lines_Color_Picker.setOnAction(event -> Sample_Center_Pane.setStyle(getNewCenterPaneStyle()));
        Menu_Bar_Color_Picker.setOnAction(event -> Sample_Menu_Bar.setStyle(getNewMenuBarStyle()));
        Menu_Item_Color_Picker.setOnAction(event -> {
            for (Menu menu : Sample_Menu_Bar.getMenus()) {
                menu.setStyle(getNewMenuStyle());
                menu.getItems().get(0).setStyle(getNewMenuStyle());
            }
        });
        Node_Label_Color_Picker.setOnAction(event -> {
            Sample_Parent_Entity.setStyle(getNewNodeLabelStyle());
            Sample_Child_Entity_1.setStyle(getNewNodeLabelStyle());
            Sample_Child_Entity_2.setStyle(getNewNodeLabelStyle());
            Sample_Child_Entity_3.setStyle(getNewNodeLabelStyle());
        });
        Edge_Color_Picker.setOnAction(event -> {
            Sample_Edge_1.setStyle(getNewEdgeStyle());
            Sample_Edge_2.setStyle(getNewEdgeStyle());
            Sample_Edge_3.setStyle(getNewEdgeStyle());
        });
        Button_Color_Picker.setOnAction(event -> {
            Sample_Button_1.setStyle(getNewButtonStyle());
            Sample_Button_2.setStyle(getNewButtonStyle());
            Sample_Button_3.setStyle(getNewButtonStyle());
        });
        Button_Text_Color_Picker.setOnAction(event -> {
            Sample_Button_1.setStyle(getNewButtonStyle());
            Sample_Button_2.setStyle(getNewButtonStyle());
            Sample_Button_3.setStyle(getNewButtonStyle());
        });
        Text_Field_Label_Color_Picker.setOnAction(event -> Sample_TF_Label.setStyle(getNewTextFieldLabelStyle()));
        Text_Field_Color_Picker.setOnAction(event -> Sample_Text_Field.setStyle(getNewTextFieldStyle()));
        Text_Field_Text_Color_Picker.setOnAction(event -> Sample_Text_Field.setStyle(getNewTextFieldStyle()));
    }

    /**
     * converts color picker hex values to web RGB color values
     *
     * @param color colorPicker with hex value
     * @return rgb code in web format
     */
    private String toRGBCode(ColorPicker color) {
        Color color1 = color.getValue();
        return String.format("#%02X%02X%02X", (int) (color1.getRed() * 255), (int) (color1.getGreen() * 255),
                (int) (color1.getBlue() * 255));
    }

    /**
     * generates a css configuration for centerPane
     *
     * @return New css configuration for centerPane
     */
    private String getNewCenterPaneStyle() {
        return "    -fx-background-color: " + toRGBCode(CP_Background_Color_Picker) + ",\n" +
                "    linear-gradient(from 0px 0px to 20px 0px, repeat, " + toRGBCode(CP_Grid_Inner_Lines_Color_Picker) + " 0%, transparent 5%, transparent 95%, " + toRGBCode(CP_Grid_Inner_Lines_Color_Picker) + " 100% ),\n" +
                "    linear-gradient(from 0px 0px to 0px 20px, repeat, " + toRGBCode(CP_Grid_Inner_Lines_Color_Picker) + " 0%, transparent 5%, transparent 95%, " + toRGBCode(CP_Grid_Inner_Lines_Color_Picker) + " 100% ),\n" +
                "    linear-gradient(from 0px 0px to 100px 0px, repeat, " + toRGBCode(CP_Grid_Outer_Lines_Color_Picker) + " 0%, transparent 1%, transparent 99%, " + toRGBCode(CP_Grid_Outer_Lines_Color_Picker) + " 100% ),\n" +
                "    linear-gradient(from 0px 0px to 0px 100px, repeat, " + toRGBCode(CP_Grid_Outer_Lines_Color_Picker) + " 0%, transparent 1%, rgba(100, 100, 100, 0) 99%, " + toRGBCode(CP_Grid_Outer_Lines_Color_Picker) + " 100%);";
    }

    /**
     * generates a css configuration for MenuBar
     *
     * @return New css configuration for MenuBar
     */
    private String getNewMenuBarStyle() {
        return "    -fx-background-color: linear-gradient(#706e6a," + toRGBCode(Menu_Bar_Color_Picker) + ");\n";
    }

    /**
     * generates a css configuration for Menu
     *
     * @return New css configuration for Menu
     */
    private String getNewMenuStyle() {
        return "    -fx-background-color: linear-gradient(#706e6a," + toRGBCode(Menu_Item_Color_Picker) + ");";
    }

    /**
     * generates a css configuration for NodeLabel
     *
     * @return New css configuration for NodeLabel
     */
    private String getNewNodeLabelStyle() {
        return "    -fx-text-fill: " + toRGBCode(Node_Label_Color_Picker) + ";\n";
    }

    /**
     * generates a css configuration for Edge
     *
     * @return New css configuration for Edge
     */
    private String getNewEdgeStyle() {
        return "    -fx-stroke: " + toRGBCode(Edge_Color_Picker) + ";\n";
    }

    /**
     * generates a css configuration for Button
     *
     * @return New css configuration for Button
     */
    private String getNewButtonStyle() {
        return "    -fx-background-color: linear-gradient(#706e6a," + toRGBCode(Button_Color_Picker) + ");\n" +
                "    -fx-text-fill: " + toRGBCode(Button_Text_Color_Picker) + ";\n";
    }

    /**
     * generates a css configuration for TextFieldLabel
     *
     * @return New css configuration for TextFieldLabel
     */
    private String getNewTextFieldLabelStyle() {
        return "    -fx-text-fill: " + toRGBCode(Text_Field_Label_Color_Picker) + ";\n";
    }

    /**
     * generates a css configuration for TextField
     *
     * @return New css configuration for TextField
     */
    private String getNewTextFieldStyle() {
        return "    -fx-text-fill: " + toRGBCode(Text_Field_Text_Color_Picker) + ";\n" +
                "    -fx-background-color: linear-gradient(#706e6a," + toRGBCode(Text_Field_Color_Picker) + ");\n";
    }

    /**
     * generates a css configuration for Root
     *
     * @return New css configuration for Root
     */
    private String getNewRootStyle() {
        return "    -fx-base: " + toRGBCode(Base_Color_Picker) + ";\n" +
                "    -fx-accent: " + toRGBCode(Accent_Color_Picker) + ";\n" +
                "    -fx-default-button: " + toRGBCode(Button_Color_Picker) + ";\n" +
                "    -fx-focus-color: " + toRGBCode(Focus_Color_Picker) + ";\n" +
                "    -fx-faint-focus-color: " + toRGBCode(Faint_Focus_Color_Picker) + ";\n" +
                "    -fx-focused-text-base-color : ladder(\n" +
                "            -fx-selection-bar,\n" +
                "            -fx-light-text-color 45%,\n" +
                "            -fx-dark-text-color 46%,\n" +
                "            -fx-dark-text-color 59%,\n" +
                "            -fx-mid-text-color 60%\n" +
                "    );\n" +
                "    -fx-focused-mark-color : -fx-focused-text-base-color;\n";
    }

    /**
     * generates a css configuration from all css components
     *
     * @return New css configuration
     */
    private String getNewCssContent() {
        String content = ".root {\n" + getNewRootStyle() + "}\n\n" +
                ".text-input:focused {\n" +
                "    -fx-highlight-text-fill: ladder(\n" +
                "            -fx-highlight-fill,\n" +
                "            -fx-light-text-color 45%,\n" +
                "            -fx-dark-text-color  46%,\n" +
                "            -fx-dark-text-color  59%,\n" +
                "            -fx-mid-text-color   60%\n" +
                "    );\n" +
                "}\n\n" +
                "#Center_Anchor_Pane{\n" + getNewCenterPaneStyle() + "}\n\n" +
                ".button {\n" + getNewButtonStyle() + "}\n\n" +
                ".label {\n" + getNewTextFieldLabelStyle() + "}\n\n" +
                ".menu-bar {\n" + getNewMenuBarStyle() + "}\n\n" +
                ".menu {\n" + getNewMenuStyle() + "}\n\n" +
                ".menu-item {\n" + getNewMenuStyle() + "}\n\n" +
                ".text-field {\n" + getNewTextFieldStyle() + "}\n\n" +
                "#NodeRectangle{\n" +
                "    -fx-fill: lightgray;\n" +
                "    -fx-stroke: darkgray;\n" +
                "}\n\n" +
                "#NodeLabel {\n" + getNewNodeLabelStyle() + "}\n\n" +
                "#Edge{\n" + getNewEdgeStyle() + "}";
        return content;
    }

    /**
     * sets the main application scene for applying the theme
     *
     * @param scene used for applying the theme
     */
    public void setApplicationScene(Scene scene) {
        this.mainScene = scene;
    }
}
