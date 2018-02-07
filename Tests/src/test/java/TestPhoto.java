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
import static org.junit.Assert.assertThat;

public class TestPhoto {

    private Client client, clientMap;
    private WebTarget target, targetMap;

    private String location;

    @Before
    public void init(){
        this.client = ClientBuilder.newClient();
        this.target = this.client.target("http://localhost:8080/GeoQuizz/api/photos?idMap=1");
        this.clientMap = ClientBuilder.newClient();
        this.targetMap = this.client.target("http://localhost:8080/GeoQuizz/api/maps");
    }
    
    @Test
    public void testPost() {
        JsonObjectBuilder partie = Json.createObjectBuilder();
        JsonObject partieJson = partie
                .add("url", "http://googleimage")
                .add("latitude", "15.2")
                .add("longitude", "20.25")
                .build();
        Response partieResponse = this.target
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(partieJson));
        location = partieResponse.getHeaderString("Location");
        assertThat(partieResponse.getStatus(), is(201));
    }

    /*@Test
    public void testGet() {
        JsonObject jsonRecupere = this.client
                .target(location)
                .request(MediaType.APPLICATION_JSON)
                .get(JsonObject.class);

        assertTrue(jsonRecupere.getJsonObject("partie").containsKey("nom"));
        assertTrue(jsonRecupere.getJsonObject("partie").getString("nom").contains("bio"));
        assertTrue(jsonRecupere.getJsonObject("partie").containsKey("description"));
        assertTrue(jsonRecupere.getJsonObject("partie").getString("description").contains("un sandwich bio"));
    }

    @Test
    public void testDelete() {
        Response deleteResponse = this.client
                .target(location)
                .request()
                .delete();
        assertThat(deleteResponse.getStatus(), is(204));
    }*/

   
}
