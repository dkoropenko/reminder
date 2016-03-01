package listeners;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import db_logic.mainDBAction;
import gui.Constants;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

/**
 * Created by Koropenkods on 09.02.16.
 * Главный листенер, где указаны все методы для работы
 * с базой данных. Далее некоторые методы будут переопределены.
 */
public abstract class Listener {
    private JList list;
    private JTable table;
    private DefaultListModel listModel;
    private DefaultTableModel tableModel;

    private mainDBAction database;

    private Listener() {}

    public Listener(JList list, DefaultListModel listModel, JTable table, DefaultTableModel tableModel) {
        this.list = list;
        this.table = table;
        this.listModel = listModel;
        this.tableModel = tableModel;
    }

    public void addData(){
        if (!list.isSelectionEmpty()){
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
            if (data != null)
                database.addValues(data,System.currentTimeMillis(),0);

            //Обновляем UI
            refresh();
            table.setRowSelectionInterval(database.getDBSize()-1,database.getDBSize()-1);
        }
    }

    public void delData(){
        if (table.getSelectedRow() != -1) {
            //Берем имя выделенного элемента в левом меню.
            String dataBaseName = (String) listModel.getElementAt(list.getSelectedIndex());
            //Открываем базу данных этого элемента
            database = new mainDBAction(dataBaseName);

            if (table.getSelectedRow() != -1) {
                int options = JOptionPane.showConfirmDialog(table, "Вы уверены?");

                if (options == JOptionPane.OK_OPTION) {
                    int index = table.getSelectedRow();

                    database.delValues(index);
                    refresh();

                    System.out.println(database.getDBSize());

                    if (database.getDBSize() > 0 && index > 0) {
                        table.setRowSelectionInterval(index - 1, index - 1);
                    } else if (database.getDBSize() > 0)
                        table.setRowSelectionInterval(index, index);
                }
            }
        }
    }

    public void modData(){
        if (table.getSelectedRow() != -1) {
            delData();
            addData();
        }
    }

    public void complete(){
        if (table.getSelectedRow() != -1) {
            //Берем имя выделенного элемента в левом меню.
            String dataBaseName = (String) listModel.getElementAt(list.getSelectedIndex());
            //Открываем базу данных этого элемента
            database = new mainDBAction(dataBaseName);

            if (table.getSelectedRow() != -1) {
                //Берем индех выделенного элемента и устанавливаем
                //этой задаче статус "выполнено"
                int index = table.getSelectedRow()+1;

                int notCompleteStatus = 0;
                for (int i = 0; i < database.getDBSize(); i++) {
                    if (database.getStatus().get(i) == 0) {
                        notCompleteStatus++;
                    }

                    if (notCompleteStatus == index){
                        database.setStatus(i);
                        refresh();
                        if (database.getDBSize() > 0)
                            table.setRowSelectionInterval(database.getDBSize()-1, database.getDBSize()-1);
                        return;
                    }
                }
            }
        }
    }

    public void refresh(){
        //Создаем новую модель для таблицы
        DefaultTableModel newTableModel = new DefaultTableModel();
        //Делаем шапку таблицы
        newTableModel.addColumn(Constants.STATUS);
        newTableModel.addColumn(Constants.TASK);
        newTableModel.addColumn(Constants.DATE);

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

        if (database.getDBSize() > 0)
            table.setRowSelectionInterval(0,0);
    }


}
