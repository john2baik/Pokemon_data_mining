import java.util.List;

/**
 * Created by johnbaik on 4/5/17.
 */
public class NumericalClusterTest {
    public static void main(String[] args){
        ParserNormalizer pokemon = new ParserNormalizer("/Users/johnbaik/IdeaProjects/Pokemon_data_mining/src/pokemon.csv");
        String attributeDescriptions = pokemon.getAttributeDescriptions();
        NumericalCluster numCluster = new NumericalCluster(pokemon.getDoubleAttributes(), pokemon.getAttributeDescriptions(), pokemon.getMaxiNumOfAttribs(), pokemon.getMiniNumOfAttribs();
        numCluster.printData();


    }
}
