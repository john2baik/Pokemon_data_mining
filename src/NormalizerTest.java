/**
 * Created by johnbaik on 3/31/17.
 */
import java.util.List;
public class NormalizerTest {
    public static void main(String[] args){
        PokemonParser pokemon = new PokemonParser("/Users/johnbaik/IdeaProjects/Pokemon_data_mining/src/pokemon.csv");
        List<double[]> doubleAttributes = pokemon.getDoubleAttributes();
        List<String[]> stringAttributes = pokemon.getStringAttributes();
    }
}
