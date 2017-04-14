import java.util.Arrays;
import java.util.List;

/**
 * Created by johnbaik on 4/4/17.
 */
public class NumericalCluster {
    private List <double[]> data;
    private String attributeDescriptions;
    private double[] max;
    private double[] min;
    private double[] oldCenter;
    private double[] newCenter;
    private int tupleLength;
    private int dataSize;

    public NumericalCluster(List<double[]> data, String attributeDescriptions, double[] max, double[] min){
        this.data = data;
        this.attributeDescriptions = attributeDescriptions;
        this.max = max;
        this.min = min;
        tupleLength = data.get(0).length;
        dataSize = data.size();


        run();
       // System.out.println(attributeDescriptions);
    }

    private void run(){
        initialSetup();
    }

    private void initialSetup(){

    }

    //-----------------initial

    public void printData(){
        for(double[] tuple : data){
            System.out.println(Arrays.toString(tuple));
        }
    }



}
