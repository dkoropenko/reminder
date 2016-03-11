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
 * Created by Koropenkods on 10.03.16.
 */
public class UpdateTaskGUI extends JFrame {
    JPanel mainPanel, textPanel, buttonPanel, startTimePanel, modTimePanel, finishTimePanel;

    private JTextField taskTitle;
    private JTextArea taskBody;
    private JScrollPane scrollBody;

    private JButton btnCreate, btnCancel;

    private JLabel startTimeLabel, modTimeLabel, finishTimeLabel;
    private JLabel startTimeData, modTimeData, finishTimeData;

    private MainWindowListener listener;
    private String selectedMasterName;
    private String title;
    DataBaseClass database;

    public UpdateTaskGUI(MainWindowListener listener, String selectedMasterName, String title){
        setTitle("Обновить");
        setIconImage(Constants.ICON.getImage());
        setSize(300,270);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        this.listener = listener;
        this.selectedMasterName = selectedMasterName;
        this.title = title;

        initTexts();
        //initLabels();
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

    private void initLabels(){
        startTimeLabel = new JLabel("Время создания");
        modTimeLabel = new JLabel("Время изменения");
        finishTimeLabel = new JLabel("Время завершения");

        startTimeData = new JLabel();
        modTimeData = new JLabel("Без изменений");
        finishTimeData = new JLabel("В работе");

        try {
            database = DataBaseClass.getInstance();
            database.connect();

            int startTime = Integer.parseInt(database.getFromTasks("createTime", database.currentUser, selectedMasterName, title).get(0));
            int modTime = Integer.parseInt(database.getFromTasks("modifyTime", database.currentUser, selectedMasterName, title).get(0));
            int finishTime = Integer.parseInt(database.getFromTasks("finishTime", database.currentUser, selectedMasterName, title).get(0));

            Calendar startDate = Calendar.getInstance();
            startDate.setTimeInMillis(startTime*1000);

            System.out.println(startDate.getTime().toString());

            startTimeData.setText(startDate.getTime().toString());

            if (modTime != 0){
                Calendar modDate = Calendar.getInstance();
                modDate.setTimeInMillis(modTime*1000);
                modTimeData.setText(modDate.getTime().toString());
            }
            if (finishTime != 0){
                Calendar finDate = Calendar.getInstance();
                finDate.setTimeInMillis(modTime*1000);
                finishTimeData.setText(finDate.getTime().toString());
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initBtn(){
        btnCreate = new JButton("Обновить");
        btnCreate.setName("updateTask");

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
                UpdateTaskGUI.this.setVisible(false);
            }
        });
    }

    public void initPanels(){
        BorderLayout mainLayout = new BorderLayout(5,5);
        FlowLayout elementsLayout = new FlowLayout();

        textPanel = new JPanel(elementsLayout);
//        startTimePanel = new JPanel(elementsLayout);
//        modTimePanel = new JPanel(elementsLayout);
//        finishTimePanel = new JPanel(elementsLayout);
        buttonPanel = new JPanel(elementsLayout);

        textPanel.add(taskTitle);
        textPanel.add(scrollBody);

        //startTimePanel.add(startTimeLabel);
        //startTimePanel.add(startTimeData);
        //modTimePanel.add(modTimeLabel);
        //modTimePanel.add(modTimeData);
        //finishTimePanel.add(finishTimeLabel);
        //finishTimePanel.add(finishTimeData);

//        textPanel.add(startTimePanel);
//        textPanel.add(modTimePanel);
//        textPanel.add(finishTimePanel);

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
