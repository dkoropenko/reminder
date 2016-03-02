package gui;

import db_logic.DBAction;
import db_logic.mainDBAction;
import listeners.DataMenuListeners;
import listeners.LeftMenuListeners;
import listeners.RefreshMouseListeners;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;

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
                    listLeftPanel,
                    mainLeftPanel;

    /**
     *Панель для главного окна вывода данных.
     */
    private JPanel btnDataPanel,
                    listDataPanel,
                    mainDataPanel;
    /**
     * Переменная для главного меню
     */
    private JMenuBar mainMenu;
    private JMenu fileMenu, prefMenu, helpMenu;
    private JMenuItem newDBMenu, openDBMenu, closeDBMenu, exitMenu;
    private JMenuItem usersMenu, optionsMenu, refreshItemsMenu;

    //Кнопки для управлением данными в левом меню.
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

    //Подключение к базам данных
    private DBAction dataBase;
    private mainDBAction mainDatabase;

    public MainWindow(){
        this.dataBase = DBAction.getInstance();
        setTitle("Reminder v 0.9.9");
        setSize(700,450);
        setResizable(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon("resource/images/icon.png").getImage());

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
        openDBMenu = new JMenuItem("Открыть БД");
        closeDBMenu = new JMenuItem("Закрыть БД");
        exitMenu = new JMenuItem("Выход");

        usersMenu = new JMenuItem("Пользователи");
        optionsMenu = new JMenuItem("Опции");

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

        //Заполняем данными левый блок.
        ArrayList<String> elements = dataBase.getValues();
        if (elements != null){
            for (String result: elements) {
                listModel.addElement(result);
            }
            //Выделяем первый элемент.
            leftData.setSelectedIndex(0);
        }
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

        //Листенер для обновления окна при выборе другого пункта в левом меню
        leftData.addMouseListener(new RefreshMouseListeners(leftData,listModel,mainData, tableModel));
    }

    private void initTableContents(){
        //Подключаемся к базе данных для того, что бы забрать данные для выделенного элемента.
        if(!leftData.isSelectionEmpty()){
            mainDatabase = new mainDBAction((String)listModel.getElementAt(leftData.getSelectedIndex()));

            if(mainDatabase.getDBSize() > 0){
                ArrayList<String> data = mainDatabase.getContent();
                ArrayList<Long> time = mainDatabase.getTime();
                ArrayList<Integer> status = mainDatabase.getStatus();

                Calendar hms = Calendar.getInstance();

                //Переменная для добавления данных в таблицу.
                Object[] result;

                //Заполняем таблицу данными для выделенного элемента.
                for (int i = 0; i < mainDatabase.getContent().size(); i++) {
                    if (mainDatabase.getStatus().get(i) == 0){
                        hms.setTimeInMillis(time.get(i));
                        result = new Object[]{"В процессе", data.get(i), hms.get(5)+"."+ hms.get(4) +"."+ hms.get(1)};
                        tableModel.addRow(result);
                    }
                }

                for (int i = 0; i < mainDatabase.getContent().size(); i++) {
                    if (mainDatabase.getStatus().get(i) == 1){
                        hms.setTimeInMillis(time.get(i));
                        result = new Object[]{"Завершено", data.get(i), hms.get(5)+"."+ hms.get(4) +"."+ hms.get(1)};
                        tableModel.addRow(result);
                    }
                }
            }
        }
    }

    private void initBtn(){

        LeftMenuListeners leftListener = new LeftMenuListeners(leftData,listModel,mainData, tableModel);
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

        DataMenuListeners mainListener = new DataMenuListeners(mainData, tableModel, listModel, leftData);
        btnDataAdd = new JButton();
        btnDataAdd.setIcon(new ImageIcon("resource/images/add.png"));
        btnDataAdd.setToolTipText("Добавить");
        btnDataAdd.setName("add");
        btnDataAdd.addActionListener(mainListener);

        btnDataDelete = new JButton();
        btnDataDelete.setIcon(new ImageIcon("resource/images/del.png"));
        btnDataDelete.setToolTipText("Удалить");
        btnDataDelete.setName("del");
        btnDataDelete.addActionListener(mainListener);

        btnDataMod = new JButton();
        btnDataMod.setIcon(new ImageIcon("resource/images/mod.png"));
        btnDataMod.setToolTipText("Модифицировать");
        btnDataMod.setName("mod");
        btnDataMod.addActionListener(mainListener);

        complete = new JButton();
        complete.setIcon(new ImageIcon("resource/images/complete.png"));
        complete.setToolTipText("Завершить");
        complete.setName("complete");
        complete.addActionListener(mainListener);
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



    public void run(){setVisible(true);}
}
