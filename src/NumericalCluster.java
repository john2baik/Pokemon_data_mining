import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by johnbaik on 4/4/17.
 */
public class NumericalCluster {
    private List <double[]> data;
    private List<List<double[]>> clusters;
    private String attributeDescriptions;
    private double[] max;
    private double[] min;
    private List<double[]> oldCenter;
    private List<double[]> newCenter;
    private int tupleLength;
    private int dataSize;
    private int k;
    private boolean hasChanged;
    private int numOfAttributes;
    private final double threshold = 1.0;
    int attribute1;
    int attribute2;



    public NumericalCluster(List<double[]> data, String attributeDescriptions, double[] max, double[] min, int k, int attribute1, int attribute2){
        this.data = data;
        this.attributeDescriptions = attributeDescriptions;
        this.max = max;
        this.min = min;
        tupleLength = data.get(0).length;
        dataSize = data.size();
        this.k = k;
        hasChanged = true;
        numOfAttributes = data.get(0).length;
        this.attribute1 = attribute1;
        this.attribute2 = attribute2;
        if(attribute1 < 0 || attribute2 < 0){
            runAll();
        }
        else
            run();

        // System.out.println(attributeDescriptions);
    }

    private void run(){

    }

    private void runAll(){
        initialSetup();
        cluster();
    }

    private void cluster(){
        while(true){
            createClusters();
            assignCluster();
            updateClusterMean();
            checkForChange();
            if(!hasChanged){//clusters is now all clustrized
                break;
            }
            else{
                updateCenters();
            }
        }
    }

    private void updateCenters(){
        oldCenter = new ArrayList<>(newCenter);
    }

    private void checkForChange(){
        hasChanged = true;//only change when ALL centers have not changed by a 5% margin
        for(int i = 0; i < k; i++){
            double[] oldCenterTuple = oldCenter.get(i);
            double[] newCenterTuple = newCenter.get(i);
            double difference = Math.abs((oldCenterTuple[i] - newCenterTuple[i]));
            double percentChange = (difference / oldCenterTuple[i]) * 100.0;
            //consider all centers together. none or all rule.
            if(percentChange <= threshold){
                hasChanged = false;
            }
            else if(percentChange > threshold){
                hasChanged = true;
            }
        }
    }

    private void updateClusterMean(){
        newCenter = new ArrayList<>();
        for(List<double[]> cluster : clusters){
            int clusterSize = cluster.size();
            double[] sum = new double[numOfAttributes];
            for(double[] tuple : cluster){
                for(int i = 0; i < numOfAttributes; i++){
                    sum[i] += tuple[i];
                }
            }
            double[] average = new double[numOfAttributes];
            for(int i = 0; i < numOfAttributes; i++){
                average[i] = sum[i] / clusterSize;
            }
            newCenter.add(average);
        }

    }
    private void assignCluster() {
        for (double[] tuple : data) {
            double minDist = Double.MAX_VALUE;
            int pos = 0;
            //System.out.println("Current cluster size : " + centers.size());
            for (int curCluster = 0; curCluster < clusters.size(); curCluster++) {
                List<double[]> cluster = clusters.get(curCluster);
                double[] centroid = cluster.get(0);//this is the centeroid, always at the beginning of the list;
                double distance = euclideanDistance(centroid, tuple);
                if (distance < minDist) {
                    minDist = distance;
                    pos = curCluster;
                }
            }
            clusters.get(pos).add(tuple);
        }
    }

    public double euclideanDistance(double[] centroid, double[] tuple){
        double total = 0, difference;
        for(int i = 0; i < numOfAttributes; i++){
            difference = centroid[i] - tuple[i];
            total += difference * difference;
        }
        return  Math.sqrt(total);
    }

    private void createClusters(){
        clusters = new ArrayList<>();
        for(double[] centroid : oldCenter){
            List<double[]> tempList = new ArrayList<>();
            tempList.add(centroid);
            clusters.add(tempList);
        }
    }

    private void initialSetup(){
        setInitialoldCenter();
    }

    private void setInitialoldCenter(){
        oldCenter = new ArrayList<>();
        for(int i = 0; i < k; i++){
            int random = (int) (Math.random()*dataSize);
            oldCenter.add(data.get(random));
        }
    }


    //from normalized data, set k random initial centeroids
//    public void setInitialPoints(){
//        newCenter = new ArrayList<double[]>();
//        for(int i = 0; i < k; i++){
//            int random = (int) (Math.random() * dataSize);
//            newCenter.add(normalizedData.get(random));
//        }
//    }

    //-----------------initial----------

    public void printData() {
        for (double[] tuple : data) {
            System.out.println(Arrays.toString(tuple));
        }
    }
    public List<List<double[]>> getClusters(){
        return clusters;
    }

    public void printClusters(){
        int count=0;
        for(List<double[]>cluster: clusters){
            System.out.println("This is cluster number " + count + ".\n\n");
            System.out.println("The size of this cluster is " + cluster.size() + ".");
            for(double[] x : cluster ){
                System.out.println(Arrays.toString(x));
            }
            count++;
        }
    }

    public String getAttributeDescriptions(){
        return attributeDescriptions;
    }



}
