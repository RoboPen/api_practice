package org.example.userstest;

import io.restassured.response.Response;
import org.example.entities.User;
import org.example.steps.UserServiceSteps;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Random;

public class UserServiceTest {
    User expectedUser = createUser();

    @Test
    public void loginTest() {
        Response responseLogin = UserServiceSteps.login(expectedUser.getUsername(), expectedUser.getPassword());
        Assert.assertEquals(responseLogin.getStatusCode(), 200,
                "Incorrect status code");
    }

    @Test
    public void createUsersTest() {
        Response responseCreatedUser = UserServiceSteps.createUser(expectedUser);
        Assert.assertEquals(responseCreatedUser.getStatusCode(), 200,
                "Incorrect status code");
    }

    @Test
    public void getUserByUsernameTest() {
        Response responseCreatedUser = UserServiceSteps.createUser(expectedUser);
        User createdUser = UserServiceSteps.getUserByUsername(expectedUser.getUsername()).as(User.class);
        Assert.assertEquals(createdUser.getId(), expectedUser.getId(),
                "Incorrect user id");
    }

    @Test
    public void getUserByUsernameAfterDeletionTest() {
        Response responseCreatedUser = UserServiceSteps.createUser(expectedUser);
        Assert.assertEquals(responseCreatedUser.getStatusCode(), 200,
                "Incorrect status code of response of user creation");
        UserServiceSteps.deleteUserByUsername(expectedUser.getUsername());
        Response responseDeletedUser = UserServiceSteps.getUserByUsername(expectedUser.getUsername());
        Assert.assertEquals(responseDeletedUser.getStatusCode(), 404,
                "User not deleted or invalid username");
    }

    private User createUser() {
        Random random = new Random();
        return new User()
                .setId(random.nextInt(1000))
                .setUsername("user" + random.nextInt(1000))
                .setFirstName("John" + random.nextInt(100))
                .setLastName("Smith" + random.nextInt(100))
                .setEmail("testEmail" + random.nextInt(100) + "@gmail.com")
                .setPassword("Kentucky11")
                .setPhone("8976500" + (random.nextInt(90) + 10))
                .setUserStatus(1);
    }
}


