package listeners;

import db_logic.DataBaseClass;
import gui.Constants;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Koropenkods on 11.03.16.
 */
public class UserListener implements ActionListener {

    private JList listOfUsers;
    private DataBaseClass database = null;

    JTextField login;
    JPasswordField passwd;
    int selectedUser;

    public UserListener(JList listOfUsers){
        this.listOfUsers = listOfUsers;
    }

    private int createDialog(String[] options, String title){
        login = new JTextField(10);
        login.setBorder(BorderFactory.createTitledBorder(new EtchedBorder(), "Пользователь"));

        passwd = new JPasswordField(10);
        passwd.setBorder(BorderFactory.createTitledBorder(new EtchedBorder(), "Пароль"));

        JPanel createUserPanel = new JPanel();
        createUserPanel.setLayout(new FlowLayout());
        createUserPanel.add(login);
        createUserPanel.add(passwd);

        int status = 1;
        return JOptionPane.showOptionDialog(null,createUserPanel,title, JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,Constants.ADD,options,status);
    }

    private void addUser(){
        try {
            String[] options = {"Создать", "Отмена"};
            int status = createDialog(options, "Создать");

            if (status == 0 && !login.getText().equals("")){
                database = DataBaseClass.getInstance();
                database.connect();

                ArrayList<String> checkedNameList = database.getFromUsers("name");

                if (!checkedNameList.contains(login.getText())){
                    database.add("Users", login.getText(), String.copyValueOf(passwd.getPassword()));
                    selectedUser = database.getSize("Users", null,null)-1;
                    refreshUsers();
                }
                else
                    JOptionPane.showMessageDialog(null,"Данное имя уже присутствует в списке.", "Неудачно", 0, Constants.FAIL);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (database != null & !database.databaseIsClosed())
                    database.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    private void deleteUser(){
        try {
            database = DataBaseClass.getInstance();
            database.connect();

            if (!database.currentUser.equals((String)listOfUsers.getSelectedValue())){
                database.clearDBFromUser((String)listOfUsers.getSelectedValue());
                if (selectedUser != 0)
                    selectedUser--;
                refreshUsers();
            }
            else
                JOptionPane.showMessageDialog(null,"Извините, но вы вошли под этим пользователем. \n" +
                    "Перезайдите под другим и попробуйте снова", "Ошибка удаления", JOptionPane.ERROR_MESSAGE, Constants.FAIL);
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
    private void changeUser(){
        try {
            String[] options = {"Изменить", "Отмена"};
           // login.setText((String)listOfUsers.getSelectedValue());
            int status = createDialog(options, "Изменить");

            if (status == 0 && !login.getText().equals("")){
                database = DataBaseClass.getInstance();
                database.connect();

                ArrayList<String> checkedNameList = database.getFromUsers("name");

                if (!checkedNameList.equals(login.getText()) && !checkedNameList.contains(login.getText())){
                    database.changeUserInformation(login.getText(),(String)listOfUsers.getSelectedValue(),String.copyValueOf(passwd.getPassword()));
                    database.changeUserInDB(login.getText(), (String)listOfUsers.getSelectedValue());
                    database.currentUser = login.getText();
                    refreshUsers();
                }
                else
                    JOptionPane.showMessageDialog(null,"Данное имя уже присутствует в списке.", "Неудачно", 0, Constants.FAIL);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (database != null & !database.databaseIsClosed())
                    database.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void refreshUsers(){
        try {
            database = DataBaseClass.getInstance();
            database.connect();

            ArrayList<String> userName = database.getFromUsers("name");

            String[] users = new String[userName.size()];

            for (int i = 0; i < userName.size() ; i++) {
                users[i] = userName.get(i);
            }
            listOfUsers.setListData(users);
            listOfUsers.setSelectedIndex(selectedUser);

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

        JButton button = (JButton)e.getSource();
        selectedUser = listOfUsers.getSelectedIndex();

        switch(button.getName()){
            case "addUser":
                addUser();
                break;
            case "deleteUser":
                if (listOfUsers.getMaxSelectionIndex() > 0)
                    deleteUser();
                break;
            case "changeUser":
                changeUser();
                break;

        }

    }
}
