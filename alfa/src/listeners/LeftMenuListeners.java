package listeners;

import db_logic.DBAction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Koropenkods on 05.02.16.
 */
public class LeftMenuListeners implements ActionListener {

    private DefaultListModel listModel;
    private JList container;
    private JButton button;
    private DBAction dataBase;
    private final String VAR = ">";

    public LeftMenuListeners(DefaultListModel dataList, JList container){
        this.listModel = dataList;
        this.container = container;
        dataBase = new DBAction();
    }

    private void addElement(){
        String elemName = "";
        while (elemName.equals(""))
            elemName = JOptionPane.showInputDialog("Введи имя нового элемента");

        if (!elemName.contains(VAR)){
            dataBase.addValue(elemName);
            refresh();
        }
    }

    private void delElement(){
        if (container.getSelectedIndex() != -1){
            int options = JOptionPane.showConfirmDialog(container,"Вы уверены?");
            if (options == JOptionPane.OK_OPTION){
                int index = container.getSelectedIndex();
                dataBase.remove(index);
                refresh();
            }
        }
    }

    private void modifyElemenet(){
        if (!container.isSelectionEmpty()){

            String elemName = "";
            while (elemName.equals("")){
                elemName = JOptionPane.showInputDialog("Введи имя нового элемента");

                if (elemName == null) return;
            }

            if(elemName != null || !elemName.equals("") || !elemName.contains(VAR)){
                int index = container.getSelectedIndex();
                dataBase.renameElement(index,elemName);
                refresh();
                container.setSelectedIndex(index);
            }
        }
    }

    private void refresh(){
        ArrayList<String> elements = dataBase.getValues();

        if (elements != null){
            listModel.clear();
            for (String result: elements) {
                listModel.addElement(result);
                container.setSelectedIndex(listModel.size()-1);
            }
        }
        else
            listModel.clear();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        button = (JButton) e.getSource();
        switch (button.getName()){
            case "add":
                addElement();
                break;
            case "delete":
                delElement();
                break;
            case "mod":
                modifyElemenet();
                break;
        }
    }
}
