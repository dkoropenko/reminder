package listeners;

import db_logic.mainDBAction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Koropenkods on 08.02.16.
 */
public class DataMenuListeners implements ActionListener {

    JTable table;
    DefaultTableModel tableModel;
    JList list;
    DefaultListModel listModel;
    JButton button;

    mainDBAction database;
    String dataBaseName;

    public DataMenuListeners(JTable table, /*DefaultTableModel model,*/ DefaultListModel listModel, JList list){
        this.table = table;
        //this.tableModel = model;
        this.list = list;
        this.listModel = listModel;
    }

    private void addData(){
        dataBaseName = (String)listModel.getElementAt(list.getSelectedIndex());

        /*
        Сделать окно для ввода данных.
        1. Что сделать.
        2. Системное время когда была создана тема.
        3. Записать в файл данных
        4. Обновить таблицу.
         */
    }

    private void delData(){

    }

    private void modData(){

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
        }

    }
}
