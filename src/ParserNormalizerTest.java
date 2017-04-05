/**
 * Created by lpchou on 4/3/2017.
 */

import java.util.Arrays;
import java.util.HashMap;
import java.io.File;

public class ParserNormalizerTest {
    private String dataFileName;

    public static void main(String[] args) {
        String dataFileName = args[0];

        //ParserNormalizer pokemon = new ParserNormalizer("/Users/johnbaik/IdeaProjects/Pokemon_data_mining/src/pokemon.csv");
        ParserNormalizer pokemon = new ParserNormalizer(dataFileName);
        System.out.println(pokemon.getAttributeDescriptions());
        System.out.println("This is the normalized list of numerical attributes:");
        for (double[] tuple : pokemon.getDoubleAttributes()) {
            System.out.println(Arrays.toString(tuple));
        }

        System.out.println("this is the string attriubtes and their count");
        for (int i = 0; i < pokemon.getStringAttributes().size(); i++) {
            System.out.println("Attribute # " + i);
            HashMap<String, Integer> map = pokemon.getStringAttributes().get(i);
            for (String key : map.keySet()) {
                System.out.println(key + " : " + map.get(key));
            }
        }
    }
}
