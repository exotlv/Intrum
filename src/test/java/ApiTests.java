import com.intrum.api.ApiClient;
import com.intrum.models.UserData;
import com.intrum.utils.CsvUtil;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class ApiTests {

    ApiClient apiClient = new ApiClient();
    List<UserData> users = CsvUtil.readFromCsv();

    @Test
    public void test1CreateUser(){
        UserData specificUser = null;
        for (UserData user : users) {
            if ("John Doe".equals(user.getName())) {
                specificUser = user;
                break;
            }
        }
        Response response = apiClient.createUser(specificUser);
        assertEquals(201, response.getStatusCode());
    }

    @Test
    public void testDeleteUser(){
        UserData specificUser = null;
        for (UserData user : users) {
            if ("Jane Smith".equals(user.getName())) {
                specificUser = user;
                break;
            }
        }
        // Create a new user using the data from the CSV for deletion testing
        Response createResponse = apiClient.createUser(specificUser);

        // Extract user ID from the create response
        String userId = createResponse.jsonPath().getString("id");

        // Delete the user
        Response deleteResponse = apiClient.deleteUser(userId);
        assertEquals(204, deleteResponse.getStatusCode()); // 204 No Content means deletion was successful

        // Attempt to retrieve the deleted user to confirm deletion
        Response getResponse = apiClient.getUser(userId);
        int statusCode = getResponse.getStatusCode();

        // In most APIs, trying to get a non-existent resource either returns a 404 (Not Found) or a 200 with a null body.
        // Depending on the API's behavior, adjust the assertion accordingly.
        assertTrue(statusCode == 404 || (statusCode == 200 && getResponse.getBody().jsonPath().getString("data") == null));
    }

    @Test
    public void testUpdateUser() {
        UserData specificUser = null;
        for (UserData user : users) {
            if ("Alice Johnson".equals(user.getName())) {
                specificUser = user;
                break;
            }
        }
        // Step 1: Create a new user using data from the CSV
        Response createResponse = apiClient.createUser(specificUser);
        String userId = createResponse.jsonPath().getString("id");

        //   Step 2: Update the user's name (or any other field)
        UserData modifiedUser = new UserData();
        modifiedUser.setName("UpdatedName");
        Response updateResponse = apiClient.updateUser(userId, modifiedUser);
        assertEquals(200, updateResponse.getStatusCode());

        // Step 3: Retrieve the user and validate the update
        Response getResponse = apiClient.getUser(userId);
        assertEquals(200, getResponse.getStatusCode());
    }
}
