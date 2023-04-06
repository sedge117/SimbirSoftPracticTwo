package models;
import lombok.Data;
import java.util.List;

@Data
public class PokemonModel {
    private final String name;
    private final Integer weight;
    private final List<String> abilities;

}
