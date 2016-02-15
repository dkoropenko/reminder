import db_logic.DBAction;
import gui.MainWindow;

/**
 * Created by Koropenkods on 12.02.16.
 */
public class Run {
    public static void main(String[] args) {
        DBAction mainDataBase = DBAction.getInstance();

        MainWindow app = new MainWindow();
        app.run();
    }
}
