package gui;

import javax.swing.*;

/**
 * Created by Koropenkods on 29.02.16.
 * Интерфейс, который я использую исключительно как
 * контейнер с константами.
 */
public interface Constants {
    //Данные для заполнения таблицы
    String ID =            "№";
    String CREATE_DATE =   "Cоздано";
    String MODIFY_DATE =   "Изменено";
    String FINISH_DATE =   "Завершено";
    String TASK =          "Задание";
    String STATUS =        "Статус";
    int TASK_CREATE =       0;
    int TASK_MOD =          1;
    int TASK_START =        2;
    int TASK_PAUSE =        3;
    int TASK_FINISH =       4;
    String[] TASK_STATUS = {"Создано", "Изменен", "В работе", "Пауза", "Закончено"};

    //Данные для создания БД.
    String DRIVER = "org.sqlite.JDBC";
    String URL = "jdbc:sqlite:Task.db";
    String DEFAULT_DB = "Task.db";
    String DEFAULT_USER = "User";
    String DEFAULT_PASSWORD = "";

    //Переменные UI
    String APPNAME = "Reminder";
    ImageIcon ICON = new ImageIcon("images/icon.png");
    ImageIcon ADD = new ImageIcon("images/add.png");
    ImageIcon DEL = new ImageIcon("images/del.png");
    ImageIcon OPTIONS = new ImageIcon("images/mod.png");
    ImageIcon START = new ImageIcon("images/start.png");
    ImageIcon PAUSE = new ImageIcon("images/pause.png");
    ImageIcon COMPLETE = new ImageIcon("images/complete.png");
    ImageIcon FAIL = new ImageIcon("images/fail.png");
    ImageIcon NEW = new ImageIcon("images/new.png");
    ImageIcon SAVE = new ImageIcon("images/save.png");
    ImageIcon EXIT = new ImageIcon("images/exit.png");
    ImageIcon USERS = new ImageIcon("images/users.png");
    ImageIcon HELP = new ImageIcon("images/help.png");

}
