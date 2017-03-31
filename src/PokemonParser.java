import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by johnbaik on 3/31/17.
 */

public class PokemonParser {

    private File csvFile;
    private List <String[]> data;
    private String attributeDescriptions;
    private

    public PokemonParser(File csvFile){
        this.csvFile = csvFile;
    }

    private void makeIntoList(File csvFile throws FileNotFoundException{
        Scanner sc = new Scanner(csvFile);
        attributeDescriptions = sc.nextLine();
        while(sc.hasNextLine()){
            String line = sc.nextLine();
            String[] tuple = line.split(",");
            data.add(tuple);
        }
    }

}
