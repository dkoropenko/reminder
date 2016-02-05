package gui;

import db_logic.DBAction;
import listeners.LeftMenuListeners;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Koropenkods on 04.02.16.
 */
public class MainWindow extends JFrame {

    private JPanel mainPanel;

    //Панели для левого окна вывода данных.
    private JPanel btnLeftPanel,
                    listLeftPanel,
                    mainLeftPanel;

    //Панели для главного окна вывода данных.
    private JPanel btnDataPanel,
                    listDataPanel,
                    mainDataPanel;

    //Кнопки для управлением данными в левом меню.
    private JButton btnLeftAdd, btnLeftDelete, btnLeftMod;
    //Кнопки для управлением данными в главном окне.
    private JButton btnDataAdd, btnDataDelete;

    private DefaultListModel listModel;
    private JList leftData;

    private JTable mainData;
    JScrollPane jscrlp;
    private final Object[] HEADERS  = {"№", "Дата", "Описание"};

    private DBAction dataBase;

    public MainWindow(){
        setTitle("Reminder v 0.0.4");
        setSize(700,450);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Подключаемся к базе
        dataBase = new DBAction();

        initData();
        initBtn();
        initPanels();

        setVisible(true);
    }

    private void initBtn(){

        LeftMenuListeners leftListener = new LeftMenuListeners(listModel, leftData);
        btnLeftAdd = new JButton();
        btnLeftAdd.setIcon(new ImageIcon("resource/images/add.png"));
        btnLeftAdd.setToolTipText("Добавить");
        btnLeftAdd.setName("add");
        btnLeftAdd.addActionListener(leftListener);

        btnLeftDelete = new JButton();
        btnLeftDelete.setIcon(new ImageIcon("resource/images/del.png"));
        btnLeftDelete.setToolTipText("Удалить");
        btnLeftDelete.setName("delete");
        btnLeftDelete.addActionListener(leftListener);

        btnLeftMod = new JButton();
        btnLeftMod.setIcon(new ImageIcon("resource/images/mod.png"));
        btnLeftMod.setToolTipText("Переименовать");
        btnLeftMod.setName("mod");
        btnLeftMod.addActionListener(leftListener);

        btnDataAdd = new JButton();
        btnDataAdd.setIcon(new ImageIcon("resource/images/add.png"));
        btnDataAdd.setToolTipText("Добавить");

        btnDataDelete = new JButton();
        btnDataDelete.setIcon(new ImageIcon("resource/images/del.png"));
        btnDataDelete.setToolTipText("Удалить");
    }

    private void initData(){
        listModel = new DefaultListModel();
        leftData = new JList(listModel);

        ArrayList<String> elements = dataBase.getValues();
        if (elements != null){
            for (String result: elements) {
                listModel.addElement(result);
            }
        }

        Object[][] data = {{"aaaaa","bbbbb","ccccc"},
                {"aaaaa","bbbbb","ccccc"},
                {"aaaaa","bbbbb","ccccc"},
                {"aaaaa","bbbbb","ccccc"},
                {"aaaaa","bbbbb","ccccc"},
                {"aaaaa","bbbbb","ccccc"},
                {"aaaaa","bbbbb","ccccc"},
                {"aaaaa","bbbbb","ccccc"},
                {"aaaaa","bbbbb","ccccc"},
                {"aaaaa","bbbbb","ccccc"},
                {"aaaaa","bbbbb","ccccc"},
                {"aaaaa","bbbbb","ccccc"},
                {"aaaaa","bbbbb","ccccc"},
                {"aaaaa","bbbbb","ccccc"},
                {"aaaaa","bbbbb","ccccc"},
                {"aaaaa","bbbbb","ccccc"},
                {"aaaaa","bbbbb","ccccc"},
                {"aaaaa","bbbbb","ccccc"},
                {"aaaaa","bbbbb","ccccc"},
                {"aaaaa","bbbbb","ccccc"},};
        mainData = new JTable(data, HEADERS);
        jscrlp = new JScrollPane(mainData);
        //Устаналиваем размеры прокручиваемой области
        mainData.setPreferredScrollableViewportSize(new Dimension(250, 100));
    }

    private void initPanels(){
        //*****Создаем слои*****//
        BorderLayout mainLayout = new BorderLayout(5,5);
        BorderLayout leftMainLayout = new BorderLayout(5,5);
        BorderLayout dataMainLayout = new BorderLayout(5,5);

        FlowLayout btnLayout = new FlowLayout();
        btnLayout.setAlignment(FlowLayout.LEFT);
        //***********************//

        //*****Создаем панели*****//
        //Главная панель
        mainPanel = new JPanel(mainLayout);

        //Левая панель для кнопок.
        btnLeftPanel = new JPanel(btnLayout);
        btnLeftPanel.setBorder(new EtchedBorder());
        //Общая левая панель.
        mainLeftPanel = new JPanel(leftMainLayout);
        mainLeftPanel.setBorder(new EtchedBorder());

        //Центральная панель для кнопок.
        btnDataPanel = new JPanel(btnLayout);
        btnDataPanel.setBorder(new EtchedBorder());
        //Общая центральная панель.
        mainDataPanel = new JPanel(dataMainLayout);
        mainDataPanel.setBorder(new EtchedBorder());
        //**************************//

        //*****Заполняем панели*****//
        //Заполняем левую панель
        btnLeftPanel.add(btnLeftAdd);
        btnLeftPanel.add(btnLeftDelete);
        btnLeftPanel.add(btnLeftMod);
        mainLeftPanel.add(btnLeftPanel, BorderLayout.NORTH);
        mainLeftPanel.add(leftData, BorderLayout.CENTER);

        //Заполняем центральную панель
        btnDataPanel.add(btnDataAdd);
        btnDataPanel.add(btnDataDelete);
        mainDataPanel.add(btnDataPanel, BorderLayout.NORTH);
        mainDataPanel.add(jscrlp, BorderLayout.CENTER);

        //Заполняем главную панель данными
        mainPanel.add(mainLeftPanel, BorderLayout.WEST);
        mainPanel.add(mainDataPanel, BorderLayout.CENTER);
        //*************************//

        //Заполняем Frame
        getContentPane().add(mainPanel);
    }

    public static void main(String[] args) {
        MainWindow test = new MainWindow();
    }
}
