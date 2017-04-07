import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by johnbaik on 4/5/17.
 */
public class NumericalClusterTest {
    public static void main(String[] args){
        ParserNormalizer pokemon = new ParserNormalizer("/Users/johnbaik/IdeaProjects/Pokemon_data_mining/src/pokemon.csv");
        String attributeDescriptions = pokemon.getAttributeDescriptions();
        NumericalCluster numCluster = new NumericalCluster(pokemon.getDoubleAttributes(), pokemon.getAttributeDescriptions(), pokemon.getMaxiNumOfAttribs(), pokemon.getMiniNumOfAttribs(), 5, -1, -1);
        //numCluster.printData();
       // System.out.println(numCluster.getAttributeDescriptions());
      //  numCluster.printClusters();


        //now try to do with given 2 attributes
        int x=0, y=0;
        Scanner sc = new Scanner(System.in);
        double[] goodExample = pokemon.getDoubleAttributes().get(0);

        System.out.println("this is the example\n" + Arrays.toString(goodExample));
        String attributeList = pokemon.getAttributeDescriptions();
        String[] attributes = attributeList.split(",");
        int count = 0;

        for(int i = 0; i < attributes.length; i++){
            if(goodExample[i] > 0){
                System.out.println(count++ + " : " + attributes[i] );

            }
        }

        System.out.println("Please input 2 numbers that you would like to see the correlation:\n");
        x = sc.nextInt();
        y = sc.nextInt();

        NumericalCluster dosAttributes = new NumericalCluster(pokemon.getDoubleAttributes(), pokemon.getAttributeDescriptions(), pokemon.getMaxiNumOfAttribs(), pokemon.getMiniNumOfAttribs(), 5, x, y);

    }
}
