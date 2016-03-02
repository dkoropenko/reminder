package gui;

import db_logic.DataBaseClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Koropenkods on 02.03.16.
 */
public class UsersWindow extends JFrame{

    private JPanel mainPanel, listPanel, btnMainPanel, buttonsPanel;

    private JButton addUser, deleteUser, changeUser;
    private JButton close;

    private DataBaseClass database = null;

    private JList<String> listOfUsers;

    private void initData(){
        listOfUsers = new JList<>();
        HashMap<String, Object> values = new HashMap<>();
        ArrayList<String> usersName = new ArrayList<>();

        try {
            database = DataBaseClass.getInstance();

            for (int i = 1; i <= database.getSize("Users"); i++) {
                values = database.getFromUsers(i);
                if (values.size()!=0){
                    for (Map.Entry<String, Object> out: values.entrySet()){
                        if (out.getKey().equals("name")){
                            usersName.add(out.getValue().toString());
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (database != null)
                database.close();
        }

        String[] result = new String[usersName.size()];

        for (int i = 0; i < usersName.size(); i++) {
            result[i] = usersName.get(i);
            System.out.println("result "+ result[i]);
        }

        listOfUsers.setListData(result);
        listOfUsers.setSelectedIndex(0);
    }

    private void initButtons(){
        addUser = new JButton("Добавить");
        changeUser = new JButton("Изменить");
        deleteUser = new JButton("Удалить");

        close = new JButton("Закрыть");
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
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

    public void run(){
        setName("Пользователи");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300,500);
        //setResizable(false);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon("resource/images/icon.png").getImage());

        initData();
        initButtons();
        initPanels();

        getContentPane().add(mainPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        UsersWindow test = new UsersWindow();

        test.run();
    }

}
