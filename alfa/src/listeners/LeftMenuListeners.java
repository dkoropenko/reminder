package listeners;

import db_logic.DBAction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Created by Koropenkods on 05.02.16.
 */
public class LeftMenuListeners extends Listener implements ActionListener {

    private DefaultListModel listModel;
    private JList list;
    private JButton button;
    private DBAction dataBase;
    private final String VAR = ">";

    public LeftMenuListeners(JList list, DefaultListModel listModel, JTable table, DefaultTableModel tableModel){
        super(list, listModel, table,tableModel);
        this.listModel = listModel;
        this.list = list;
        dataBase = new DBAction();
    }

    public void addData(){
        String elemName = "";
        while (elemName.equals(""))
            elemName = JOptionPane.showInputDialog("Введи имя нового элемента");

        if (!elemName.contains(VAR)){
            dataBase.addValue(elemName);
            refreshLeft();
        }
    }
    public void delData(){
        if (list.getSelectedIndex() != -1){
            int options = JOptionPane.showConfirmDialog(list,"Вы уверены?");
            if (options == JOptionPane.OK_OPTION){
                int index = list.getSelectedIndex();

                //Открываем файл для удаления
                //Короче это не работает
                //Да и хер с ним.
                //Потом сделаю в другом треде.
//                String file = "DataBase/"+ dataBase.getValues().get(index) +"DB";
//                try {
//                    Files.delete(FileSystems.getDefault().getPath(file));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

                //Удаляем значение из базы данных
                dataBase.remove(index);

                //Обновляем окно
                refreshLeft();
            }
        }


    }
    public void modData(){
        if (!list.isSelectionEmpty()){

            String elemName = "";
            while (elemName.equals("")){
                elemName = JOptionPane.showInputDialog("Введи имя нового элемента");

                if (elemName == null) return;
            }

            if(elemName != null || !elemName.equals("") || !elemName.contains(VAR)){
                int index = list.getSelectedIndex();
                dataBase.renameElement(index,elemName);
                refreshLeft();
                list.setSelectedIndex(index);
            }
        }
    }

    public void refreshLeft(){
        ArrayList<String> elements = dataBase.getValues();

        if (elements != null){
            listModel.clear();
            for (String result: elements) {
                listModel.addElement(result);
                list.setSelectedIndex(listModel.size()-1);
            }
        }
        else
            listModel.clear();

        refresh();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        button = (JButton) e.getSource();
        switch (button.getName()){
            case "add":
                addData();
                break;
            case "delete":
                delData();
                break;
            case "mod":
                modData();
                break;
        }
    }
}
