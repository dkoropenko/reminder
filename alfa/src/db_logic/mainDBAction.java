package db_logic;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Koropenkods on 08.02.16.
 */
public class mainDBAction {
    private File DataBase;
    private FileWriter fw;
    private FileReader fr;

    public mainDBAction(String name){
        //Открываем файл
        DataBase = new File("DataBase/"+ name +"DB");


        //Если файл не создан, то создаем его.
        try {
            fw = new FileWriter(DataBase,true);
            fw.write("");
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addValues(String content, long time, int status){
        //Результирующая строка для записи
        String result;

        //Складываем строку
        result = content +"*"+ time +"*"+ status;

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
    public void delValues(int row){
        ArrayList<String> data = getContent();
        ArrayList<Long> time = getTime();
        ArrayList<Integer> status = getStatus();

        data.remove(row);
        time.remove(row);
        status.remove(row);

        try {
            fw = new FileWriter(DataBase);
            fw.write("");
            fw.flush();
            fw.close();

            for (int i = 0; i < data.size(); i++) {
                addValues(data.get(i), time.get(i), status.get(i));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setStatus(int row){
        ArrayList<String> data = getContent();
        ArrayList<Long> time = getTime();
        ArrayList<Integer> status = getStatus();

        String valueData = data.get(row);
        long valueTime = time.get(row);
        int valueStatus = status.get(row);

        if (valueStatus != 1){
            delValues(row);
            addValues(valueData,valueTime,1);
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

            if (!lines.isEmpty()){
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
            }
            else return null;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<Long> getTime() {
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
            ArrayList<Long> result = new ArrayList<>();

            //Промежуточная переменная для проверки наличия символа переноса строки.
            long value;

            //Пока не прочитаем весь файл.
            while (lines.length() != returnIndex + 1) {
                //Берем индекс символа разделителя с начала файла
                // или с последнего символа переноса строки.
                beginIndex = lines.indexOf("*", returnIndex);
                asteriskIndex = lines.indexOf("*", beginIndex+1);
                //Записываем значение в переменную
                value = Long.valueOf(lines.substring(beginIndex+1, asteriskIndex));

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
}
