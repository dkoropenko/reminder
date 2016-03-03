package listeners;

import gui.UsersWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Koropenkods on 03.03.16.
 */
public class MainMenuListener implements ActionListener {

    private UsersWindow usersWindow;

    public MainMenuListener(){
        usersWindow = new UsersWindow();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem menuItem = (JMenuItem) e.getSource();

        switch (menuItem.getName()){
            case "newDBMenu":
                break;
            case "openDBMenu":
                break;
            case "closeDBMenu":
                break;
            case "usersMenu":
                if (usersWindow.isVisible())
                    usersWindow.setVisible(false);
                else
                    usersWindow.setVisible(true);
                break;
            case "OptionsMenu":
                break;

        }
    }
}
