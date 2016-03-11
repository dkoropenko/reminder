package listeners;

import gui.MainWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Koropenkods on 11.03.16.
 */
public class MainWindowMouseListener extends MainWindowListener implements MouseListener {

    private JList masterList;
    private DefaultTableModel tableModel;
    private JTable taskTable;

    public MainWindowMouseListener(JList masterList, DefaultTableModel tableModel, JTable taskTable) {
        super(masterList, tableModel, taskTable);
        this.masterList = masterList;
        this.tableModel = tableModel;
        this.taskTable = taskTable;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (MouseEvent.BUTTON1 == e.getButton()){
            setSelectedElements(masterList.getSelectedIndex(),0);
            refreshElements();
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
