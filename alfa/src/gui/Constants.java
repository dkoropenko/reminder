package gui;

import javax.swing.*;

/**
 * Created by Koropenkods on 29.02.16.
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
    String[] LETTERS = {"/", "\\", ":", "*", "?", "\"", "<", ">", "|"};
    String DRIVER = "org.sqlite.JDBC";
    String URL = "jdbc:sqlite:Task.db";
    String DEFAULT_USER = "User";
    String DEFAULT_PASSWORD = "";

    //Переменные UI
    String APPNAME = "Reminder 0.9";
    ImageIcon ICON = new ImageIcon("resource/images/icon.png");
    ImageIcon ADD = new ImageIcon("resource/images/add.png");
    ImageIcon DEL = new ImageIcon("resource/images/del.png");
    ImageIcon OPTIONS = new ImageIcon("resource/images/mod.png");
    ImageIcon START = new ImageIcon("resource/images/start.png");
    ImageIcon PAUSE = new ImageIcon("resource/images/pause.png");
    ImageIcon COMPLETE = new ImageIcon("resource/images/complete.png");
    ImageIcon FAIL = new ImageIcon("resource/images/fail.png");
    ImageIcon NEW = new ImageIcon("resource/images/new.png");
    ImageIcon OPEN = new ImageIcon("resource/images/open.png");
    ImageIcon SAVE = new ImageIcon("resource/images/save.png");
    ImageIcon CLOSE = new ImageIcon("resource/images/close.png");
    ImageIcon EXIT = new ImageIcon("resource/images/exit.png");
    ImageIcon USERS = new ImageIcon("resource/images/users.png");
    ImageIcon PREFERENCE = new ImageIcon("resource/images/options.png");

}
