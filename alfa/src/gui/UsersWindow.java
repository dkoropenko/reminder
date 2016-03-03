package gui;

import db_logic.DataBaseClass;
import listeners.UsersListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Koropenkods on 02.03.16.
 * UI класс для работы с пользователями.
 * А именно "Добавление", "Удаление", "Изменение".
 */
public class UsersWindow extends JFrame{

    //Панели для UI
    private JPanel mainPanel, listPanel, btnMainPanel, buttonsPanel;

    //Кнопки проекта
    private JButton addUser, deleteUser, changeUser, chooseUser;
    private JButton close;

    //Подключение к БД.
    private DataBaseClass database = null;

    //Список пользователей.
    private JList<String> listOfUsers;

    public UsersWindow(){
        setTitle("Пользователи");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setMinimumSize(new Dimension(200,300));
        setMaximumSize(new Dimension(400,900));
        setSize(200,300);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon(Constants.ICON).getImage());

        initData();
        initButtons();
        initPanels();

        getContentPane().add(mainPanel);
    }

    //Заполнение списка пользователей.
    private void initData(){
//        listOfUsers = new JList<>();
//        HashMap<String, Object> values = new HashMap<>();
//        ArrayList<String> usersName = new ArrayList<>();
//
//        try {
//            database = DataBaseClass.getInstance();
//            database.connect();
//
//            for (int i = 1; i <= database.getSize("Users"); i++) {
//                values = database.getFromUsers(i);
//                if (values.size()!=0){
//                    for (Map.Entry<String, Object> out: values.entrySet()){
//                        if (out.getKey().equals("name")){
//                            usersName.add(out.getValue().toString());
//                        }
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }finally {
//            if (database != null)
//                database.close();
//        }
//
//        String[] result = new String[usersName.size()];
//        int iterable = usersName.size();
//
//        for (int i = 0; i < iterable; i++) {
//            result[i] = usersName.get(i);
//            System.out.println("i = "+ i +" result "+ result[i]);
//        }
//
//        listOfUsers.setListData(result);
//        listOfUsers.setSelectedIndex(0);
    }
    private void initButtons(){

        UsersListener usersListener = new UsersListener(database);

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
        addUser.addActionListener(usersListener);

        changeUser.setMinimumSize(minSize);
        changeUser.setPreferredSize(prefSize);
        changeUser.setMaximumSize(maxSize);

        deleteUser.setMinimumSize(minSize);
        deleteUser.setPreferredSize(prefSize);
        deleteUser.setMaximumSize(maxSize);

        close = new JButton("Закрыть");
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
    }
    private void initPanels(){
        BorderLayout main = new BorderLayout(5,5);
        BorderLayout list = new BorderLayout(5,5);
        BorderLayout btn = new BorderLayout(5,5);

        mainPanel = new JPanel(main);
        listPanel = new JPanel(list);
        btnMainPanel = new JPanel(btn);
        
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel,BoxLayout.Y_AXIS));

        listPanel.add(listOfUsers, BorderLayout.CENTER);

        buttonsPanel.add(addUser);
        buttonsPanel.add(changeUser);
        buttonsPanel.add(deleteUser);

        btnMainPanel.add(buttonsPanel, BorderLayout.CENTER);
        btnMainPanel.add(close, BorderLayout.SOUTH);

        mainPanel.add(listPanel, BorderLayout.CENTER);
        mainPanel.add(btnMainPanel, BorderLayout.EAST);
    }

    public static void main(String[] args) {
        UsersWindow test = new UsersWindow();
        test.setVisible(true);
    }

}
