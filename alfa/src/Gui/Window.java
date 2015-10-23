package Gui;

import engine.OpenDataBase;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Nick on 08.10.2015.
 */
public class Window {
    private JPanel buttons; //Панели для компоновки элементов.
    private JButton add, delete, change; //Кнопки для редактирования меню.

    private JList menu; //Список
    private JScrollPane container; //Контейнер для списков

    private OpenDataBase data;

    //Создаем кнопки для управления
    public JPanel createButtons(){

        //Добавляем иконки для кнопок
        ImageIcon addIcon = new ImageIcon("resource\\images\\add.png");
        ImageIcon delIcon = new ImageIcon("resource\\images\\del.png");
        ImageIcon modIcon = new ImageIcon("resource\\images\\mod.png");

        //Создаем сами кнопки
        add = new JButton(addIcon);
        delete = new JButton(delIcon);
        change = new JButton(modIcon);

        //Добавляем их к панели кнопок
        buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttons.add(add);
        buttons.add(delete);
        buttons.add(change);

        return buttons;
    }

    //Создаем gui списка меню
    public JScrollPane createList(){

        //Подключаемся к базе для взятия оттуда данных
        data = new OpenDataBase();

        //Создаем меню с данными из базы
        menu = new JList(data.readFromFile());
        container = new JScrollPane(menu);

        return container;
    }

    public GridBagConstraints setButtonsOptions(){
        GridBagConstraints butOptions = new GridBagConstraints();

        butOptions.anchor = GridBagConstraints.WEST;
        butOptions.fill   = GridBagConstraints.NONE;
        butOptions.gridheight = 1;
        butOptions.gridwidth  = 1;
        butOptions.gridx = 0;
        butOptions.gridy = 0;
        butOptions.insets = new Insets(2, 3, 3, 2);
        butOptions.ipadx = 0;
        butOptions.ipady = 0;
        butOptions.weightx = 1.0;
        butOptions.weighty = 0.0;

        return butOptions;
    }

    public GridBagConstraints setListOptions(){
        GridBagConstraints listOptions = new GridBagConstraints();

        listOptions.anchor = GridBagConstraints.WEST;
        listOptions.fill   = GridBagConstraints.BOTH;
        listOptions.gridheight = 1;
        listOptions.gridwidth  = 1;
        listOptions.gridx = 0;
        listOptions.gridy = 1;
        listOptions.insets = new Insets(2, 3, 3, 2);
        listOptions.ipadx = 0;
        listOptions.ipady = 0;
        listOptions.weightx = 1.0;
        listOptions.weighty = 1.0;

        return listOptions;
    }

    public JPanel createPanel(){

        //Засовываем все в общий контейнер
        GridBagLayout gb1 = new GridBagLayout();
        JPanel main = new JPanel(gb1);

        JPanel resultButtons = new JPanel();
        JScrollPane resultList = new JScrollPane();

        resultButtons = createButtons();
        resultList = createList();

        gb1.setConstraints(resultButtons, setButtonsOptions());
        main.add(resultButtons);
        gb1.setConstraints(resultList, setListOptions());
        main.add(resultList);

        main.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return main;
    }
}
