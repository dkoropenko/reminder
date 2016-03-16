package listeners;

import gui.MainWindow;
import gui.TaskGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Koropenkods on 11.03.16.
 *
 * Класс для обновления главного окна программы при выборе
 * Нового элемента из левого списка.
 *
 * А так же для открытия
 */
public class MainWindowMouseListener extends MainWindowListener implements MouseListener {

    private JList masterList;
    private DefaultTableModel tableModel;
    private JTable taskTable;

    private TaskGUI showTask;

    public MainWindowMouseListener(JList masterList, DefaultTableModel tableModel, JTable taskTable, JLabel user) {
        super(masterList, tableModel, taskTable, user);
        this.masterList = masterList;
        this.tableModel = tableModel;
        this.taskTable = taskTable;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getComponent() instanceof JList){
            if (MouseEvent.BUTTON1 == e.getButton()){
                setSelectedElements(masterList.getSelectedIndex(),0);
                refreshElements();
            }
        }

        if (e.getComponent() instanceof JTable){
            if (MouseEvent.BUTTON1 == e.getButton() && e.getClickCount() == 2){
                if (taskTable.getSelectedRow() != -1){
                    updateTaskGUI = new TaskGUI(this, (String)masterList.getSelectedValue(), (String)tableModel.getValueAt(taskTable.getSelectedRow(),1), "Изменить");
                    updateTaskGUI.setVisible(true);
                }
            }
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
