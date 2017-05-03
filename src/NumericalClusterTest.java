import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by johnbaik on 4/5/17.
 */
public class NumericalClusterTest {
    public static void main(String[] args){
        //ParserNormalizer pokemon = new ParserNormalizer("/Users/johnbaik/IdeaProjects/Pokemon_data_mining/src/pokemon.csv");
        ParserNormalizer pokemon = new ParserNormalizer("/Users/lpchou/Documents/GitHub/Pokemon_data_mining/pokemon.csv");

        //now try to do with given 2 attributes
        int x=0, y=0, numOfClusters = 0;

//        for(int i = 0; i < pokemon.getDoubleAttributes().size(); i++){
//            System.out.println(Arrays.toString(pokemon.getDoubleAttributes().get(i)));
//        }
        List<String> white = pokemon.getNumericAttributeNames();
        for(String pink : white){
            if (pink!= null)
            System.out.print(pink + ", ");
        }
        System.out.println();

                    Scanner sc = new Scanner(System.in);

        List<Double> purple = pokemon.getTotal();
        System.out.print(purple);

        // System.out.println("this is the example\n" + Arrays.toString(goodExample));
        String attributeList = pokemon.getAttributeDescriptions();
        String[] attributes = attributeList.split(",");
        int count = 0;

        System.out.println("Please input 2 numbers that you would like to see the correlation:\n");
        x = sc.nextInt();
        y = sc.nextInt();
        System.out.println("\n");
        System.out.println("Please input the number of clusters you would like to have.\n");
        numOfClusters = sc.nextInt();


        NumericalCluster dosAttributes = new NumericalCluster(pokemon.getDoubleAttributes(), pokemon.getAttributeDescriptions(), pokemon.getMaxiNumOfAttribs(), pokemon.getMiniNumOfAttribs(), numOfClusters, x, y);
        dosAttributes.printClusters();

        //CSVFileMaker file = new CSVFileMaker(dosAttributes.getClusters(),dosAttributes.getAttributeDescriptions(), x, y);
        dosAttributes.printClusters();
    }
}