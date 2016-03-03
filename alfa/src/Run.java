import db_logic.DataBaseClass;
import gui.LogInWindow;
import gui.MainWindow;

import javax.xml.crypto.Data;
import java.sql.SQLException;

/**
 * Created by Koropenkods on 12.02.16.
 */
public class Run {
    public static void main(String[] args) {
        DataBaseClass database = null;
        try {
            database = DataBaseClass.getInstance();

            LogInWindow startWindow = new LogInWindow();
            startWindow.setVisible(true);

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (database != null)
                database.close();
        }
    }
}
