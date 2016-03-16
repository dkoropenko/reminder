package listeners;

import db_logic.DataBaseClass;
import gui.Constants;
import gui.UsersWindow;

import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by Koropenkods on 03.03.16.
 */
public class MainMenuListener extends MainWindowListener implements ActionListener {

    private UsersWindow usersWindow;
    private DataBaseClass database = null;

    private JFileChooser openDBFile;

    public MainMenuListener(JList masterList, DefaultTableModel tableModel, JTable taskTable, JLabel user){
        super(masterList,tableModel,taskTable, user);
    }

    private void newDB(){
        String message = "Вы уверены? \n При создании новой БД. Используемая будет сохранена в \n" +
                "папке Backups в папке с программой.";
        int choose = JOptionPane.showConfirmDialog(null,message,"Новая БД", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, Constants.COMPLETE);

        if (choose == JOptionPane.OK_OPTION){
            Calendar date = Calendar.getInstance();
            date.setTimeInMillis(System.currentTimeMillis());
            String resultDate = date.get(Calendar.DAY_OF_MONTH)+"."+ date.get(Calendar.MONTH)+1 +"."+ date.get(Calendar.YEAR);

            File databaseFile = new File("Task.db");
            File BackupDatabaseFile = new File("Backup/Backup-"+ resultDate +".db");

            InputStream inStream = null;
            OutputStream outStream = null;

            try{
                inStream = new FileInputStream(databaseFile);
                outStream = new FileOutputStream(BackupDatabaseFile);
                byte[] buffer = new byte[1024];
                int length;

                while((length = inStream.read(buffer)) > 0){
                    outStream.write(buffer, 0, length);
                }

                database = DataBaseClass.getInstance();
                database.connect();
                database.dropTables();
                database.currentUser = Constants.DEFAULT_USER;
                refreshElements();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try{
                    inStream.close();
                    outStream.close();

                    try {
                        if (!database.databaseIsClosed())
                            database.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void backupDB(){
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(System.currentTimeMillis());
        String resultDate = date.get(Calendar.DAY_OF_MONTH)+"."+ date.get(Calendar.MONTH)+1 +"."+ date.get(Calendar.YEAR);

        File databaseFile = new File("Task.db");
        File BackupDatabaseFile = new File("Backup/Backup-"+ resultDate +".db");

        InputStream inStream = null;
        OutputStream outStream = null;

        try{
            inStream = new FileInputStream(databaseFile);
            outStream = new FileOutputStream(BackupDatabaseFile);
            byte[] buffer = new byte[1024];
            int length;

            while((length = inStream.read(buffer)) > 0){
                outStream.write(buffer, 0, length);
            }

            JOptionPane.showMessageDialog(null, "Резервная копия БД была создана в папке Backup\n" +
                    "в папке с программой.", "Резервная копия", JOptionPane.INFORMATION_MESSAGE, Constants.COMPLETE);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try{
                inStream.close();
                outStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void helpMenu(){
        String message = "Reminder 1.0 \n" +
                "Программа написана Коропенко Дмитрием. \n" +
                "\nРаспространяется свободно." +
                "\nЕсли есть вопросы или предложения" +
                "\nпо улучшению программы, то адреса для связи:\n" +
                "\n e-mail:         nikotin13@mail.ru"+
                "\n skype:          nikotin_dm";
        JOptionPane.showMessageDialog(null,message,"Помощь", JOptionPane.INFORMATION_MESSAGE, Constants.COMPLETE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem menuItem = (JMenuItem) e.getSource();

        switch (menuItem.getName()){
            case "newDBMenu":
                newDB();
                break;
            case "backupDBMenu":
                backupDB();
                break;
            case "exit":
                System.exit(0);
                break;
            case "usersMenu":
                usersWindow = new UsersWindow();
                usersWindow.setVisible(true);
                break;
            case "helpMenu":
                helpMenu();
                break;
        }
    }
}
