package listeners;

import db_logic.mainDBAction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Koropenkods on 09.02.16.
 */
public class RefreshMouseListeners extends Listener implements MouseListener {
    private JTable table;
    private DefaultTableModel tableModel;

    private JList list;
    private DefaultListModel listModel;
    private mainDBAction mainDataBase;


    public RefreshMouseListeners(JList list, DefaultListModel listModel, JTable table, DefaultTableModel tableModel){
        super(list, listModel, table,tableModel);

        this.table = table;
        this.tableModel = tableModel;
        this.list = list;
        this.listModel = listModel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!list.isSelectionEmpty()){
            //Берем имя выделенного элемента в левом меню.
            String dataBaseName = (String) listModel.getElementAt(list.getSelectedIndex());
            //Открываем базу данных этого элемента
            mainDataBase = new mainDBAction(dataBaseName);

            refresh();
            if (mainDataBase.getDBSize() > 0)
                table.setRowSelectionInterval(0,0);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
