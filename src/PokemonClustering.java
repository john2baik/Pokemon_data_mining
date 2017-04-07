/**
 * Created by johnbaik on 4/7/17.
 */
public class PokemonClustering {

    public static void main(String[] args){
        ParserNormalizer pokemon = new ParserNormalizer("/Users/johnbaik/IdeaProjects/Pokemon_data_mining/src/pokemon.csv");
        String attributeDescriptions = pokemon.getAttributeDescriptions();
       // NumericalCluster numCluster = new NumericalCluster(pokemon.getDoubleAttributes(), pokemon.getAttributeDescriptions(), pokemon.getMaxiNumOfAttribs(), pokemon.getMiniNumOfAttribs(), 5);
        //numCluster.printData();
        //System.out.println(numCluster.getAttributeDescriptions());
        //
        //numCluster.printClusters();


    }
}
