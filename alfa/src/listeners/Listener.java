package listeners;

import db_logic.mainDBAction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

/**
 * Created by Koropenkods on 09.02.16.
 * Главный листенер, где указаны все методы для работы
 * с базой данных. Далее некоторые методы будут переопределены.
 */
public class Listener {
    private JList list;
    private JTable table;
    private DefaultListModel listModel;
    private DefaultTableModel tableModel;

    private mainDBAction database;

    public Listener(JList list, DefaultListModel listModel, JTable table, DefaultTableModel tableModel) {
        this.list = list;
        this.table = table;
        this.listModel = listModel;
        this.tableModel = tableModel;
    }

    public void addData(){
        //Берем имя выделенного элемента в левом меню.
        String dataBaseName = (String) listModel.getElementAt(list.getSelectedIndex());
        //Открываем базу данных этого элемента
        database = new mainDBAction(dataBaseName);

        //Открываем диалог для ввода данных
        String data = JOptionPane.showInputDialog(table, "Введите данные задачи");

        //Забираем системное время для указания времени создания задачи.
        Calendar time = Calendar.getInstance();
        time.setTimeInMillis(System.currentTimeMillis());

        //Добавляем данные в базу данных
        database.addValues(data,System.currentTimeMillis(),0);

        //Обновляем UI
        refresh();
    }

    public void delData(){
        //Берем имя выделенного элемента в левом меню.
        String dataBaseName = (String)listModel.getElementAt(list.getSelectedIndex());
        //Открываем базу данных этого элемента
        database = new mainDBAction(dataBaseName);

        int options = JOptionPane.showConfirmDialog(table,"Вы уверены?");

        if (options == JOptionPane.OK_OPTION){
            int index = table.getSelectedRow();

            database.delValues(index);
            refresh();
        }

    }

    public void modData(){
        delData();
        addData();
        refresh();
    }

    public void complete(){
        //Берем имя выделенного элемента в левом меню.
        String dataBaseName = (String) listModel.getElementAt(list.getSelectedIndex());
        //Открываем базу данных этого элемента
        database = new mainDBAction(dataBaseName);

        //Берем индех выделенного элемента и устанавливаем
        //этой задаче статус "выполнено"
        int index = table.getSelectedRow();
        database.setStatus(index);
        refresh();
    }

    public void refresh(){
        //Создаем новую модель для таблицы
        DefaultTableModel newTableModel = new DefaultTableModel();
        //Делаем шапку таблицы
        newTableModel.addColumn("Статус");
        newTableModel.addColumn("Дата создания");
        newTableModel.addColumn("Задание");

        //Подключаемся к базе данных для того, что бы забрать данные для выделенного элемента.
        if (list.getSelectedIndex() != -1){
            String databaseName = (String)listModel.getElementAt(list.getSelectedIndex());
            database = new mainDBAction(databaseName);



            if(database.getContent() != null){
                ArrayList<String> data = database.getContent();
                ArrayList<Long> time = database.getTime();
                ArrayList<Integer> status = database.getStatus();

                Calendar hms = Calendar.getInstance();

                //Переменная для добавления данных в таблицу.
                Object[] result;

                //Заполняем таблицу данными для выделенного элемента.
                for (int i = 0; i < database.getContent().size(); i++) {
                    if (database.getStatus().get(i) == 0){
                        hms.setTimeInMillis(time.get(i));
                        result = new Object[]{"В процессе", data.get(i), hms.get(5)+"."+ hms.get(4) +"."+ hms.get(1)};
                        newTableModel.addRow(result);
                    }
                }

                for (int i = 0; i < database.getContent().size(); i++) {
                    if (database.getStatus().get(i) == 1){
                        hms.setTimeInMillis(time.get(i));
                        result = new Object[]{"Завершено", data.get(i), hms.get(5)+"."+ hms.get(4) +"."+ hms.get(1)};
                        newTableModel.addRow(result);
                    }
                }

                table.setModel(newTableModel);
                table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
                table.getColumnModel().getColumn(0).setMaxWidth(100);
                table.getColumnModel().getColumn(2).setMaxWidth(100);
            }
            else
                //Очищаем таблицу
                table.setModel(newTableModel);
        }
        else{
            //Очищаем таблицу
            table.setModel(newTableModel);
        }
    }
}
