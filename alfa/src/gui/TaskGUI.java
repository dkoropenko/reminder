package gui;

import db_logic.DataBaseClass;
import listeners.MainWindowListener;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Calendar;

/**
 * Created by Koropenkods on 16.03.16.
 */
public class TaskGUI extends JFrame {
    JPanel mainPanel, textPanel, buttonPanel;

    private JTextField taskTitle;
    private JTextArea taskBody;
    private JScrollPane scrollBody;

    private JButton btnCreate, btnCancel;

    private MainWindowListener listener;
    private String selectedMasterName = "";
    private String title = "";
    private String action;
    DataBaseClass database;

    public TaskGUI(MainWindowListener listener, String selectedMasterName, String title, String action){
        setTitle(action);
        setIconImage(Constants.ICON.getImage());
        setSize(300,270);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        this.listener = listener;
        this.selectedMasterName = selectedMasterName;
        this.title = title;
        this.action = action;

        initTexts();
        initBtn();
        initPanels();

        getContentPane().add(mainPanel);
    }

    public void initTexts(){
        Dimension prefSizeTitle = new Dimension(270,40);
        Dimension prefSizeBody = new Dimension(250,80);

        taskTitle = new JTextField();
        taskTitle.setPreferredSize(prefSizeTitle);
        taskTitle.setBorder(BorderFactory.createTitledBorder(new EtchedBorder(),"Заголовок"));
        taskTitle.setText(title);

        taskBody = new JTextArea(15,7);
        taskBody.setLineWrap(true);
        taskBody.setPreferredSize(prefSizeBody);
        if (action.equals("Создать"))
            taskBody.setText("");
        else
            taskBody.setText(getBodyTask());

        scrollBody = new JScrollPane(taskBody, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollBody.setPreferredSize(new Dimension(270,110));
        scrollBody.setBorder(BorderFactory.createTitledBorder(new EtchedBorder(),"Пояснение"));
    }
    private String getBodyTask(){
        String result = null;
        try{
            database = DataBaseClass.getInstance();
            database.connect();
            result = database.getFromTasks("body", database.currentUser, selectedMasterName, title).get(0);
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                if (database.databaseIsClosed())
                    database.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public void initBtn(){
        switch (action){
            case "Создать":
                btnCreate = new JButton("Создать");
                btnCreate.setName("newTask");
                break;
            case "Изменить":
                btnCreate = new JButton("Обновить");
                btnCreate.setName("updateTask");
                break;
        }

        btnCancel = new JButton("Отмена");

        Dimension minSizeTitle = new Dimension(90,30);
        Dimension prefSizeTitle = new Dimension(90,30);
        Dimension maxSizeTitle = new Dimension(100,30);

        btnCreate.setMinimumSize(minSizeTitle);
        btnCreate.setPreferredSize(prefSizeTitle);
        btnCreate.setMaximumSize(maxSizeTitle);
        btnCreate.addActionListener(listener);

        btnCancel.setMinimumSize(minSizeTitle);
        btnCancel.setPreferredSize(prefSizeTitle);
        btnCancel.setMaximumSize(maxSizeTitle);
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
    }

    public void initPanels(){
        BorderLayout mainLayout = new BorderLayout(5,5);
        FlowLayout elementsLayout = new FlowLayout();

        textPanel = new JPanel(elementsLayout);
        buttonPanel = new JPanel(elementsLayout);

        textPanel.add(taskTitle);
        textPanel.add(scrollBody);

        buttonPanel.add(btnCreate);
        buttonPanel.add(btnCancel);

        mainPanel = new JPanel(mainLayout);
        mainPanel.add(textPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    public String getTitle(){
        String result = taskTitle.getText();
        return result;
    }
    public String getBody(){
        String result = taskBody.getText();
        return result;
    }
    public void clearData(){
        taskTitle.setText("");
        taskBody.setText("");
    }
}
