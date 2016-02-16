package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * Created by Koropenkods on 16.02.16.
 */
public class MainDBTableRender extends DefaultTableCellRenderer {

    private Color complete = Color.LIGHT_GRAY;
    private Color inWork = Color.WHITE;
    private Color select = Color.YELLOW;

    public MainDBTableRender(){}

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (column > 0){
            if (comp.getBackground() == complete)
                comp.setBackground(complete);
            else
                comp.setBackground(inWork);
        }
        else{
            if (value.equals("Завершено") && !isSelected)
                comp.setBackground(complete);
            else
                comp.setBackground(inWork);
        }

        if (isSelected) comp.setBackground(select);

        return comp;
    }
}
