package Gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Nick on 07.10.2015.
 */
public class ToDoList extends Window {

    private String[] columnName;
    private String[][] data;
    private JTable table;
    private JScrollPane container;

    public GridBagConstraints setButtonsOptions(){
        GridBagConstraints butOptions = new GridBagConstraints();
        butOptions.anchor = GridBagConstraints.WEST;
        butOptions.fill   = GridBagConstraints.NONE;
        butOptions.gridheight = 1;
        butOptions.gridwidth  = 1;
        butOptions.gridx = 0;
        butOptions.gridy = 0;
        butOptions.insets = new Insets(2, 3, 3, 2);
        butOptions.ipadx = 0;
        butOptions.ipady = 0;
        butOptions.weightx = 1.0;
        butOptions.weighty = 0.0;

        return butOptions;
    }

    public GridBagConstraints setListOptions(){
        GridBagConstraints listOptions = new GridBagConstraints();
        listOptions.anchor = GridBagConstraints.WEST;
        listOptions.fill   = GridBagConstraints.BOTH;
        listOptions.gridheight = 1;
        listOptions.gridwidth  = 1;
        listOptions.gridx = 0;
        listOptions.gridy = 1;
        listOptions.insets = new Insets(2, 3, 3, 2);
        listOptions.ipadx = 0;
        listOptions.ipady = 0;
        listOptions.weightx = 1.0;
        listOptions.weighty = 1.0;

        return listOptions;
    }

    public JScrollPane createList(){
        columnName = new String[]{"Date", "ToDo", "Status"};

        data = new String[][]{
                {"01.01.01", "To-Do somethink", "Complite"},
                {"02.01.01", "To-Do somethink", "Complite"},
                {"02.01.01", "To-Do somethink", "In Process"},
        };

        table = new JTable(data, columnName);
        container = new JScrollPane(table);

        return container;
    }

}
