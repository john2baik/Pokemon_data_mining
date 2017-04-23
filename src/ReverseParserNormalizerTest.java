import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ReverseParserNormalizerTest {
    public static void main(String[] args){
        if (args.length != 3) {
            System.out.println("Usage: PokemonParserTest dataFileName outputFile reverseOutputFile");
            return;
        }
        String dataFileName = args[0];
        String output = args[1];
        String reverseOutput = args[2];
        ReverseParserNormalizer pokemon = new ReverseParserNormalizer(dataFileName, output, reverseOutput); //("/Users/johnbaik/IdeaProjects/Pokemon_data_mining/src/pokemon.csv");

        pokemon.printReverseLists(pokemon.getReverseDoubleAttributes(), reverseOutput);
    }

}
