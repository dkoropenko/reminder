package gui;

import db_logic.DataBaseClass;
import listeners.MainMenuListener;
import listeners.MainWindowListener;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

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
    private JPanel btnMasterPanel,
                    mainMasterPanel;
    /**
     *Панель для главного окна вывода данных.
     */
    private JPanel btnTaskPanel,
                    mainTaskPanel;
    /**
     * Переменная для главного меню
     */
    private JMenuBar mainMenu;
    private JMenu fileMenu, prefMenu, helpMenu;
    private JMenuItem newDBMenu, openDBMenu, closeDBMenu, exitMenu;
    private JMenuItem usersMenu, optionsMenu;

    private MainWindowListener btnListener;

    //Кнопки для управлением данными в левом окне.
    private JButton btnMasterAdd, btnMasterDelete, btnMasterOptions;
    //Кнопки для управлением данными в главном окне.
    private JButton btnTaskAdd, btnTaskDelete, btnTaskOptions;
    //Кнопки для управлением состоянием задач
    private JButton btnTaskPause, btnTaskStart, btnTaskComplete;

    //Переменные управления списком элементов в левом меню
    private JList masterList;

    //Переменные для управления списком элементов в основном меню
    private DefaultTableModel tableModel;
    private JTable mainData;
    JScrollPane jscrlp;

    //База данных
    DataBaseClass database = null;

    public MainWindow(){
        setTitle(Constants.APPNAME + " User: " + database.currentUser);
        setSize(700, 450);
        setResizable(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(Constants.ICON.getImage());

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
        masterList = new JList();
        masterList.setDragEnabled(false);

        try {
            database = DataBaseClass.getInstance();
            database.connect();

            ArrayList<String> tasks = database.getFromMaster("name", database.currentUser);

            masterList.setListData(tasks.toArray());
            masterList.setSelectedIndex(0);
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


        //********КОНЕЦ ЛЕВОЕ МЕНЮ******************

        //******** ОСНОВНОЕ МЕНЮ******************
        //Создаем модель для работы с таблицей.
        tableModel = new DefaultTableModel();

        //Создаем таблицу и добавляем ей модель. Оборачиваем все в скроллпаин.
        mainData = new JTable();
        mainData.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        //Устаналиваем размеры прокручиваемой области
        mainData.setPreferredScrollableViewportSize(new Dimension(250, 100));

        //Заполняем таблицу данными.
        initTableContents();

        //Создаем пользовательский рендер для таблицы.
        //MainDBTableRender render = new MainDBTableRender();
        //mainData.setDefaultRenderer(Object.class,render);

        //Задаем модель для таблицы.
        mainData.setModel(tableModel);
        jscrlp = new JScrollPane(mainData);
        //Выделяем первую строчку в таблице
        if (tableModel.getRowCount() > 0)
           mainData.setRowSelectionInterval(0,0);

        //А так же размеры столбцов
        mainData.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        mainData.getColumnModel().getColumn(0).setMaxWidth(30);
        mainData.getColumnModel().getColumn(1).setMaxWidth(10000);
        mainData.getColumnModel().getColumn(2).setMaxWidth(70);
        //********КОНЕЦ ПРАВОЕ МЕНЮ******************
    }

    private void initTableContents(){
        try {
            database = DataBaseClass.getInstance();
            database.connect();

            ArrayList<Integer> id = new ArrayList<>();
            ArrayList<String> resultStatus = new ArrayList<>();
            ArrayList<String> title = database.getFromTasks("title", database.currentUser, (String)masterList.getSelectedValue());
            ArrayList<String> status = database.getFromTasks("status", database.currentUser, (String)masterList.getSelectedValue());

            //Заролняем таблицу
            for (int i = 1; i <= title.size(); i++) {
                id.add(i);
                resultStatus.add(Constants.TASK_STATUS[Integer.parseInt(status.get(i-1))]);
            }
            tableModel.addColumn(Constants.ID, id.toArray());
            tableModel.addColumn(Constants.TASK, title.toArray());
            tableModel.addColumn(Constants.STATUS, resultStatus.toArray());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initBtn(){
        btnListener = new MainWindowListener(masterList, tableModel, mainData);
        
        btnMasterAdd = new JButton();
        btnMasterAdd.setIcon(Constants.ADD);
        btnMasterAdd.setToolTipText("Добавить");
        btnMasterAdd.setName("MasterAdd");
        btnMasterAdd.addActionListener(btnListener);

        btnMasterDelete = new JButton();
        btnMasterDelete.setIcon(Constants.DEL);
        btnMasterDelete.setToolTipText("Удалить");
        btnMasterDelete.setName("MasterDel");
        btnMasterDelete.addActionListener(btnListener);

        btnMasterOptions = new JButton();
        btnMasterOptions.setIcon(Constants.OPTIONS);
        btnMasterOptions.setToolTipText("Переименовать");
        btnMasterOptions.setName("MasterOptions");
        btnMasterOptions.addActionListener(btnListener);


        btnTaskAdd = new JButton();
        btnTaskAdd.setIcon(Constants.ADD);
        btnTaskAdd.setToolTipText("Добавить");
        btnTaskAdd.setName("TaskAdd");
        btnTaskAdd.addActionListener(btnListener);

        btnTaskDelete = new JButton();
        btnTaskDelete.setIcon(Constants.DEL);
        btnTaskDelete.setToolTipText("Удалить");
        btnTaskDelete.setName("TaskDel");
        btnTaskDelete.addActionListener(btnListener);

        btnTaskOptions = new JButton();
        btnTaskOptions.setIcon(Constants.OPTIONS);
        btnTaskOptions.setToolTipText("Модифицировать");
        btnTaskOptions.setName("TaskOptions");
        btnTaskOptions.addActionListener(btnListener);

        btnTaskComplete = new JButton();
        btnTaskComplete.setIcon(Constants.COMPLETE);
        btnTaskComplete.setToolTipText("Завершить");
        btnTaskComplete.setName("TaskComplete");
        btnTaskComplete.addActionListener(btnListener);

        btnTaskStart = new JButton();
        btnTaskStart.setIcon(Constants.START);
        btnTaskStart.setToolTipText("Старт задачи");
        btnTaskStart.setName("TaskStart");
        btnTaskStart.addActionListener(btnListener);

        btnTaskPause = new JButton();
        btnTaskPause.setIcon(Constants.PAUSE);
        btnTaskPause.setToolTipText("Приостановить");
        btnTaskPause.setName("TaskPause");
        btnTaskPause.addActionListener(btnListener);
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
        btnMasterPanel = new JPanel(btnLayout);
        btnMasterPanel.setBorder(new EtchedBorder());
        //Общая левая панель.
        mainMasterPanel = new JPanel(leftMainLayout);
        mainMasterPanel.setBorder(new EtchedBorder());

        //Центральная панель для кнопок.
        btnTaskPanel = new JPanel(btnLayout);
        btnTaskPanel.setBorder(new EtchedBorder());
        //Общая центральная панель.
        mainTaskPanel = new JPanel(dataMainLayout);
        mainTaskPanel.setBorder(new EtchedBorder());
        //**************************//

        //*****Заполняем панели*****//
        //Заполняем левую панель
        btnMasterPanel.add(btnMasterAdd);
        btnMasterPanel.add(btnMasterDelete);
        btnMasterPanel.add(btnMasterOptions);
        mainMasterPanel.add(btnMasterPanel, BorderLayout.NORTH);
        mainMasterPanel.add(masterList, BorderLayout.CENTER);

        //Заполняем центральную панель
        btnTaskPanel.add(btnTaskAdd);
        btnTaskPanel.add(btnTaskDelete);
        btnTaskPanel.add(btnTaskOptions);
        btnTaskPanel.add(btnTaskPause);
        btnTaskPanel.add(btnTaskStart);
        btnTaskPanel.add(btnTaskComplete);
        mainTaskPanel.add(btnTaskPanel, BorderLayout.NORTH);
        mainTaskPanel.add(jscrlp, BorderLayout.CENTER);

        //Заполняем главную панель данными
        mainPanel.add(mainMasterPanel, BorderLayout.WEST);
        mainPanel.add(mainTaskPanel, BorderLayout.CENTER);
        //*************************//

        //Заполняем Frame
        setJMenuBar(mainMenu);
        getContentPane().add(mainPanel);
    }
}
