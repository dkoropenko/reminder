package db_logic;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Koropenkods on 08.02.16.
 */
public class mainDBAction {
    private File DataBase;
    private FileWriter fw;
    private FileReader fr;

    public mainDBAction(String name){
        //Открываем файл
        DataBase = new File("DataBase/"+ name +"DataBase");

        //Если файл не создан, то создаем его.
        try {
            fw = new FileWriter(DataBase,true);
            fw.write("");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addValues(String content, Calendar time, int status){
        //Переменные времени
        int hour, minutes; //Переменные для указания даты

        //Результирующая строка для записи
        String result;

        //Берем системное время
        hour = time.get(11);
        minutes = time.get(12);

        //Складываем строку
        result = content +"*"+ hour +":"+ minutes +"*"+ status;

        try{
            //И записываем ее в файл.
            fw = new FileWriter(DataBase,true);
            fw.write(result +"\n");
            fw.flush();
            fw.close();
        }catch (IOException e){
            e.printStackTrace();
        }


    }

    public ArrayList<String> getContent() {
        try {
            //Открываем файл на чтение.
            fr = new FileReader(DataBase);

            //Переменныя для считывания данных из файла
            String lines = "";
            int c;
            //Считываем символы и записываем их в строку.
            while ((c = fr.read()) > 0) {
                lines += Character.toString((char) c);
            }

            //Переменные для хранения индекса и знака разделения данных.
            int returnIndex = 0;
            int asteriskIndex;
            //Переменная для возврата результата.
            ArrayList<String> result = new ArrayList<>();

            //Промежуточная переменная для проверки наличия символа переноса строки.
            String value;

            //Пока не прочитаем весь файл.
            while (lines.length() != returnIndex + 1) {
                //Берем индекс символа разделителя с начала файла
                // или с последнего символа переноса строки.
                asteriskIndex = lines.indexOf("*", returnIndex);
                //Записываем значение в переменную
                value = lines.substring(returnIndex, asteriskIndex);
                //Если в строке есть символ переноса строки, то удаляем его.
                if (value.indexOf('\n') != -1) value = value.substring(1, value.length());

                //Записываем все в результат.
                result.add(value);
                returnIndex = lines.indexOf("\n", returnIndex + 1);
            }

            return result;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<String> getTime() {
        try {
            //Открываем файл на чтение.
            fr = new FileReader(DataBase);

            //Переменныя для считывания данных из файла
            String lines = "";
            int c;
            //Считываем символы и записываем их в строку.
            while ((c = fr.read()) > 0) {
                lines += Character.toString((char) c);
            }

            //Переменные для хранения индекса и знака разделения данных.
            int returnIndex = 0;
            int beginIndex, asteriskIndex;
            //Переменная для возврата результата.
            ArrayList<String> result = new ArrayList<>();

            //Промежуточная переменная для проверки наличия символа переноса строки.
            String value;

            //Пока не прочитаем весь файл.
            while (lines.length() != returnIndex + 1) {
                //Берем индекс символа разделителя с начала файла
                // или с последнего символа переноса строки.
                beginIndex = lines.indexOf("*", returnIndex);
                asteriskIndex = lines.indexOf("*", beginIndex+1);
                //Записываем значение в переменную
                value = lines.substring(beginIndex+1, asteriskIndex);

                //Записываем все в результат.
                result.add(value);
                returnIndex = lines.indexOf("\n", returnIndex + 1);
            }

            return result;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<Integer> getStatus() {
        try {
            //Открываем файл на чтение.
            fr = new FileReader(DataBase);

            //Переменныя для считывания данных из файла
            String lines = "";
            int c;
            //Считываем символы и записываем их в строку.
            while ((c = fr.read()) > 0) {
                lines += Character.toString((char) c);
            }

            //Переменные для хранения индекса и знака разделения данных.
            int returnIndex = 0, nIndex;
            int beginIndex, asteriskIndex;
            //Переменная для возврата результата.
            ArrayList<Integer> result = new ArrayList<>();

            //Промежуточная переменная для проверки наличия символа переноса строки.
            String value;

            //Пока не прочитаем весь файл.
            while (lines.length() != returnIndex + 1) {
                //Берем индекс символа разделителя с начала файла
                // или с последнего символа переноса строки.
                beginIndex = lines.indexOf("*", returnIndex);
                asteriskIndex = lines.indexOf("*", beginIndex+1);
                nIndex = lines.indexOf("\n", asteriskIndex);

                System.out.println("Return Index = "+ returnIndex);

                //Записываем значение в переменную
                value = lines.substring(asteriskIndex+1, nIndex);

                //Записываем все в результат.
                result.add(Integer.parseInt(value));
                returnIndex = lines.indexOf("\n", returnIndex + 1);
            }

            return result;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) {
        mainDBAction test = new mainDBAction("test");
        Calendar timeTest = Calendar.getInstance();
        timeTest.setTimeInMillis(System.currentTimeMillis());

        //test.addValues("капучино", timeTest,0);

        ArrayList<String> arr = test.getTime();

        for (String aa : arr){
            System.out.println("Test: "+ aa);
        }

        ArrayList<Integer> arr1 = test.getStatus();

        for (int aa : arr1){
            System.out.println("Test status: "+ aa);
        }
    }
}
