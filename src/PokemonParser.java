
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by johnbaik on 3/31/17.
 */

public class PokemonParser {

    private File csvFile;
    private List <String[]> data;
    private String attributeDescriptions;

    public PokemonParser(String filePathName){
        this.csvFile = new File(filePathName);
        makeIntoList(csvFile);
    }

    public void makeIntoList(File inputFile){
        data = new ArrayList<>();
        try{
            Scanner sc = new Scanner(inputFile);
            attributeDescriptions = sc.nextLine(); //skip header line
            while(sc.hasNextLine()){
                String line = sc.nextLine();
                String[] tuple = line.split(",");
                data.add(tuple);
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }


    public List<String[]> getData(){
        return data;
    }
}
