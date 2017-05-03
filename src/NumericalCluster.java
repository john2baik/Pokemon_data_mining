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
    private List<double[]> oldCenter;
    private List<double[]> newCenter;
    private int dataSize;
    private int k;
    private boolean hasChanged;
    private int numOfAttributes;
    private final double threshold = 1.0;

    //for only 2 attributes
    int attribute1;
    int attribute2;
    List<double[]> couple;


    /*
    Set up constructor so that it will run the clustering based on
     */

    public NumericalCluster(List<double[]> data, String attributeDescriptions, double[] max, double[] min, int k, int attribute1, int attribute2){
        this.data = data;
        this.attributeDescriptions = attributeDescriptions;
        dataSize = data.size();
        this.k = k;
        hasChanged = true;
        numOfAttributes = data.get(0).length;
        this.attribute1 = attribute1;
        this.attribute2 = attribute2;
        //printData();
        run();
        // System.out.println(attributeDescriptions);
    }



    private void extractCouple(){
        couple = new ArrayList<>();
        for(double[] tuple : data){
            // System.out.println(tuple[attribute1] + tuple[attribute2]);
            double[] arr = {tuple[attribute1], tuple[attribute2]};
            couple.add(arr);
        }

        data = new ArrayList<> (couple);
        dataSize = couple.size();
        numOfAttributes = 2;
    }

    private void run(){
        if(attribute1 < 0 && attribute2 < 0){
            initialSetup();
            cluster();
        }
        else{
            extractCouple();
            //printData();
            initialSetup();
            // printCenters(oldCenter);
            cluster();
        }

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
        for(int i = 0; i < data.get(0).length; i++){
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

    /*
    Creates the new Centers
     */
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

    /*
    For each tuple in the data, adds it to each individual cluster
     */
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

    private void printCenters(List<double[]> Center){
        for(double[] x : Center){
            System.out.println(Arrays.toString(x));
        }
    }

    public int getAttribute1(){
        return attribute1;
    }

    public int getAttribute2(){
        return attribute2;
    }

}