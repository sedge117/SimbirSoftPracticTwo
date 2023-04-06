package tests;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.qameta.allure.restassured.AllureRestAssured;
import models.Pocs;
import models.PokemonModel;
import helpers.PokemonDeserializer;
import helpers.PokemonSerializer;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;


public class ApiTests {
    private final String pageUrl = "https://pokeapi.co/api/v2/pokemon";
    private final String rattataUri="/rattata";
    private final String pidgeottoUri="/pidgeotto";



    Gson gson = new GsonBuilder() //куда-то его убрать надо
            .setPrettyPrinting()
            .registerTypeAdapter(PokemonModel.class, new PokemonSerializer())
            .registerTypeAdapter(PokemonModel.class, new PokemonDeserializer())
            .create();

    @Test(description = "")
    public void pokemonTest() {
        Specifications.installSpecification(Specifications.requestSpec(pageUrl), Specifications.responseSpec200());
        PokemonModel pokemonRattata = gson.fromJson(
                given().get(rattataUri).then().extract().asString(), PokemonModel.class);
        PokemonModel pokemonPedgiotto = gson.fromJson(
                given().get(pidgeottoUri).then().extract().asString(), PokemonModel.class);

        Assert.assertTrue(pokemonRattata.getWeight() < pokemonPedgiotto.getWeight(),
                "Вес покемона Раттата больше или равен весу Пиджеотто");
        Assert.assertTrue(pokemonRattata.getAbilities().contains("run-away"), "Ожидаемого умения нет");
    }

    @Test(description = "")
    public void pokemonLimitTest() {
        Specifications.installSpecification(Specifications.requestSpec(pageUrl), Specifications.responseSpec200());
        int limit = 1279;

        List<Pocs> pocs = given().filter(new AllureRestAssured()).queryParam("offset", 0)
                .queryParam("limit", limit)
                .when().get().then().extract().body().jsonPath().getList("results", Pocs.class);

        pocs.forEach(item -> Assert.assertNotNull(item.getName()));
        Assert.assertEquals(pocs.size(), limit,
                "Количество покемонов не соответствует установленному лимиту");
    }
}

