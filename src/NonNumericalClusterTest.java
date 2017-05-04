import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


/*to make a NonNumericalCluster object, pass in the following in this order:
    List<HashMap<String, Integer>> typeCounts - leslie's list of hashmaps containing the count of each specific attribute and their types
     List<Double> total - the list of the total from parsernormalizer
     List<String> attributeNames - The attribute names of the categorical types
     List<String[]> tuples - the csv file represeted as a List of string arrays
     int category - the index of the category you are prompted to use
*/
public class NonNumericalClusterTest {
    public static void main(String[] args){
        ParserNormalizer pokemon = new ParserNormalizer("/Users/johnbaik/IdeaProjects/Pokemon_data_mining/src/pokemon.csv");
        List<HashMap<String, Integer>> data = pokemon.getStringAttributes();
        List<Double> total = pokemon.getTotal();
        List<String> attributeNames = pokemon.getAlphbeticAttrNames();
        List<String[]> tuples = pokemon.getData();

        //printing data
//        for(HashMap<String, Integer> x : data){
//            for(String key : x.keySet()){
//                System.out.println(key + " : " + x.get(key));
//            }
//        }

        //printing total
//        for(Double x : total){
//            System.out.println(x);
//        }


        //make sure these all work fine?
//        for(int i = 0; i < attributeNames.size(); i++){
//            System.out.println( attributeNames.get(i));
//        }

//        for(String[] x : tuples){
//            System.out.println(Arrays.toString(x));
//        }
        int p = 1;
        for(String attr: attributeNames){
            System.out.println(p + ". " +  attr);
            p++;

        }


        System.out.println();
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the attribute number you are interested in.\n");
        int x = sc.nextInt();
        String attribute = attributeNames.get(x);

        NonNumericalCluster ex = new NonNumericalCluster(data, total, attributeNames,tuples, x);

        HashMap<String, Double> map = ex.getTypesTotal();
        CSVFileMaker file = new CSVFileMaker(map, attribute);

    }


}

