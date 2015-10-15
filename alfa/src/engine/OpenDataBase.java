package engine;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Nick on 07.10.2015.
 */
public class OpenDataBase {
    private ArrayList<String> data; //Данные, считанные из файла.
    private String pathToFile; //Путь до файла.
    private BufferedReader fileReader; //Файл для открытия.
    private FileReader fileR;
    private FileWriter fileW;


    public String[] readFromFile(){
        data = new ArrayList<>();
        pathToFile = "db\\menu.txt";

        try{
            fileR = new FileReader(pathToFile);
            fileReader = new BufferedReader(fileR);

            String a = "";
            while ((a = fileReader.readLine()) != null){
                data.add(a);
            }
            fileR.close();
        }
        catch (FileNotFoundException e){
            System.out.println("File not found when try to read");
        }
        catch (NullPointerException e){
            System.out.println("File not open when try to read");
        }
        catch (IOException e){
            System.out.println("Error IO when try to read");
        }
        finally {
        }

        int count = data.size();
        String[] result = new String[count];
        for (int i = 0; i < data.size(); i++) {
            result[i] = data.get(i);
        }

        return result;
    }

    public void writeToFile(){
        try {
            fileW = new FileWriter(pathToFile, true);

            String text = "Hello World";
            String text1 = "Hello World 2";

            fileW.append('\n');
            fileW.write(text);
            fileW.append('\n');
            fileW.write(text1);
            fileW.close();
        }
        catch (IOException e){
            System.out.println(" Error IO when try to write");
        }
        finally {
        }
    }

    public void readFromList(){
        ArrayList<String[]> data = new ArrayList<>();
        pathToFile = "db\\list.txt";

        try{
            fileR = new FileReader(pathToFile);
            fileReader = new BufferedReader(fileR);

            String[] text = new String[4];
            String a = "";

            while ((a = fileReader.readLine()) != null){
                System.out.println(a.indexOf(5,0));
            }


            fileR.close();
        }
        catch (FileNotFoundException e){
            System.out.println("File not found when try to read");
        }
        catch (NullPointerException e){
            System.out.println("File not open when try to read");
        }
        catch (IOException e){
            System.out.println("Error IO when try to read");
        }
        finally {
        }

        /*int count = data.size();
        String[] result = new String[count];
        for (int i = 0; i < data.size(); i++) {
            result[i] = data.get(i);
        }*/
    }
}
