

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

//import kMeans.pokemon.stringAttribs;

public class NormalizerTest {
    double[][]						arrayOfNormalizedNumberic;
    double[]            			maxiNumOfAttribs;
    double[]						miniNumOfAttribs;
    ArrayList<ArrayList<String>> 	dataSets;
    stringAttribs[]  				arrayOfStringValues;
    //String[][]						arrayOfStringValues;
    HashMap<String, Integer>		hMap;
    int totalNumOfDataSets;
    boolean							debug = true;

    public NormalizerTest() {
        dataSets = new ArrayList<ArrayList<String>>();
        hMap = new HashMap<String, Integer>();
        totalNumOfDataSets = 0;
    }

    public void readData(String fName) throws FileNotFoundException, IOException{
        BufferedReader	in = new BufferedReader(new FileReader(fName));

        String 			line;
        boolean			firstLine = true;
        ArrayList<String> dataset;

        while (in.ready() && (line = in.readLine()) != null) {
            dataset = new ArrayList<String>();

            if (firstLine) {
                // since first line is header, skip it
                firstLine = false;
                continue;
            }

            String attribs[] = line.split(",");
            for (int i = 0; i < attribs.length; i++) {
                dataset.add(attribs[i]);
            }
            totalNumOfDataSets++;
            dataSets.add(dataset);

        }
    }

    public void processData() {
        ArrayList<String>	tmp;
        double[][]			arrayOfNumeric;
        String[][]			arrayOfString;
        double				dValue = 0.0;
        int					count = 0;

        arrayOfNumeric 		= new double[totalNumOfDataSets][dataSets.get(0).size()];
        arrayOfString  		= new String[totalNumOfDataSets][dataSets.get(0).size()];
        maxiNumOfAttribs 	= new double[dataSets.get(0).size()];
        miniNumOfAttribs 	= new double[dataSets.get(0).size()];

        //Initialize maxiNumOfAttribs & miniNumOfAttribs
        for (int i = 0; i < dataSets.get(0).size(); i++) {
            maxiNumOfAttribs[i] = 0.0;
            miniNumOfAttribs[i] = 1000000.0;
        }

        // calculate maxi and mini value for all attributes
        for (int i = 0; i < totalNumOfDataSets; i++) {
            tmp = dataSets.get(i);

            for (int j = 0; j < tmp.size(); j++) {
                try {
                    dValue = Double.valueOf(tmp.get(j));
                    arrayOfNumeric[i][j] = dValue;
                    if (dValue > maxiNumOfAttribs[j])
                        maxiNumOfAttribs[j] = dValue;
                    if (dValue < miniNumOfAttribs[j])
                        miniNumOfAttribs[j] = dValue;
                } catch (NumberFormatException ex) {
                    arrayOfString[i][j] = tmp.get(j);
                }
            }
        }

        arrayOfNormalizedNumberic = new double[totalNumOfDataSets][dataSets.get(0).size()];

        // normalize numeric data
        for (int i = 0; i < totalNumOfDataSets; i++) {
            int size = maxiNumOfAttribs.length;

            for (int j = 0; j < size; j++) {
                if ( arrayOfNumeric[i][j] != 0.0) {
                    arrayOfNormalizedNumberic[i][j] = formatDecimal((arrayOfNumeric[i][j] - miniNumOfAttribs[j]) / (maxiNumOfAttribs[j] - miniNumOfAttribs[j]));
                    if (debug)
                        System.out.println("i : " + i + " j : " + j + " val : " + arrayOfNumeric[i][j] + " maxi : " + maxiNumOfAttribs[j] + " mini : "+ miniNumOfAttribs[j] + " nor : " + arrayOfNormalizedNumberic[i][j]);
                }
            }
        }

        arrayOfStringValues = new stringAttribs[maxiNumOfAttribs.length];
        String str;
        stringAttribs newArray;

        // process String attribute
        for (int i = 0; i < totalNumOfDataSets; i++) {
            int size = maxiNumOfAttribs.length;

            //if ( arrayOfStringValues[i] == null)
            //	newArray = arrayOfStringValues[i];
            //else
            //	newArray = new stringAttribs(totalNumOfDataSets);

            for (int j = 0; j < size; j++) {
                if (null != arrayOfString[i][j]) {
                    if (debug)
                        System.out.println("i : " + i + " j : " + j + " " +arrayOfString[i][j]);
                    str = arrayOfString[i][j];
                    if ("".equals(str))
                        continue;

                    if (arrayOfStringValues[j] == null) {
                        arrayOfStringValues[j] = new stringAttribs(totalNumOfDataSets);
                        arrayOfStringValues[j].addAttribs(str);
                    } else {
                        arrayOfStringValues[j].addAttribs(str);
                    }
                }
            }
        }

        if (debug)
            System.out.println("Finished processData");
    }

    public static double formatDecimal(double val) {
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        return Double.valueOf(numberFormat.format(val));
    }


    public class stringAttribs {
        boolean 	empty;
        String[]	attribNames;
        int			index;
        int         lastIndex;
        int			numOfAttribs;
        int[]		attribCount;

        public stringAttribs() {
            empty = false;
        }

        public stringAttribs(int num) {
            empty 			= true;
            numOfAttribs 	= num;
            attribNames		= new String[num];
            attribCount     = new int[numOfAttribs];
            lastIndex    = 0;
        }

        public void addAttribs(String attrib) {
            boolean found = false;
            int index = 0;

            for (int i = 0; i < numOfAttribs; i++) {
                if (attrib.equals(attribNames[i])) {
                    found = true;
                    index = i;
                    break;
                }
            }

            if (found) {
                attribNames[index] = attrib;
                attribCount[index]++;
            }
            else {
                attribNames[lastIndex] = attrib;
                attribCount[lastIndex++]++;
            }
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: NormalizerTest datasetName");
            return;
        }

        String fileName = args[0];
        NormalizerTest p = new NormalizerTest();

        try {
            p.readData(fileName);
            p.processData();
        } catch (Exception ex) {

        }
    }

}
