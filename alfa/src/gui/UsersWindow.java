package gui;

import db_logic.DataBaseClass;
import listeners.UserListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Koropenkods on 02.03.16.
 * UI класс для работы с пользователями.
 * А именно "Добавление", "Удаление", "Изменение".
 */
public class UsersWindow extends JFrame{

    //Панели для UI
    private JPanel mainPanel, listPanel, btnMainPanel, buttonsPanel;

    //Кнопки проекта
    private JButton addUser, deleteUser, changeUser;
    private JButton close;

    //Подключение к БД.
    private DataBaseClass database = null;

    //Список пользователей.
    private JList<String> listOfUsers;

    public UsersWindow(){
        setTitle("Пользователи");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setMinimumSize(new Dimension(200,300));
        setMaximumSize(new Dimension(400,900));
        setSize(200,300);
        setLocationRelativeTo(null);
        setIconImage(Constants.ICON.getImage());

        initData();
        initButtons();
        initListeners();
        initPanels();

        getContentPane().add(mainPanel);
    }

    //Заполнение списка пользователей.
    private void initData(){
        try {
            database = DataBaseClass.getInstance();
            database.connect();

            ArrayList<String> userName = database.getFromUsers("name");

            String[] users = new String[userName.size()];

            for (int i = 0; i < userName.size() ; i++) {
                users[i] = userName.get(i);
            }
            listOfUsers = new JList<>();
            listOfUsers.setListData(users);
            listOfUsers.setSelectedIndex(0);

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
    private void initButtons(){
        Dimension minSize = new Dimension(90,30);
        Dimension prefSize = new Dimension(90,30);
        Dimension maxSize = new Dimension(100,30);

        addUser = new JButton("Добавить");
        changeUser = new JButton("Изменить");
        deleteUser = new JButton("Удалить");

        addUser.setMinimumSize(minSize);
        addUser.setPreferredSize(prefSize);
        addUser.setMaximumSize(maxSize);
        addUser.setName("addUser");

        changeUser.setMinimumSize(minSize);
        changeUser.setPreferredSize(prefSize);
        changeUser.setMaximumSize(maxSize);
        changeUser.setName("changeUser");

        deleteUser.setMinimumSize(minSize);
        deleteUser.setPreferredSize(prefSize);
        deleteUser.setMaximumSize(maxSize);
        deleteUser.setName("deleteUser");

        close = new JButton("Закрыть");
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
    }
    private void initListeners(){
        UserListener userListener = new UserListener(listOfUsers);

        addUser.addActionListener(userListener);
        deleteUser.addActionListener(userListener);
        changeUser.addActionListener(userListener);
    }
    private void initPanels() {
        BorderLayout main = new BorderLayout(5, 5);
        BorderLayout list = new BorderLayout(5, 5);
        BorderLayout btn = new BorderLayout(5, 5);

        mainPanel = new JPanel(main);
        listPanel = new JPanel(list);
        btnMainPanel = new JPanel(btn);

        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));

        listPanel.add(listOfUsers, BorderLayout.CENTER);

        buttonsPanel.add(addUser);
        buttonsPanel.add(changeUser);
        buttonsPanel.add(deleteUser);

        btnMainPanel.add(buttonsPanel, BorderLayout.CENTER);
        btnMainPanel.add(close, BorderLayout.SOUTH);

        mainPanel.add(listPanel, BorderLayout.CENTER);
        mainPanel.add(btnMainPanel, BorderLayout.EAST);
    }
}
