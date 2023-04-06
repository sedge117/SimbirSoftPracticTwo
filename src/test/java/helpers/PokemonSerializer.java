package helpers;
import com.google.gson.*;
import models.PokemonModel;
import java.lang.reflect.Type;

public class PokemonSerializer implements JsonSerializer<PokemonModel> {
    @Override
    public JsonElement serialize(PokemonModel src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        JsonArray abilities = new JsonArray();
        src.getAbilities().forEach(abilities::add);

        result.addProperty("name", src.getName());
        result.addProperty("weight", src.getWeight());
        result.add("abilitiesNames", abilities);

        return result;
    }
}
