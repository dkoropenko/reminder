import db_logic.DataBaseClass;
import gui.Constants;
import gui.LogInWindow;
import gui.MainWindow;

import java.sql.SQLException;

/**
 * Created by Koropenkods on 12.02.16.
 * <p>Класс входа в программу. Если в базе данных есть
 * хотя бы один не стандартный пользователь, то
 * пользователю будет показано окно аунтетификации.</p>
 *
 * <p>Иначе программа откроется без запроса пароля.</p>
 */
public class Run {

    public static void main(String[] args) {
        DataBaseClass database = null;

        try {
            database = DataBaseClass.getInstance();
            database.connect();

            //database.getFromUsers("name").get(0).equals(Constants.DEFAULT_USER)
            // выясняем есть ли в бд стандартный пользователь и является ли он единственным.
            if (database.getSize("Users",null,null) == 1 && database.getFromUsers("name").get(0).equals(Constants.DEFAULT_USER)){
                database.currentUser = Constants.DEFAULT_USER;
                database.currentDB = Constants.DEFAULT_DB;

                MainWindow mainWindow = new MainWindow();
                mainWindow.setVisible(true);
            } else{
                database.currentDB = Constants.DEFAULT_DB;
                LogInWindow startWindow = new LogInWindow();
                startWindow.setVisible(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (!database.databaseIsClosed())
                    database.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
