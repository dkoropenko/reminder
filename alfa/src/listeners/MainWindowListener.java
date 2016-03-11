package listeners;

import db_logic.DataBaseClass;
import gui.AddTaskGUI;
import gui.Constants;
import gui.UpdateTaskGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
    private DefaultTableModel tableModel;
    private JTable taskTable;

    private int selectedMasterElement;
    private int selectedTaskRow;

    AddTaskGUI addTaskGUI;
    UpdateTaskGUI updateTaskGUI;

    String taskTitle, taskBody;

    public MainWindowListener(JList masterList, DefaultTableModel tableModel, JTable taskTable){
        addTaskGUI = new AddTaskGUI(this);
        updateTaskGUI = new UpdateTaskGUI(this);
        this.masterList = masterList;
        this.tableModel = tableModel;
        this.taskTable = taskTable;
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
                    selectedMasterElement = database.getSize("Master", database.currentUser, null) - 1;
                    selectedTaskRow = 0;
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
                    " удалятся и все записи этого объекта", "Удаление", 0, 0, Constants.DEL);

            if (name == JOptionPane.OK_OPTION){
                database = DataBaseClass.getInstance();
                database.connect();
<<<<<<< HEAD
                database.deleteFromMaster((String)masterList.getSelectedValue(), database.currentUser);
                database.clearTask(database.currentUser, (String)masterList.getSelectedValue());
=======
                database.delete("Master", (String) masterList.getSelectedValue());
>>>>>>> 79d10337a3fa20ee52f4d4c2ec37ac9d773b4fad

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
                database.change("Master", name, (String)masterList.getSelectedValue());
                database.change("Tasks", name, (String)masterList.getSelectedValue());
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
        String selectedMaster = (String)masterList.getSelectedValue();
        Long createTime;

        if (!taskTitle.equals("")){
            try {
                database = DataBaseClass.getInstance();
                database.connect();

<<<<<<< HEAD
                ArrayList<String> checkName = database.getFromTasks("title", database.currentUser, selectedMaster);

                if (!checkName.contains(taskTitle)){
                    //Дата создания задания
                    createTime = System.currentTimeMillis()/1000;

                    database.add("Tasks", taskTitle, taskBody, createTime,0,0,0, database.currentUser, selectedMaster);
                    selectedTaskRow = database.getSize("Tasks", database.currentUser, (String)masterList.getSelectedValue())-1;

                    addTaskGUI.clearData();
                }else{
                    JOptionPane.showMessageDialog(null,"Данное задача уже присутствует в списке.\n" +
                            "Измените тему и повторите заново", "Неудачно", 0, Constants.FAIL);
                }
=======
                //Дата создания задания
                createTime = System.currentTimeMillis()/1000;

                database.add("Tasks", taskTitle, taskBody, createTime,0,0,0, database.currentUser, selectedMaster);
                selectedTaskRow = database.getSize("Tasks")-1;
>>>>>>> 79d10337a3fa20ee52f4d4c2ec37ac9d773b4fad

            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                try {
                    if (!database.databaseIsClosed()){
                        database.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        refreshElements();
<<<<<<< HEAD
    }
    private void deleteTaskDataFromDB(){
        String title = String.valueOf(tableModel.getValueAt(selectedTaskRow,1));

        try{
            int name = JOptionPane.showConfirmDialog(null,"Вы уверены? \nПри удалении объекта" +
                    " вы не сможете его восстановить", "Удаление", 0, 0, Constants.DEL);

            if (name == JOptionPane.OK_OPTION){
                database = DataBaseClass.getInstance();
                database.connect();
                System.out.println(title);
                database.deleteFromTasks(title,database.currentUser,(String)masterList.getSelectedValue());

                if(selectedTaskRow != 0)
                    selectedTaskRow--;
            }
        }catch (SQLException e){
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
    private void changeTaskDataToDB(){
        String title = updateTaskGUI.getTitle();
        String oldTitle = (String)tableModel.getValueAt(taskTable.getSelectedRow(),1);
        String body = updateTaskGUI.getBody();
        String selectedMaster = (String)masterList.getSelectedValue();

        if (!title.equals("")){
            //Сделать 2 ветвления для орагнизации изменения
            //титла и боди.

            //И закоментируй наконец свою работу. Задудешь же
            // что делал...
        }

        System.out.println("new Title: "+ title);
        System.out.println("new Body: "+ body);

        System.out.println("old Title: "+ oldTitle);
        System.out.println("Selection master: "+ selectedMaster);

//
//        try {
//            database = DataBaseClass.getInstance();
//            database.connect();
//
//            String oldTitle = database.getFromTasks()
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

=======
>>>>>>> 79d10337a3fa20ee52f4d4c2ec37ac9d773b4fad
    }

    private void refreshElements(){
        try {
            database = DataBaseClass.getInstance();
            database.connect();

            ArrayList<String> masterElements = database.getFromMaster("name", database.currentUser);
            masterList.setListData(masterElements.toArray());
            masterList.setSelectedIndex(selectedMasterElement);

            ArrayList<String> title = database.getFromTasks("title", database.currentUser, (String)masterList.getSelectedValue());
            ArrayList<String> status = database.getFromTasks("status", database.currentUser, (String)masterList.getSelectedValue());

            Object[][] rowData = new Object[title.size()][];

            for (int i = 0; i < title.size(); i++) {
                Object[] rowLine = new Object[3];

                rowLine[0] = i+1;
                rowLine[1] = title.get(i);
<<<<<<< HEAD
                rowLine[2] = Constants.TASK_STATUS[Integer.parseInt(status.get(i))];
=======
                rowLine[2] = status.get(i);
>>>>>>> 79d10337a3fa20ee52f4d4c2ec37ac9d773b4fad

                rowData[i] = rowLine;
            }
            Object[] head = {Constants.ID, Constants.TASK, Constants.STATUS};

            //Заролняем таблицу
            tableModel.setDataVector(rowData,head);

<<<<<<< HEAD
            //Выделяем cтрочку в таблице
            if (tableModel.getRowCount() > 0)
                taskTable.setRowSelectionInterval(0,selectedTaskRow);
=======
            //Выделяем первую строчку в таблице
            if (tableModel.getRowCount() > 0){
                System.out.println("Больше 0");
                taskTable.setRowSelectionInterval(0,selectedTaskRow);
            }
>>>>>>> 79d10337a3fa20ee52f4d4c2ec37ac9d773b4fad

            //А так же размеры столбцов
            taskTable.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
            taskTable.getColumnModel().getColumn(0).setMaxWidth(30);
            taskTable.getColumnModel().getColumn(1).setMaxWidth(1000);
            taskTable.getColumnModel().getColumn(2).setMaxWidth(70);



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
        selectedTaskRow = taskTable.getSelectedRow();

        if (taskTable.getSelectedRow() != -1)
            selectedTaskRow = taskTable.getSelectedRow();

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
                deleteTaskDataFromDB();
                break;
            case "TaskOptions":
                updateTaskGUI.setVisible(true);
                break;
            case "updateTask":
                updateTaskGUI.setVisible(false);
                changeTaskDataToDB();
                break;
            case "TaskComplete":
                System.out.println("TaskComplete");
                break;
        }

    }
}
