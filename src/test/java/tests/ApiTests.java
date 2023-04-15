package tests;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.qameta.allure.Epic;
import io.qameta.allure.restassured.AllureRestAssured;
import models.PokemonFromListModel;
import models.PokemonModel;
import helpers.PokemonDeserializer;
import helpers.PokemonSerializer;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;
import static io.restassured.RestAssured.given;


@Epic("PokemonsApiTests")
public class ApiTests {
    private final String pageUrl = "https://pokeapi.co/api/v2/pokemon";
    private final String rattataUri="/rattata";
    private final String pidgeottoUri="/pidgeotto";

    /**
     * Настройка Gson
     */
    Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(PokemonModel.class, new PokemonSerializer())
            .registerTypeAdapter(PokemonModel.class, new PokemonDeserializer())
            .create();

    @Test(description = "Проверка веса и умений покемонов Раттата и Педжиотто")
    public void pokemonTest() {
        Specifications.installSpecification(Specifications.requestSpec(pageUrl), Specifications.responseSpec200());
        PokemonModel pokemonRattata = gson.fromJson(
                given().filter(new AllureRestAssured()).get(rattataUri).then().extract().asString(),
                PokemonModel.class);
        PokemonModel pokemonPedgiotto = gson.fromJson(
                given().filter(new AllureRestAssured()).get(pidgeottoUri).then().extract().asString(),
                PokemonModel.class);

        Assert.assertTrue(pokemonRattata.getWeight() < pokemonPedgiotto.getWeight(),
                "Вес покемона Раттата больше или равен весу Пиджеотто");
        Assert.assertTrue(pokemonRattata.getAbilities().contains("run-away"), "Ожидаемого умения нет");
    }

    @Test(description = "Установка лимита в списке покемонов, проверка наличия имен")
    public void pokemonLimitTest() {
        Specifications.installSpecification(Specifications.requestSpec(pageUrl), Specifications.responseSpec200());
        int limit = 10;
        List<PokemonFromListModel> pokemons = given()
                .filter(new AllureRestAssured())
                .queryParam("offset", 0)
                .queryParam("limit", limit)
                .when().get().then().extract().body().jsonPath().getList("results", PokemonFromListModel.class);

        pokemons.forEach(item -> Assert.assertNotNull(item.getName()));
        Assert.assertEquals(pokemons.size(), limit, "Количество покемонов не соответствует установленному лимиту");
    }
}

