package listeners;

import db_logic.mainDBAction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Koropenkods on 09.02.16.
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

        int index = table.getSelectedRow();
        database.setStatus(index);
        refresh();
    }

    public void refresh(){

        //Подключаемся к базе данных для того, что бы забрать данные для выделенного элемента.
        String databaseName = (String)listModel.getElementAt(list.getSelectedIndex());
        database = new mainDBAction(databaseName);

        DefaultTableModel newTableModel = new DefaultTableModel();
        //Делаем шапку таблицы
        newTableModel.addColumn("№");
        newTableModel.addColumn("Дата создания");
        newTableModel.addColumn("Задание");

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
                    result = new Object[]{"В процессе", hms.get(11) +":"+ hms.get(12), data.get(i),};
                    newTableModel.addRow(result);
                }
            }

            for (int i = 0; i < database.getContent().size(); i++) {
                if (database.getStatus().get(i) == 1){
                    hms.setTimeInMillis(time.get(i));
                    result = new Object[]{"Завершено", hms.get(11) +":"+ hms.get(12), data.get(i),};
                    newTableModel.addRow(result);
                }
            }

            table.setModel(newTableModel);
        }
        else{
            //Очищаем таблицу
            table.setModel(newTableModel);
        }
    }
}
