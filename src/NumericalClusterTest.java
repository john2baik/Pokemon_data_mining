import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by johnbaik on 4/5/17.
 */
public class NumericalClusterTest {
    public static void main(String[] args){
        ParserNormalizer pokemon = new ParserNormalizer("/Users/johnbaik/IdeaProjects/Pokemon_data_mining/src/pokemon.csv");

        //now try to do with given 2 attributes
        int x=0, y=0, numOfClusters = 0;
        List<String> attributeNames = pokemon.getNumericAttributeNames();
        for(int i = 0; i < attributeNames.size(); i++){
            System.out.print(i + ". " + attributeNames.get(i) + "  ");
        }
        System.out.println();
        Scanner sc = new Scanner(System.in);

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

        CSVFileMaker file = new CSVFileMaker(dosAttributes.getClusters(),attributeNames, x, y);
        dosAttributes.printClusters();
    }
}
