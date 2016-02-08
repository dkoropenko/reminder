package db_logic;

import javax.xml.crypto.Data;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by Koropenkods on 04.02.16.
 */
public class DBAction {
    //Переменная файла
    private File leftDB;
    //Переменная входящего потока
    private FileOutputStream fos;

    public DBAction(){
        connect();
    }

    private boolean connect(){
        //Подключаемся к базе данных
        leftDB = new File("DataBase/leftDataBase");

        if(leftDB.exists()) return true;
        else return false;
    }
    public void addValue(String name){
        try {
            //Открываем поток для записи в файл.
            //Пишем в конец файла
            fos = new FileOutputStream(leftDB,true);

            FileWriter  newDataBaseFile = new FileWriter("DataBase/"+ name +"DataBase");
            newDataBaseFile.write("");

            //Добавляем управляющий символ к элементу
            name += ">";

            //Пишем его в файл и закрываем поток.
            fos.write(name.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public ArrayList<String> getValues(){
        try{
            //Открываем файл на чтение.
            FileReader DataBase = new FileReader(leftDB);

            //Записываем все значения в строку
            String names = "";
            int c;
            while ((c = DataBase.read()) != -1){
                names += Character.toString((char)c);
            }

            DataBase.close();

            if (names.length() > 0){
                //Коллекция result содержит все данные из БД
                ArrayList<String> result = new ArrayList<>();

                //Находим первый управляющий симов.
                int index = names.indexOf(">");
                String name; //Переменная, содержащая одно значение из БД
                while (index != -1){
                    name = names.substring(0,index); //Записываем всё значение от начала строки до управляющего символа
                    names = names.substring(index+1, names.length()); //перезаписываем все значения за исключением считанного.
                    result.add(name); //Добавляем в коллекцию считанное значение.

                    index = names.indexOf(">");
                }
                return result;
            }
        } catch (FileNotFoundException e){
            System.out.println("Файл не найден");
        } catch (IOException e){
            System.out.println("Ошибка ввода вывода");
        }
        return null;
    }
    public void clear(){
        try{
            fos = new FileOutputStream(leftDB,false);

            fos.write("".getBytes());
            fos.close();
        }catch (FileNotFoundException e){
            System.out.println("Нет такого файла");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void remove(int index){
        try {
            ArrayList<String> names = getValues();
            names.remove(index);
            String result = "";

            for(int i=0; i < names.size(); i++)
                result += names.get(i) + ">";

            fos = new FileOutputStream(leftDB, false);
            fos.write(result.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void renameElement(int index, String newName){
        ArrayList<String> result = getValues();

        result.remove(index);
        result.add(index,newName);

        clear();
        for (String name: result) {
            addValue(name);
        }
    }

//    public static void main(String[] args) {
//        DBAction test = new DBAction();
//
//        if(test.connect()){
//            System.out.println("Подключились к базе");
//            test.addValue("                   П е т я ");
//            test.addValue("Вася");
//            test.addValue("Георгий");
//            test.addValue("Петручио");
//
//            ArrayList<String> testValues= test.getValues();
//
//            System.out.println("Добавили значения:");
//            for (String text: testValues) {
//                System.out.println(text.trim());
//            }
//
//            test.remove(2);
//            System.out.println("Удалили значение №3:");
//            testValues= test.getValues();
//
//            if (testValues != null) {
//                for (String text : testValues) {
//                    System.out.println(text.trim());
//
//                }
//            }
//            else System.out.println("Файл БД Пуст");
//
//            System.out.println("Очистили базу:");
//            test.clear();
//            testValues= test.getValues();
//
//            if (testValues != null) {
//                for (String text : testValues) {
//                    System.out.println(text.trim());
//
//                }
//            }
//            else System.out.println("Файл БД Пуст");
//
//        }
//        else
//            System.out.println("Вы кто такие? Идите нахер, я вас не звал!!!");
//    }
}
