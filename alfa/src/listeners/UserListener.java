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

    public UserListener(JList listOfUsers){
        this.listOfUsers = listOfUsers;
    }

    private int createDialog(String[] options){
        login = new JTextField(10);
        login.setBorder(BorderFactory.createTitledBorder(new EtchedBorder(), "Пользователь"));

        passwd = new JPasswordField(10);
        passwd.setBorder(BorderFactory.createTitledBorder(new EtchedBorder(), "Пароль"));

        JPanel createUserPanel = new JPanel();
        createUserPanel.setLayout(new FlowLayout());
        createUserPanel.add(login);
        createUserPanel.add(passwd);

        int status = 1;
        return JOptionPane.showOptionDialog(null,createUserPanel,"Создать", JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,Constants.ADD,options,status);
    }

    private void addUser(){
        try {
            String[] options = {"Создать", "Отмена"};
            int status = 1;
            status = createDialog(options);

            if (status == 0 && !login.getText().equals("")){
                database = DataBaseClass.getInstance();
                database.connect();

                ArrayList<String> checkedNameList = database.getFromUsers("name");

                if (!checkedNameList.contains(login.getText())){
                    database.add("Users", login.getText(), String.copyValueOf(passwd.getPassword()));
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
        int selectedUser = listOfUsers.getSelectedIndex();
        try {
            database = DataBaseClass.getInstance();
            database.connect();

            if (selectedUser != -1){
                database.deleteFromUsers((String)listOfUsers.getSelectedValue());
                refreshUsers();
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

        switch(button.getName()){
            case "addUser":
                addUser();
                break;
            case "deleteUser":
                deleteUser();
                break;
            case "changeUser":
                System.out.println("chUser");
                break;

        }

    }
}
