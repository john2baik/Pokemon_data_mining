
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Created by johnbaik on 3/31/17.
 */

public class PokemonParser {

    private File csvFile;
    private List <String[]> data;
    private String attributeDescriptions;
    private int tupleLength;
    private int dataSize;
    private  List<double[]> doubleAttributes;
    private List<String[]> stringAttributes;

    public PokemonParser(String filePathName){
        this.csvFile = new File(filePathName);
        makeLists();
    }

    public void makeLists(){
        makeIntoList(csvFile);
        seperateNumerical();
    }

    public void makeIntoList(File inputFile){
        data = new ArrayList<>();
        try{
            Scanner sc = new Scanner(inputFile);
            attributeDescriptions = sc.nextLine(); //skip header line
            while(sc.hasNextLine()){
                String line = sc.nextLine();
                String[] tuple = line.split(",");
                tupleLength = tuple.length;
                data.add(tuple);
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        dataSize = data.size();
    }

    private void seperateNumerical(){
        doubleAttributes = new ArrayList<>(dataSize);
        initializeDoubleList();
        stringAttributes = new ArrayList<>(dataSize);
        initializeStringList();

        for(int i = 0; i < dataSize; i++){
            String[] tuple = data.get(i);
            for(int j = 0; j < tupleLength; j++){
                try{
                    double numericalAttribute = Double.parseDouble(tuple[j]);
                    doubleAttributes.get(i)[j] = numericalAttribute;
                }
                catch(NumberFormatException e){
                    addToStringAttributes(i, j, tuple[j]);
                }
            }
        }
    }


    public void addToStringAttributes(int i, int j, String attribute){
        if(attribute == null || attribute.isEmpty()){
            stringAttributes.get(i)[j] = "DNE";
        }
        else{
            stringAttributes.get(i)[j] = attribute;
        }
    }

    public void initializeStringList(){
        String[] temp = new String[tupleLength];
        for(int i = 0; i < dataSize; i++){
            stringAttributes.add(temp);
        }
    }


    public void initializeDoubleList(){
        double[] temp = new double[tupleLength];
        for(int i = 0; i < dataSize; i++){
            doubleAttributes.add(temp);
        }
    }



    public void printDoubleLists(List<double[]> list){
        for(double[] x:list){
            System.out.println(Arrays.toString(x));
        }
    }

    public void printStringList(List<String[]> list){
        for(String[] x : list){
            System.out.println(Arrays.toString(x));
        }
    }

    public List<double[]> getDoubleAttributes(){
        return doubleAttributes();
    }

    public List<String[]> getStringAttributes(){
        return stringAttributes();
    }
    public List<String[]> getData(){
        return data;
    }
}
