import java.util.Arrays;

/**
 * Created by johnbaik on 3/31/17.
 */
public class PokemonParserTest {
    public static void main(String[] args){
        PokemonParser pokemon = new PokemonParser("/Users/johnbaik/IdeaProjects/Pokemon_data_mining/src/pokemon.csv");
//        for(String[] data : pokemon.getData()){
//            System.out.println(Arrays.toString(data));
//        }
        pokemon.printDoubleLists(pokemon.getDoubleAttributes());
        System.out.println();
        System.out.println();
        pokemon.printStringList(pokemon.getStringAttributes());

    }

}
