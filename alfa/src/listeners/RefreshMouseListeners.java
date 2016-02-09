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


    public RefreshMouseListeners(JList list, DefaultListModel listModel, JTable table, DefaultTableModel tableModel){
        super(list, listModel, table,tableModel);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        refresh();
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
