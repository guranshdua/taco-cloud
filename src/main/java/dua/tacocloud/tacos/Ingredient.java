package dua.tacocloud.tacos;
import lombok.Data;

@Data
public class Ingredient {
    private final String id;
    private final String name;
    private final Type type;

    public enum Type{
        PROTEIN,CHEESE,WRAP,VEGGIES,SAUCE
    }
}
