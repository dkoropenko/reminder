package gui;

import db_logic.LeftMenu;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

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
    private JButton btnLeftAdd, btnLeftDelete;
    //Кнопки для управлением данными в главном окне.
    private JButton btnDataAdd, btnDataDelete;

    private DefaultListModel listModel;
    private JList leftData;

    private JTable mainData;
    JScrollPane jscrlp;
    private final Object[] HEADERS  = {"№", "Дата", "Описание"};

    public MainWindow(){
        setTitle("Reminder v 0.0.2");
        setSize(500,500);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initBtn();
        initData();
        initPanels();

        setVisible(true);
    }

    private void initBtn(){
        btnLeftAdd = new JButton();
        btnLeftAdd.setIcon(new ImageIcon("resource/images/add.png"));
        btnLeftAdd.setToolTipText("Добавить");

        btnLeftDelete = new JButton();
        btnLeftDelete.setIcon(new ImageIcon("resource/images/del.png"));
        btnLeftDelete.setToolTipText("Удалить");

        btnDataAdd = new JButton();
        btnDataAdd.setIcon(new ImageIcon("resource/images/add.png"));
        btnDataAdd.setToolTipText("Добавить");

        btnDataDelete = new JButton();
        btnDataDelete.setIcon(new ImageIcon("resource/images/del.png"));
        btnDataDelete.setToolTipText("Удалить");
    }

    private void initData(){
        LeftMenu test = new LeftMenu();
        test.setName("Вася");

        listModel = new DefaultListModel();
        leftData = new JList(listModel);
        listModel.addElement(test.getName());


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
