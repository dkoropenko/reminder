package gui;

import db_logic.DataBaseClass;
import listeners.MainMenuListener;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;

/**
 * Created by Koropenkods on 04.02.16.
 * Главный класс для построения графического интерфейса.
 * Здесь же идет первоначальное заполнение данными таблиц в программе.
 */
public class MainWindow extends JFrame {

    private JPanel mainPanel;

    /**
     *Панели для левого окна вывода данных.
     */
    private JPanel btnLeftPanel,
                    mainLeftPanel;
    /**
     *Панель для главного окна вывода данных.
     */
    private JPanel btnDataPanel,
                    mainDataPanel;
    /**
     * Переменная для главного меню
     */
    private JMenuBar mainMenu;
    private JMenu fileMenu, prefMenu, helpMenu;
    private JMenuItem newDBMenu, openDBMenu, closeDBMenu, exitMenu;
    private JMenuItem usersMenu, optionsMenu, refreshItemsMenu;

    //Кнопки для управлением данными в левом окне.
    private JButton btnLeftAdd, btnLeftDelete, btnLeftMod;
    //Кнопки для управлением данными в главном окне.
    private JButton btnDataAdd, btnDataDelete, btnDataMod, complete;

    //Переменные управления списком элементов в левом меню
    private DefaultListModel listModel;
    private JList leftData;

    //Переменные для управления списком элементов в основном меню
    private DefaultTableModel tableModel;
    private JTable mainData;
    JScrollPane jscrlp;

    //База данных
    DataBaseClass database;

    public MainWindow(){

        try {
            database = DataBaseClass.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        setTitle(Constants.APPNAME +" User: "+ database.currentUser);
        setSize(700,450);
        setResizable(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon(Constants.ICON).getImage());

        //Инициализируем UI
        initMenu();
        initData();
        initBtn();
        initPanels();
    }

    private void initMenu(){
        mainMenu = new JMenuBar();

        fileMenu = new JMenu("Файл");
        prefMenu = new JMenu("Настройки");
        helpMenu = new JMenu("Помощь");

        newDBMenu = new JMenuItem("Новая БД");
        newDBMenu.setName("newDBMenu");


        openDBMenu = new JMenuItem("Открыть БД");
        openDBMenu.setName("openDBMenu");


        closeDBMenu = new JMenuItem("Закрыть БД");
        closeDBMenu.setName("closeDBMenu");


        exitMenu = new JMenuItem("Выход");

        usersMenu = new JMenuItem("Пользователи");
        usersMenu.setName("usersMenu");


        optionsMenu = new JMenuItem("Опции");
        optionsMenu.setName("optionsMenu");


        fileMenu.add(newDBMenu);
        fileMenu.add(openDBMenu);
        fileMenu.add(closeDBMenu);
        fileMenu.addSeparator();
        fileMenu.add(exitMenu);

        prefMenu.add(usersMenu);
        prefMenu.add(optionsMenu);

        mainMenu.add(fileMenu);
        mainMenu.add(prefMenu);
    }

    private void initData(){
        //******** ЛЕВОЕ МЕНЮ******************
        //Создаем модель для работы со списком.
        listModel = new DefaultListModel();
        leftData = new JList(listModel);
        leftData.setDragEnabled(false);

        //********КОНЕЦ ЛЕВОЕ МЕНЮ******************

        //******** ОСНОВНОЕ МЕНЮ******************
        //Создаем модель для работы с таблицей.
        tableModel = new DefaultTableModel();
        //Делаем шапку таблицы
        tableModel.addColumn(Constants.ID);
        tableModel.addColumn(Constants.DATE);
        tableModel.addColumn(Constants.VALUE);

        //Создаем таблицу и добавляем ей модель. Оборачиваем все в скроллпаин.
        mainData = new JTable();
        mainData.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //Устаналиваем размеры прокручиваемой области
        mainData.setPreferredScrollableViewportSize(new Dimension(250, 100));

        //Заполняем таблицу данными.
        initTableContents();

        //Создаем пользовательский рендер для таблицы.
        MainDBTableRender render = new MainDBTableRender();
        mainData.setDefaultRenderer(Object.class,render);

        //Задаем модель для таблицы.
        mainData.setModel(tableModel);
        jscrlp = new JScrollPane(mainData);
        //Выделяем первую строчку в таблице
        if (tableModel.getRowCount() > 0)
           mainData.setRowSelectionInterval(0,0);

        //А так же размеры столбцов
        mainData.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        mainData.getColumnModel().getColumn(0).setMaxWidth(100);
        mainData.getColumnModel().getColumn(2).setMaxWidth(100);
        //********КОНЕЦ ПРАВОЕ МЕНЮ******************
    }

    private void initTableContents(){
    }

    private void initBtn(){
        btnLeftAdd = new JButton();
        btnLeftAdd.setIcon(new ImageIcon("resource/images/add.png"));
        btnLeftAdd.setToolTipText("Добавить");
        btnLeftAdd.setName("add");
        //btnLeftAdd.addActionListener(leftListener);

        btnLeftDelete = new JButton();
        btnLeftDelete.setIcon(new ImageIcon("resource/images/del.png"));
        btnLeftDelete.setToolTipText("Удалить");
        btnLeftDelete.setName("delete");
        //btnLeftDelete.addActionListener(leftListener);

        btnLeftMod = new JButton();
        btnLeftMod.setIcon(new ImageIcon("resource/images/mod.png"));
        btnLeftMod.setToolTipText("Переименовать");
        btnLeftMod.setName("mod");
        //btnLeftMod.addActionListener(leftListener);


        btnDataAdd = new JButton();
        btnDataAdd.setIcon(new ImageIcon("resource/images/add.png"));
        btnDataAdd.setToolTipText("Добавить");
        btnDataAdd.setName("add");
        //btnDataAdd.addActionListener();

        btnDataDelete = new JButton();
        btnDataDelete.setIcon(new ImageIcon("resource/images/del.png"));
        btnDataDelete.setToolTipText("Удалить");
        btnDataDelete.setName("del");
        //btnDataDelete.addActionListener();

        btnDataMod = new JButton();
        btnDataMod.setIcon(new ImageIcon("resource/images/mod.png"));
        btnDataMod.setToolTipText("Модифицировать");
        btnDataMod.setName("mod");
        //btnDataMod.addActionListener();

        complete = new JButton();
        complete.setIcon(new ImageIcon("resource/images/complete.png"));
        complete.setToolTipText("Завершить");
        complete.setName("complete");
        //complete.addActionListener();
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
        btnDataPanel.add(btnDataMod);
        btnDataPanel.add(complete);
        mainDataPanel.add(btnDataPanel, BorderLayout.NORTH);
        mainDataPanel.add(jscrlp, BorderLayout.CENTER);

        //Заполняем главную панель данными
        mainPanel.add(mainLeftPanel, BorderLayout.WEST);
        mainPanel.add(mainDataPanel, BorderLayout.CENTER);
        //*************************//

        //Заполняем Frame
        setJMenuBar(mainMenu);
        getContentPane().add(mainPanel);
    }
}
