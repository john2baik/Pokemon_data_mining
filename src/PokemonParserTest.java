import java.util.Arrays;

/**
 * Created by johnbaik on 3/31/17.
 */
public class PokemonParserTest {
    public static void main(String[] args){
        if (args.length != 2) {
            System.out.println("Usage: PokemonParserTest dataFileName outputFile reverseOutputFile");
            return;
        }
        String dataFileName = args[0];
        String output = args[1];

        ParserNormalizer pokemon = new ParserNormalizer(dataFileName); //("/Users/johnbaik/IdeaProjects/Pokemon_data_mining/src/pokemon.csv");
//        for(String[] data : pokemon.getData()){
//            System.out.println(Arrays.toString(data));
//        }
        pokemon.printDoubleLists(pokemon.getDoubleAttributes(), output);
        System.out.println();
        System.out.println();
        pokemon.printStringList(pokemon.getStringAttributes(), output);

    }

}
