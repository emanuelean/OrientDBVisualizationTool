package com.mycompany.orientdbvisualizationtool;

import com.mycompany.orientdbvisualizationtool.View.MainFrame;
import com.mycompany.orientdbvisualizationtool.database.Test;
import javafx.application.Application;

/**
 *
 * @author Niels
 */
public class VisTool {
    public static void main(String[] args) {
        Test test = new Test();
        test.mainTestDB(args);
        Application.launch(MainFrame.class, args);
    }
}
