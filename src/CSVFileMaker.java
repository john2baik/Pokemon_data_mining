import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by johnbaik on 4/18/17.
 */
public class CSVFileMaker {
    private List<List<double[]>> clusters;
    private String attributeDescriptions;
    private int attr1; //names for the 2 attributes chosen
    private int attr2;


    public CSVFileMaker(List<List<double[]>> clusters, String attributeDescriptions, int attribute1, int attribute2) {
        this.clusters = clusters;
        this.attributeDescriptions = attributeDescriptions;
        attr1 = attribute1;
        attr2 = attribute2;
        makeFile();
    }

    private void makeFile (){
        String[] attributes = attributeDescriptions.split(",");

        String firstAttr = attributes[attr1];
        String secondAttr = attributes[attr2];

        String filename = firstAttr+secondAttr+".csv";
        File csv = new File(filename);
        try {
            int count = 0;
            PrintWriter pw = new PrintWriter(csv);
            pw.write(firstAttr + ", " + secondAttr + "\n");
            for(List<double[]> cluster: clusters){
                for(double[] x : cluster){
                    pw.write(x[0] + ", " + x[1] + "\n");
                }
                count++;
                pw.write("\n Cluster number " + count + "\n");
            }
            pw.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }




    }

}
