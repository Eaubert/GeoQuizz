import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Tests {

    private static Client client;

    @BeforeClass
    public static void onceExecutedBeforeAll() {
        client = ClientBuilder.newClient();
        System.out.println("@BeforeClass: onceExecutedBeforeAll");
    }

    @Test
    public void testMap() {
        Response mapResponse = client.target("http://localhost:8080/GeoQuizz/api/maps")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(Json.createObjectBuilder()
                        .add("ville", "Bogota")
                        .add("latitude", "15.25")
                        .add("longitude", "20.25")
                        .add("distance", "500")
                        .build()));
        assertThat(mapResponse.getStatus(), is(201));

        //Get Map
        JsonObject jsonRecupere = client
                .target(mapResponse.getHeaderString("Location"))
                .request(MediaType.APPLICATION_JSON)
                .get(JsonObject.class);

        assertTrue(jsonRecupere.containsKey("type"));
        assertTrue(jsonRecupere.getString("type").contains("resource"));
        assertTrue(jsonRecupere.containsKey("map"));
        assertTrue(jsonRecupere.getJsonObject("map").containsKey("id"));
        assertTrue(jsonRecupere.getJsonObject("map").containsKey("distance"));
        assertEquals(500, jsonRecupere.getJsonObject("map").getInt("distance"));
        assertTrue(jsonRecupere.getJsonObject("map").containsKey("latitude"));
        assertEquals(15.2, jsonRecupere.getJsonObject("map").getJsonNumber("latitude").doubleValue(), 1);
        assertTrue(jsonRecupere.getJsonObject("map").containsKey("longitude"));
        assertEquals(20.25, jsonRecupere.getJsonObject("map").getJsonNumber("longitude").doubleValue(), 1);
        assertTrue(jsonRecupere.getJsonObject("map").containsKey("ville"));
        assertTrue(jsonRecupere.getJsonObject("map").getString("ville").contains("Bogota"));
        assertTrue(jsonRecupere.containsKey("links"));
        assertTrue(jsonRecupere.getJsonObject("links").containsKey("self"));
        assertTrue(jsonRecupere.getJsonObject("links").getJsonObject("self").containsKey("href"));
        assertTrue(jsonRecupere.getJsonObject("links").getJsonObject("self").getString("href").contains("/maps/" + jsonRecupere.getJsonObject("map").getString("id")));
        assertTrue(jsonRecupere.getJsonObject("links").containsKey("photos"));
        assertTrue(jsonRecupere.getJsonObject("links").getJsonObject("photos").containsKey("href"));
        assertTrue(jsonRecupere.getJsonObject("links").getJsonObject("photos").getString("href").contains("/maps/" + jsonRecupere.getJsonObject("map").getString("id") + "/photos"));


        //On ajoute une map
        client.target("http://localhost:8080/GeoQuizz/api/maps")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(Json.createObjectBuilder()
                        .add("ville", "Rio")
                        .add("latitude", "16.77")
                        .add("longitude", "12.12")
                        .add("distance", "322")
                        .build()));


        jsonRecupere = client
                .target("http://localhost:8080/GeoQuizz/api/maps")
                .request(MediaType.APPLICATION_JSON)
                .get(JsonObject.class);


    }
    /*@Test
    public void testBgetMap() {
        JsonObject jsonRecupere = client
                .target(this.uri_map)
                .request(MediaType.APPLICATION_JSON)
                .get(JsonObject.class);

        assertTrue(jsonRecupere.getJsonObject("map").containsKey("id"));
        assertTrue(jsonRecupere.getJsonObject("map").containsKey("distance"));
        assertTrue(jsonRecupere.getJsonObject("map").getString("distance").contains("100"));
        assertTrue(jsonRecupere.getJsonObject("map").containsKey("latitude"));
        assertTrue(jsonRecupere.getJsonObject("map").getString("latitude").contains("6.656"));
        assertTrue(jsonRecupere.getJsonObject("map").containsKey("longitude"));
        assertTrue(jsonRecupere.getJsonObject("map").getString("longitude").contains("49.555"));
        assertTrue(jsonRecupere.getJsonObject("map").containsKey("ville"));
        assertTrue(jsonRecupere.getJsonObject("map").getString("ville").contains("Bogota"));
    }*//*

    @Test
    public void testGetPartie() {
        JsonObject jsonRecupere = client
                .target(this.uri_partie)
                .request(MediaType.APPLICATION_JSON)
                .get(JsonObject.class);

        assertTrue(jsonRecupere.getJsonObject("partie").containsKey("distance"));
        assertTrue(jsonRecupere.getJsonObject("partie").getString("distance").contains("100"));
        assertTrue(jsonRecupere.getJsonObject("partie").containsKey("latitude"));
        assertTrue(jsonRecupere.getJsonObject("partie").getString("latitude").contains("6.656"));
        assertTrue(jsonRecupere.getJsonObject("partie").containsKey("longitude"));
        assertTrue(jsonRecupere.getJsonObject("partie").getString("longitude").contains("49.555"));
        assertTrue(jsonRecupere.getJsonObject("partie").containsKey("ville"));
        assertTrue(jsonRecupere.getJsonObject("partie").getString("ville").contains("Bogota"));
    }
*/

   /* @Test
    public void testDelete() {
        Response deleteResponse = this.client
                .target(location)
                .request()
                .delete();
        assertThat(deleteResponse.getStatus(), is(204));
    }*/


}
