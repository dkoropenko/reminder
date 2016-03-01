package listeners;

import db_logic.DBAction;
import db_logic.mainDBAction;
import gui.Constants;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Koropenkods on 05.02.16.
 */
public class LeftMenuListeners extends Listener implements ActionListener {

    private DefaultListModel listModel;
    private JList list;
    private JButton button;
    private DBAction dataBase;
    private int elementIndex;
    private DBAction database;

    public LeftMenuListeners(JList list, DefaultListModel listModel, JTable table, DefaultTableModel tableModel){
        super(list, listModel, table,tableModel);
        this.listModel = listModel;
        this.list = list;
        this.dataBase = DBAction.getInstance();
    }

    //Добавление элемента в БД
    public void addData(){
        String elemName = "";

        //Выводим запрос на ввод имени элемента
        do elemName = JOptionPane.showInputDialog("Введите имя нового элемента");
        while (checkLetters(elemName));

        if (elemName != null){
            dataBase.addValue(elemName);
            elementIndex = listModel.getSize();

            refreshLeft();
            list.setSelectedIndex(listModel.size()-1);
            refresh();
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
                System.out.println(elementIndex);
                //И записываем имя этого элемента для удаления файла БД.
                String elementName = dataBase.getValues().get(elementIndex);

                //Удаляем значение из базы данных
                dataBase.remove(elementIndex);

                //Открываем файл для удаления
                String patch = "DataBase/"+ elementName +".db";
                try {
                    Files.delete(FileSystems.getDefault().getPath(patch));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //Обновляем окно
                refreshLeft();
                if (elementIndex > 1) list.setSelectedIndex(elementIndex-1);
                else list.setSelectedIndex(0);
                refresh();
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

            //Выводим запрос на ввод имени элемента
            do nameDB = JOptionPane.showInputDialog("Введите имя нового элемента");
            while (checkLetters(nameDB));

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
            list.setSelectedIndex(listModel.size()-1);
            refresh();
        }
    }

    public void refreshLeft(){
        ArrayList<String> elements = dataBase.getValues();

        if (elements != null){
            listModel.clear();
            for (String result: elements) {
                listModel.addElement(result);
            }
        }
        else
            listModel.clear();
    }

    private boolean checkLetters(String elemName){
        boolean result = false;

        ArrayList<String> elements = dataBase.getValues();

        //Если пользователь не нажал Cancel или оставил поле пустым
        if(elemName != null){
            if (!elemName.isEmpty()) {

                //Проверяем на воод запрещенных знаков.
                for (int i = 0; i < Constants.LETTERS.length; i++) {
                    if (elemName.toLowerCase().indexOf(Constants.LETTERS[i]) != -1) result = true;
                    if (elemName.toLowerCase().equals("database")) result = true;
                }
                //Проверяем на повторение элементов в списке.
                if (elements != null){
                    for (int i = 0; i < elements.size(); i++) {
                        if (elements.get(i).toLowerCase().equals(elemName)) result = true;
                    }
                }
            }
            else result = true;
        }

        return result;
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
