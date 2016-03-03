package listeners;

import db_logic.DataBaseClass;
import gui.AddUserWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Koropenkods on 03.03.16.
 */
public class UsersListener implements ActionListener {

    private AddUserWindow addUserWindow;

    public UsersListener(DataBaseClass dataBase){
        addUserWindow = new AddUserWindow(dataBase);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();

        switch (button.getName()){
            case "AddUser":
                addUserWindow.setVisible(true);
                break;
        }

    }
}
