package listeners;

import db_logic.mainDBAction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Koropenkods on 08.02.16.
 */
public class DataMenuListeners extends Listener implements ActionListener {

    JTable table;
    DefaultTableModel tableModel;
    JList list;
    DefaultListModel listModel;
    JButton button;

    public DataMenuListeners(JTable table, DefaultTableModel model, DefaultListModel listModel, JList list){
        super (list,listModel,table,model);
        this.table = table;
        this.tableModel = model;
        this.list = list;
        this.listModel = listModel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        button = (JButton) e.getSource();

        switch (button.getName()){
            case "add":
                addData();
                break;
            case "del":
                delData();
                break;
            case "mod":
                modData();
                break;
            case "complete":
                complete();
                break;
        }

    }
}
