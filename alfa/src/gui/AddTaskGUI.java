package gui;

import db_logic.DataBaseClass;
import javafx.scene.layout.Border;
import listeners.MainWindowListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Koropenkods on 09.03.16.
 */
public class AddTaskGUI extends JFrame {
    JPanel mainPanel, textPanel, buttonPanel;

    private JTextField taskTitle;
    private JTextArea taskBody;
    private JScrollPane scrollBody;

    private JButton btnCreate, btnCancel;
    private MainWindowListener listener;

    public AddTaskGUI(MainWindowListener listener){
        setTitle("Создать");
        setIconImage(Constants.ICON.getImage());
        setSize(300,270);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.listener = listener;

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

        taskBody = new JTextArea(15,7);
        taskBody.setLineWrap(true);
        taskBody.setPreferredSize(prefSizeBody);

        scrollBody = new JScrollPane(taskBody, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollBody.setPreferredSize(new Dimension(270,110));
        scrollBody.setBorder(BorderFactory.createTitledBorder(new EtchedBorder(),"Пояснение"));


    }
    public void initBtn(){
        btnCreate = new JButton("Создать");
        btnCreate.setName("newTask");

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
                AddTaskGUI.this.setVisible(false);
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
        taskTitle.setText("");
        return result;
    }

    public String getBody(){
        String result = taskBody.getText();
        taskBody.setText("");
        return result;
    }
}
