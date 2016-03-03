package listeners;

import db_logic.DataBaseClass;
import gui.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Koropenkods on 03.03.16.
 */
public class LogInListener implements ActionListener {

    private JFrame startWindow;
    private JTextField text;
    private JPasswordField password;
    private DataBaseClass database;
    private MainWindow mainWindow;

    public LogInListener(JTextField text, JPasswordField passwd, JFrame startWindow){
        this.text = text;
        this.password = passwd;
        this.startWindow = startWindow;

        try {
            database = DataBaseClass.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!text.getText().equals("")){
            try {
                database.connect();

                ArrayList<String> users = database.getFromUsers("name");
                ArrayList<String> passwd = database.getFromUsers("passwd");
                int index;

                if (users.contains(text.getText())){
                    index = users.indexOf(text.getText());
                    System.out.println(index);
                    if (passwd.get(index).equals(password.getPassword())){
                        System.out.println("Entered in system");
                    }
                }
                else
                    text.setBackground(Color.PINK);


            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        else{
            text.setBackground(Color.PINK);
        }

    }
}
