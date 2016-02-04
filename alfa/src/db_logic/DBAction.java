package db_logic;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Koropenkods on 04.02.16.
 */
public class DBAction {

    private FileOutputStream fos;

    private File leftDB;

    public boolean connect(){
        leftDB = new File("DataBase/leftDataBase");

        if(leftDB.exists()) return true;
        else return false;
    }

    public void addValue(String name){
        try {
            fos = new FileOutputStream(leftDB,true);

            name += ">";

            fos.write(name.getBytes());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public ArrayList<String> getValues(){
        try{
            String names = "";
            char letter;
            int c;
            FileReader test = new FileReader(leftDB);
            while ((c = test.read()) != -1){
                letter = (char)c;
                names += Character.toString(letter);
            }

            ArrayList<String> result = new ArrayList<>();
            int index = names.indexOf(">");

            while (index != -1){
                index = names.indexOf(">");

                if (index != -1){
                    String name = names.substring(0,index);
                    names = names.substring(index+1, names.length());
                    result.add(name);
                }
            }
            String name = result.get(0).substring(1);
            result.remove(0);
            result.add(0,name);

            return result;

        } catch (FileNotFoundException e){
            System.out.println("Файл не найден");
        } catch (IOException e){
            System.out.println("Ошибка ввода вывода");
        }
        return null;
    }

    public static void main(String[] args) {
        DBAction test = new DBAction();

        if(test.connect()){
            System.out.println("Подключились к базе");
            test.addValue("                   П е т я ");
            test.addValue("Вася");
            test.addValue("Георгий");
            test.addValue("Петручио");

            ArrayList<String> testValues= test.getValues();

            for (String text: testValues) {
                System.out.println(text.trim());
            }
        }
        else
            System.out.println("Вы кто такие? Идите нахер, я вас не звал!!!");
    }
}
