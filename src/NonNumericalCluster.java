import java.util.HashMap;
import java.util.List;

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
        for(int i = 0; i < tuples.size(); i++){
            System.out.println(tuples.get(i)[index]);
            type.set(i, tuples.get(i)[index]);
        }
    }

    private void mapTypeTotal(){
        for(int i = 0; i < type.size(); i++){
            String typeName = type.get(i);
            Double val = total.get(i);
            if(typeMap.get(i) == null){
                typeMap.put(typeName, val);
            }
            else{
                Double sum = typeMap.get(type) + val;
                typeMap.put(typeName, sum);
            }
        }
    }

    private void averageType(int index){
        for(String type : typeMap.keySet()){
            int numOfType = typeCounts.get(index).get(type);
            Double totalSum = typeMap.get(type);
            Double average = totalSum/numOfType;
            typesTotal.put(type, average);
        }
    }



    public HashMap<String, Double> getTypesTotal(){
        return typesTotal;
    }

}
