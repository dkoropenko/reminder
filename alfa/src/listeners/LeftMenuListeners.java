package listeners;

import db_logic.DBAction;
import db_logic.mainDBAction;

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
    private int elementIndex;

    public LeftMenuListeners(JList list, DefaultListModel listModel, JTable table, DefaultTableModel tableModel){
        super(list, listModel, table,tableModel);
        this.listModel = listModel;
        this.list = list;
        dataBase = new DBAction();
    }

    public void addData(){
        String elemName = "";
        boolean check = true;

        while (check) {
            check = false;
            elemName = JOptionPane.showInputDialog("Введите имя нового элемента");

            if (elemName != null && !elemName.isEmpty()) {
                for (int i = 0; i < Listener.LETTERS.length; i++) {
                    if (elemName.equals(Listener.LETTERS[i])) check = true;
                }
            }
        }

        if (elemName != null){

            dataBase.addValue(elemName);
            elementIndex = listModel.getSize();
            refreshLeft();
        }
    }
    public void delData(){
        //Если выбран какой-либо элемент
        if (list.getSelectedIndex() != -1){
            //Выводим сообщение о подтвержении.
            int options = JOptionPane.showConfirmDialog(list,"Вы уверены?");
            if (options == JOptionPane.OK_OPTION){
                //Берем индекс выбранного элемента.
                elementIndex = list.getSelectedIndex();
                //И записываем имя этого элемента для удаления файла БД.
                String elementName = dataBase.getValues().get(elementIndex);

                //Удаляем значение из базы данных
                dataBase.remove(elementIndex);

                //Открываем файл для удаления
                String patch = "DataBase/"+ elementName +"DB";
                try {
                    Files.delete(FileSystems.getDefault().getPath(patch));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //Обновляем окно
                refreshLeft();
            }
        }


    }
    public void modData(){

        String nameDB = null, oldName;
        ArrayList<String> data = null;
        ArrayList<Long> time = null;
        ArrayList<Integer> status = null;

        if (!list.isSelectionEmpty()){
            elementIndex = list.getSelectedIndex();
            oldName = (String) listModel.getElementAt(elementIndex);

            mainDBAction mainDB = new mainDBAction(oldName);

            if (mainDB.getContent() != null){
                data = mainDB.getContent();
                time = mainDB.getTime();
                status = mainDB.getStatus();
            }

            delData();

            boolean check = true;
            while (check) {
                check = false;
                nameDB = JOptionPane.showInputDialog("Введите имя нового элемента");

                if (nameDB != null && !nameDB.isEmpty()) {
                    for (int i = 0; i < Listener.LETTERS.length; i++) {
                        System.out.println(nameDB + " " + Listener.LETTERS[i] + " = " + nameDB.equals(Listener.LETTERS[i]));
                        if (nameDB.equals(Listener.LETTERS[i])) check = true;
                    }
                }
            }

            if (nameDB == null){
                nameDB = oldName;
            }

            dataBase.addValue(nameDB);

            mainDB.changeDBName(nameDB);
            if(data != null)
                for (int i = 0; i < data.size(); i++) {
                    mainDB.addValues(data.get(i),time.get(i),status.get(i));
                }
            refreshLeft();
        }
    }

    public void refreshLeft(){
        ArrayList<String> elements = dataBase.getValues();

        if (elements != null){
            listModel.clear();
            for (String result: elements) {
                listModel.addElement(result);
                if (listModel.size() > 0)
                    list.setSelectedIndex(elementIndex);
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
