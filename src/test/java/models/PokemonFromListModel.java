package models;
import lombok.Data;

/**
 * Класс описывает модель покемонов, извлекаемых из списка. Содержит имя покемона
 */
@Data
public class PokemonFromListModel {
    private final String name;
}
