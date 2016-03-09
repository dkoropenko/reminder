package listeners;

import db_logic.DataBaseClass;
import gui.AddTaskGUI;
import gui.Constants;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Koropenkods on 04.03.16.
 */
public class MainWindowListener implements ActionListener{

    private DataBaseClass database;

    private JList masterList;
    private int selectedMasterElement;

    AddTaskGUI addTaskGUI;

    String taskTitle, taskBody;

    public MainWindowListener(JList masterList){
        addTaskGUI = new AddTaskGUI(this);
        this.masterList = masterList;
    }

    private void addMasterDataToDB(){
        try {
            String name = (String)JOptionPane.showInputDialog(null, "Введите значение", "Добавить", 0, Constants.ADD, null, null);

            if (name != null && !name.equals("")){
                database = DataBaseClass.getInstance();
                database.connect();

                ArrayList<String> checkedNameList = database.getFromMaster("name", database.currentUser);

                if (!checkedNameList.contains(name)){
                    database.add("Master", name, 0, database.currentUser);
                    selectedMasterElement = database.getSize("Master") - 1;
                }
                else
                    JOptionPane.showMessageDialog(null,"Данное имя уже присутствует в списке.", "Неудачно", 0, Constants.FAIL);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (!database.databaseIsClosed())
                    database.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        refreshElements();
    }
    private void delMasterDataFromTable(){
        try {
            int name = JOptionPane.showConfirmDialog(null,"Вы уверены? \nПри удалении объекта" +
                    " удалятся и все записи этого объекта", "Адаление", 0, 0, Constants.DEL);

            if (name == JOptionPane.OK_OPTION){
                database = DataBaseClass.getInstance();
                database.connect();
                database.delete("Master", (String)masterList.getSelectedValue());

                if(selectedMasterElement != 0)
                    selectedMasterElement--;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (!database.databaseIsClosed())
                    database.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        refreshElements();
    }
    private void changeMasterDataToDB(){
        try {
            String name = (String)JOptionPane.showInputDialog(null, "Введите новое значение", "Изменить", 0, Constants.ADD, null, null);

            if (name != null && !name.equals("")){
                database = DataBaseClass.getInstance();
                database.connect();
                database.change("Master", 0, name, (String)masterList.getSelectedValue());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (!database.databaseIsClosed())
                    database.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        refreshElements();
    }

    private void addTasksDataToDB(){
        taskTitle = addTaskGUI.getTitle();
        taskBody = addTaskGUI.getBody();

        if (!taskTitle.equals("")){
            //Дальше сделать:
            //Добавление записи в таблицу Tasks
            //Удаление записи из таблицы Tasks
            //И другие классные штуки с таблицей Tasks
        }
    }

    private void refreshElements(){
        try {
            database = DataBaseClass.getInstance();
            database.connect();

            ArrayList<String> tasks = database.getFromMaster("name", database.currentUser);

            masterList.setListData(tasks.toArray());
            masterList.setSelectedIndex(selectedMasterElement);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (!database.databaseIsClosed())
                    database.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        selectedMasterElement = masterList.getSelectedIndex();

        switch (button.getName()){
            case "MasterAdd":
                addMasterDataToDB();
                break;
            case "MasterDel":
                if (masterList.getSelectedIndex() != -1)
                    delMasterDataFromTable();
                break;
            case "MasterOptions":
                if (masterList.getSelectedIndex() != -1)
                    changeMasterDataToDB();
                break;
            case "TaskAdd":
                addTaskGUI.setVisible(true);
                break;
            case "newTask":
                addTaskGUI.setVisible(false);
                addTasksDataToDB();
                break;
            case "TaskDel":
                System.out.println("TaskDel");
                break;
            case "TaskOptions":
                System.out.println("TaskOptions");
                break;
            case "TaskComplete":
                System.out.println("TaskComplete");
                break;
        }

    }
}
