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
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class TestPartie {

    private Client client;
    private WebTarget target;

    private String location;

    @Before
    public void initClient(){
        this.client = ClientBuilder.newClient();
        this.target = this.client.target("http://localhost:8080/GeoQuizz/api/parties");
    }
    
    @Test
    public void testPost() {
        JsonObjectBuilder partie = Json.createObjectBuilder();
        JsonObject partieJson = partie
                .add("nom", "bio")
                .add("b", "un sandwich bio")
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
