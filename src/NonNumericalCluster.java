import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by johnbaik on 4/23/17.
 */
public class NonNumericalCluster {
    private List<HashMap<String, Integer>> typeCounts;//leslie's list of hashmaps
    private List<String> attributeNames; //array of the attribute names
    private List<Double> total; //list of totals from ParserNormalizer
    private List<String[]> tuples; //list of data from original csv
    private List<String> type; //
    private HashMap<String, Double> typeMap;//
    private HashMap<String, Double> typesTotal;
    private int category;

    public NonNumericalCluster(List<HashMap<String, Integer>> typeCounts, List<Double> total, List<String> attributeNames, List<String[]> tuples, int category){
        this.typeCounts = typeCounts;
        this.attributeNames = attributeNames;
        this.total = total;
        this.tuples = tuples;
        this.category = category;

        parseType(category);
        mapTypeTotal();
        averageType(category);
    }

    //pass in the type index, make into list of one attribute
    private void parseType(int index){
        type = new ArrayList<String>(721);
        for(int i = 0; i < tuples.size(); i++){
         //   System.out.println(tuples.get(i+1)[index]);
            type.add(i, tuples.get(i)[index]);
        }
    }

    private void mapTypeTotal(){
        typeMap = new HashMap<String, Double>();
        for(int i = 0; i < type.size(); i++){
            String typeName = type.get(i);
            Double val = total.get(i);
            System.out.println("type: " + typeName + "  " + " total: " + val);
            if(typeMap.containsKey(typeName)){
                typeMap.put(typeName, typeMap.get(typeName) + val);
            }
            else{
                typeMap.put(typeName, val);
                //  System.out.println("put " + typeName + " in the map. val is " + val);
             //   System.out.println("updated " + typeName + " total to " + sum);
            }
        }
    }

    private void averageType(int index){
        typesTotal = new HashMap<String, Double>();
        for(String type : typeMap.keySet()){
            int numOfType = typeCounts.get(index).get(type);
            Double totalSum = typeMap.get(type);

            System.out.println("numOfType" + " : " + numOfType + "   totalSum = " + totalSum);
            Double average = totalSum/numOfType;
            typesTotal.put(type, average);
        }
    }



    public HashMap<String, Double> getTypesTotal(){
        return typesTotal;
    }

}
