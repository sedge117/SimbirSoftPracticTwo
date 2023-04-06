package helpers;

import com.google.gson.*;
import models.PokemonModel;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс, содержащий метод для десериализации покемона из Json элемента в класс PokemonModel
 * с параметрами name, weight, abilities
 */
public class PokemonDeserializer implements JsonDeserializer<PokemonModel> {
    @Override
    public PokemonModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String name;
        int weight;
        List<String> abilities = new ArrayList<>();
        name = json
                .getAsJsonObject().get("forms")
                .getAsJsonArray().get(0)
                .getAsJsonObject().get("name")
                .getAsString();
        weight = json
                .getAsJsonObject().get("weight")
                .getAsInt();
        json.getAsJsonObject().get("abilities")
                .getAsJsonArray().forEach(
                        item-> abilities.add(
                                item.getAsJsonObject().get("ability")
                                        .getAsJsonObject().get("name")
                                        .getAsString()
                        )
                );
        return new PokemonModel(name, weight, abilities);
    }
}
