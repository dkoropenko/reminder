package runApp;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

/**
 * Created by Диман on 23.10.2015.
 */
public class test {

    public static void main(String[] args) {

        JFrame frame = new JFrame("New Window");
        frame.setBounds(10,10,400,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());


        ImageIcon icon = new ImageIcon("resource\\images\\add.png");

        JButton but = new JButton(icon);

        panel.add(but);

        frame.add(panel);

        frame.setVisible(true);
    }
}
