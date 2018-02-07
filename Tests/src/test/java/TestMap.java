import org.junit.Before;
import org.junit.Test;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class TestMap {

    private Client client;
    private WebTarget target;

    private String location;

    @Before
    public void initClient(){
        this.client = ClientBuilder.newClient();
        this.target = this.client.target("http://localhost:8080/GeoQuizz/api/maps");
    }
    
    @Test
    public void testPost() {
        JsonObjectBuilder partie = Json.createObjectBuilder();
        JsonObject partieJson = partie
                .add("distance","100" )
                .add("latitude", "6.656")
                .add("longitude", "49.555")
                .add("ville", "Bogota")
                .build();
        Response partieResponse = this.target
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(partieJson));
        location = partieResponse.getHeaderString("Location");
        assertThat(partieResponse.getStatus(), is(201));
    }

    @Test
    public void testGet() {
        JsonObject jsonRecupere = this.client
                .target(location)
                .request(MediaType.APPLICATION_JSON)
                .get(JsonObject.class);

        assertTrue(jsonRdecupere.getJsonObject("partie").containsKey("distance"));
        assertTrue(jsonRecupere.getJsonObject("partie").getString("distance").contains("100"));
        assertTrue(jsonRecupere.getJsonObject("partie").containsKey("latitude"));
        assertTrue(jsonRecupere.getJsonObject("partie").getString("latitude").contains("6.656"));
        assertTrue(jsonRecupere.getJsonObject("partie").containsKey("longitude"));
        assertTrue(jsonRecupere.getJsonObject("partie").getString("longitude").contains("49.555"));
        assertTrue(jsonRecupere.getJsonObject("partie").containsKey("ville"));
        assertTrue(jsonRecupere.getJsonObject("partie").getString("ville").contains("Bogota"));
    }

    @Test
    public void testDelete() {
        Response deleteResponse = this.client
                .target(location)
                .request()
                .delete();
        assertThat(deleteResponse.getStatus(), is(204));
    }
}
