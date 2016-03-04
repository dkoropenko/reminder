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
 * <p>ActionListener для GUI авторизации пользователя</p>
 * <p>Сверяет введенные данные с данными из БД.</p>
 */
public class LogInListener implements ActionListener {

    private JFrame startWindow;
    private JTextField user;
    private JPasswordField password;
    private DataBaseClass database;
    private MainWindow mainWindow;

    public LogInListener(JTextField user, JPasswordField passwd, JFrame startWindow){
        this.user = user;
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

        if (!user.getText().equals("")){
            try {
                database.connect();

                ArrayList<String> users = database.getFromUsers("name");
                ArrayList<String> passwd = database.getFromUsers("passwd");
                int index;
                String checkPasswd;

                if (users.contains(user.getText())){
                    index = users.indexOf(user.getText());
                    checkPasswd = String.copyValueOf(password.getPassword());

                    if (passwd.get(index).equals(checkPasswd)){
                        database.currentUser = user.getText();
                        startWindow.setVisible(false);
                        mainWindow = new MainWindow();
                        mainWindow.setVisible(true);
                    }
                    else{
                        password.setBackground(Color.PINK);
                        user.setBackground(Color.WHITE);
                    }

                }
                else{
                    user.setBackground(Color.PINK);
                    password.setBackground(Color.WHITE);
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }finally {
                try {
                    if (!database.databaseIsClosed())
                        database.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
        else{
            user.setBackground(Color.PINK);
            password.setBackground(Color.WHITE);
        }
    }
}
