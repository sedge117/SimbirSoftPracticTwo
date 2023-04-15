package models;
import lombok.Data;
import java.util.List;

/**
 * Класс описывает модель конкретного покемона. Содержит имя покемона, его вес и список умений.
 */
@Data
public class PokemonModel {
    private final String name;
    private final Integer weight;
    private final List<String> abilities;

}
