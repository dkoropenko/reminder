package Gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Nick on 06.10.2015.
 */
public class MainWindow {
    private JFrame window; //Окно программы
    private JPanel mainPanel;


    public MainWindow(){
        window = new JFrame("Reminder 0.0.2"); //Заголовок окна
        window.setBounds(100,100,800,400);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Закрытие по нажатию на крестик

        mainPanel = new JPanel(); //Создаем панель
        mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); //Добавляем рамку
        mainPanel.setLayout(new BorderLayout()); //Добавляем слой

        Window menu = new LeftMenu();
        Window list = new ToDoList();

        //Добавляем элементы к панели.
        mainPanel.add(menu.createPanel(), BorderLayout.WEST);
        mainPanel.add(list.createPanel(), BorderLayout.CENTER);

        window.add(mainPanel); //Добавляем ее к окну

        this.Run();
    }

    public void Run(){
        window.setVisible(true);
    }
}
