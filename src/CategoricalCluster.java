import java.util.List;

/**
 * Created by johnbaik on 4/4/17.
 */
public class CategoricalCluster {
    private List<String[]> data;
    private String attributeList;

    public CategoricalCluster(List<String[]> stringAttributes, String attributes){
        data = stringAttributes;
        attributeList = attributes;
    }
}
