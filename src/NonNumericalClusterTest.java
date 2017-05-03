import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by johnbaik on 4/23/17.
 */
public class NonNumericalClusterTest {
    public static void main(String[] args){
        ParserNormalizer pokemon = new ParserNormalizer("/Users/johnbaik/IdeaProjects/Pokemon_data_mining/src/pokemon.csv");
        List<HashMap<String, Integer>> data = pokemon.getalphbeticAttributes();
        List<Double> total = pokemon.getTotal();
        List<String> attributeNames = pokemon.getAlphbeticAttrNames();
        List<String[]> tuples = pokemon.getData();

        for(String[] x: tuples){
            System.out.println(Arrays.toString(x));
        }


        NonNumericalCluster fourth = new NonNumericalCluster(data, total, attributeNames,tuples, 4);

        HashMap<String, Double> fourthMap = fourth.getTypesTotal();
        for(String key : fourthMap.keySet()){
            System.out.println("The Average for " + key + " is " + fourthMap.get(key));
        }

    }
}

