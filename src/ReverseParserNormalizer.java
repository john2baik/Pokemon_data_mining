/**
 * Created by lpchou on 4/14/2017.
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class ReverseParserNormalizer{
    private File 			csvFile;
    private File            resultFile;
    private File			reverseDataFile;
    private List <String[]> data;
    private List <String[]> reverseData;
    private List <String[]> rData;
    private String 			attributeDescriptions;
    private int 			tupleLength;


    private int 			dataSize;
    private int				reverseDataSize;
    private List<double[]> 	doubleAttributes;
    private List<String[]> 	stringAttributes;
    private List<double[]>  reverseDoubleAttributes;
    private List<HashMap>	alphbeticAttributes;
    private List<double[]>  numericNormAttributes;
    private double[]		maxiNumOfAttribs;
    private double[]		miniNumOfAttribs;
    private final double  	MAXIMUM_NUMBER  = 1000000.0;
    private final double  	MINIMUM_NUMBER  = 0.0;
    private boolean			debug = false;
    private BufferedWriter  output;

    public ReverseParserNormalizer(String dataSetFile, String resultFile, String reverseOutputFile) {
        this.csvFile 			= new File(dataSetFile);
        this.resultFile 		= new File(resultFile);
        this.reverseDataFile	= new File(reverseOutputFile);
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

    private void readData(File inputFile) {
        try{
            Scanner sc = new Scanner(inputFile);
            //attributeDescriptions = sc.nextLine(); //skip header line
            while(sc.hasNextLine()){
                String line = sc.nextLine();
                String[] tuple = line.split(",");
                tupleLength = tuple.length;
                reverseData.add(tuple);
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        reverseDataSize = data.size();

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

        reverseNormalizeNumericData(resultFile);
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

    private void reverseNormalizeNumericData(File resultFile) {
        reverseData = new ArrayList<>(dataSize);
        readData(resultFile);

        processReverseData();
    }

    private void processReverseData() {
        rData = new ArrayList<String[]>();
        int size = miniNumOfAttribs.length;

        for(int i = 0; i < reverseDataSize; i++){
            int k = 0;
            String[] tmp = new String[size];
            String[] tuple = reverseData.get(i);

            tmp[0] = String.valueOf(i+1);

            for (int j = 1; j < size; j++) {
                // it should be the alphabetic attribute
                if (miniNumOfAttribs[j] == MAXIMUM_NUMBER)
                    tmp[j] = "0";
                else if (tuple[k].contains("[")) {
                    // remove "]" at front
                    tuple[k] = tuple[k].substring(1, tuple[k].length());
                    tmp[j] = formatDecimalToString(Double.valueOf(tuple[k]).doubleValue(), maxiNumOfAttribs[j], miniNumOfAttribs[j]);
                    k++;
                }
                else if (tuple[k].contains("]")) {
                    // remove "]" at end
                    tuple[k] = tuple[k].substring(0, tuple[k].length()-1);
                    tmp[j] = formatDecimalToString(Double.valueOf(tuple[k]).doubleValue(), maxiNumOfAttribs[j], miniNumOfAttribs[j]);
                    k++;
                } else {
                    //double dValue = Double.valueOf(tuple[j]) * (maxiNumOfAttribs[j] - miniNumOfAttribs[j]);
                    tmp[j] = formatDecimalToString(Double.valueOf(tuple[k]).doubleValue(), maxiNumOfAttribs[j], miniNumOfAttribs[j]);
                    k++;
                }
            }

            rData.add(i, tmp);
        }
    }

    private String formatDecimalToString(double doubleValue, double maxi, double mini) {
        if (maxi == 0.0)
            return "0";

        double dValue = Double.valueOf(doubleValue) * (maxi - mini);
        dValue += mini;
        dValue = formatDecimal(dValue);
        String tmp = String.valueOf(dValue);
        return tmp;
    }

    public static double formatDecimal(double val) {
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        return Double.valueOf(numberFormat.format(val));
    }

    public void printReverseLists(List<String[]> list, String outputFile){
        try {
            output = new BufferedWriter(new FileWriter(outputFile));
            int i = 0;

            for(String[] x:list){
                output.write(Arrays.toString(x) + "\r\n");
                System.out.println(Arrays.toString(x));
            }

            output.close();
        } catch (IOException ex) {
            System.out.print(ex.getMessage());
        }
    }

    public void printDoubleLists(List<double[]> list, String outputFile){
        try {
            BufferedWriter output = new BufferedWriter(new FileWriter(outputFile));

            for(double[] x:list){
                output.write(Arrays.toString(x) + "\r\n");
                System.out.println(Arrays.toString(x));
            }

            output.close();
        } catch (IOException ex) {
            System.out.print(ex.getMessage());
        }
    }

    public void printStringList(List<HashMap> list, String output){
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

    public List<String[]> getReverseDoubleAttributes(){
        return rData;
    }

    //public List<String[]> getStringAttributes(){
    public List<HashMap> getStringAttributes(){
        return alphbeticAttributes;
    }
    public List<String[]> getData(){
        return data;
    }

}
