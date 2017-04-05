
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by johnbaik on 3/31/17.
 */

public class ParserNormalizer {

    private File 			csvFile;
    private List <String[]> data;
    private String 			attributeDescriptions;
    private int 			tupleLength;


    private int 			dataSize;
    private List<double[]> 	doubleAttributes;
    private List<String[]> 	stringAttributes;
    private List<HashMap>	alphbeticAttributes;
    private List<double[]>  numericNormAttributes;
    private double[]		maxiNumOfAttribs;
    private double[]		miniNumOfAttribs;
    private final double  	MAXIMUM_NUMBER  = 1000000.0;
    private final double  	MINIMUM_NUMBER  = 0.0;
    private boolean			debug = false;

    public ParserNormalizer(String filePathName){
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
        maxiNumOfAttribs 	= new double[tupleLength];
        miniNumOfAttribs 	= new double[tupleLength];
        alphbeticAttributes = new ArrayList<HashMap>(tupleLength);
        numericNormAttributes = new ArrayList<>(dataSize);
        initializeNumericNormList();

        //Initialize maxiNumOfAttribs & miniNumOfAttribs
        for (int i = 0; i < tupleLength; i++) {
            maxiNumOfAttribs[i] = MINIMUM_NUMBER;
            miniNumOfAttribs[i] = MAXIMUM_NUMBER;
            alphbeticAttributes.add(i, new HashMap());
        }

        //finds out the max and min value for each attribute
        for(int i = 0; i < dataSize; i++){
            String[] tuple = data.get(i);
            double[] tmpDoubleArr = new double[tupleLength];
            for(int j = 0; j < tupleLength; j++){
                if (debug)
                    System.out.println(tuple[j]);

                try{
                    double numericalAttribute = Double.parseDouble(tuple[j]);
                    //double[] tmp = doubleAttributes..get(i);
                    tmpDoubleArr[j] = numericalAttribute;
                    if (numericalAttribute > maxiNumOfAttribs[j])
                        maxiNumOfAttribs[j] = numericalAttribute;
                    if (numericalAttribute < miniNumOfAttribs[j])
                        miniNumOfAttribs[j] = numericalAttribute;
                } catch(NumberFormatException e){
                    addToStringAttributes(i, j, tuple[j]);
                }
            }
            doubleAttributes.set(i, tmpDoubleArr);
        }

        normalizeNumericData();
    }



    public void addToStringAttributes(int i, int j, String attribute){
        int count ;

        if(attribute == null || attribute.length() == 0){
        //    stringAttributes.get(i)[j] = "DNE";
            attribute = "DNE";
        }
        else{
            stringAttributes.get(i)[j] = attribute;
        }

        if (alphbeticAttributes.get(j).containsKey(attribute)) {
            count = Integer.valueOf((Integer)alphbeticAttributes.get(j).get(attribute));
            count++;
            alphbeticAttributes.get(j).put(attribute, count);
        } else {
            alphbeticAttributes.get(j).put(attribute, 1);
        }

        if (debug)
            System.out.println(alphbeticAttributes.get(j).get(attribute));;
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

    public void initializeNumericNormList(){
        double[] temp = new double[tupleLength];
        for(int i = 0; i < dataSize; i++){
            numericNormAttributes.add(temp);
        }
    }

    public void normalizeNumericData() {
        // normalize numeric data
        for (int i = 0; i < dataSize; i++) {
            double[] tmpDoubleArr = new double[tupleLength];
            for (int j = 1; j < tupleLength; j++) {
                if (debug)
                    System.out.println(doubleAttributes.get(i)[j]);
                if (doubleAttributes.get(i)[j] != 0.0) {
                    tmpDoubleArr[j] = formatDecimal((doubleAttributes.get(i)[j] - miniNumOfAttribs[j]) / (maxiNumOfAttribs[j] - miniNumOfAttribs[j]));
                    if (debug)
                        System.out.println("i : " + i + " j : " + j + " val : " + doubleAttributes.get(i)[j] + " maxi : " + maxiNumOfAttribs[j] + " mini : "+ miniNumOfAttribs[j] + " nor : " + tmpDoubleArr[j]);
                }
            }
            numericNormAttributes.set(i, tmpDoubleArr);
        }

    }

    public static double formatDecimal(double val) {
        DecimalFormat numberFormat = new DecimalFormat("#.0000");
        return Double.valueOf(numberFormat.format(val));
    }

    public void printDoubleLists(List<double[]> list){
        for(double[] x:list){
            System.out.println(Arrays.toString(x));
        }
    }

    public void printStringList(List<HashMap> list){
        for (int i = 0; i < tupleLength; i++) {
            HashMap map = list.get(i);
            Set keys = map.keySet();
            System.out.println("Attribute # " + i);;
            for (Iterator iter = keys.iterator(); iter.hasNext(); ) {
                String key = (String) iter.next();
                Integer value = (Integer) map.get(key);
                System.out.println("      " +key + " ( " + value + " )");
            }
        }
    }

    public String getAttributeDescriptions(){
        return attributeDescriptions;
    }

    public List<double[]> getDoubleAttributes(){
        //return doubleAttributes();
        return numericNormAttributes;
    }

    //public List<String[]> getStringAttributes(){
    public List<HashMap> getStringAttributes(){
        //return stringAttributes();
        return alphbeticAttributes;
    }
    public List<String[]> getData(){
        return data;
    }
    public double[] getMaxiNumOfAttribs(){
        return maxiNumOfAttribs;
    }
    public double[] getMiniNumOfAttribs(){
        return miniNumOfAttribs;
    }
}
