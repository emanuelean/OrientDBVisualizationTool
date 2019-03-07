package com.mycompany.orientdbvisualizationtool.controller;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.AbstractAction;
import javax.swing.JButton;

/**
 *
 * @author Niels
 */
public class MenuButton extends JButton {

    /**
     * sets the button properties for the current button
     *
     * @param name the name for the tooltip
     */
    private void setButtonProperties(String name) {
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setToolTipText(name);
        setPreferredSize(new Dimension(200, 40));
    }

    /**
     * constructor that connect the action to the current button
     *
     * @param name name of the button tooltip
     * @param action action we want to link to the button
     */
    public MenuButton(String name, AbstractAction action) {
        super(action);
        setButtonProperties(name);
    }
}
