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

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem menuItem = (JMenuItem) e.getSource();

        switch (menuItem.getName()){
            case "newDBMenu":
                System.out.println("new DB");
                break;
            case "openDBMenu":
                System.out.println("OpenDB");
                break;
            case "closeDBMenu":
                System.out.println("Close DB");
                break;
            case "exit":
                System.exit(0);
                break;
            case "usersMenu":
                usersWindow = new UsersWindow();
                usersWindow.setVisible(true);
                break;
            case "optionsMenu":
                System.out.println("Options");
                break;

        }
    }
}
