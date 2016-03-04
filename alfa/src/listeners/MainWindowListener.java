package listeners;

import db_logic.DataBaseClass;
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

    DataBaseClass database;

    private JList masterList;

    public MainWindowListener(JList masterList){
        this.masterList = masterList;
    }

    private void addMasterDataToDB(){
        try {
            String name = (String)JOptionPane.showInputDialog(null, "Введите значение", "Добавить", 0, Constants.ADD, null, null);

            if (name != null && !name.equals("")){
                database = DataBaseClass.getInstance();
                database.connect();
                database.add("Master", database.getSize("Master")+1, name, 0, database.currentUser);
                refreshWinElements();
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
    }

    private void delMasterDataFromTable(){
        try {
            int name = JOptionPane.showConfirmDialog(null,"Вы уверены? При удалении объекта" +
                    " удалятся и все записи этого объекта", "Адаление", 0, 0, Constants.DEL);

            if (name == JOptionPane.OK_OPTION){
                database = DataBaseClass.getInstance();
                database.connect();
                database.delete("Master", (String)masterList.getSelectedValue());

                //Надо сделать определение элемента в таблице.
                for (int i = 0; i < database.getSize("Master"); i++) {

                }

                refreshWinElements();
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
    }

    private void changeMasterDataToDB(){

    }

    private void refreshWinElements(){
        try {
            database = DataBaseClass.getInstance();
            database.connect();

            ArrayList<String> tasks = database.getFromMaster("name", database.currentUser);

            masterList.setListData(tasks.toArray());
            masterList.setSelectedIndex(0);
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

        switch (button.getName()){
            case "MasterAdd":
                addMasterDataToDB();
                break;
            case "MasterDel":
                delMasterDataFromTable();
                break;
            case "MasterOptions":
                System.out.println("MasterOptions");
                break;
            case "TaskAdd":
                System.out.println("TaskAdd");
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
