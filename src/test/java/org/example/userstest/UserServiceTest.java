package org.example.userstest;

import io.restassured.response.Response;
import org.example.entities.User;
import org.example.log.Log;
import org.example.steps.UserServiceSteps;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Random;

public class UserServiceTest {
    User user = createUser();

    @Test
    public void loginTest() {
        Log.info("User login test");
        Response responseLogin = UserServiceSteps.login(user.getUsername(), user.getPassword());
        Assert.assertEquals(responseLogin.getStatusCode(), 200,
                "Incorrect status code");
        Log.info("Successful login");
    }

    @Test
    public void createUsersTest() {
        Log.info("User creation test");
        Response responseCreatedUser = UserServiceSteps.createUser(user);
        Assert.assertEquals(responseCreatedUser.getStatusCode(), 200,
                "Incorrect status code");
        Log.info("User was successfully created");
    }

    @Test
    public void getUserByUsernameTest() {
        Log.info("Get user test");
        Response responseCreatedUser = UserServiceSteps.createUser(user);
        User createdUser = UserServiceSteps.getUserByUsername(user.getUsername()).as(User.class);
        Assert.assertEquals(createdUser.getId(), user.getId(),
                "Incorrect user id");
        Assert.assertEquals(createdUser.getUsername(), user.getUsername(),
                "Incorrect username");
        Log.info("User was successfully retrieved");
    }

    @Test
    public void deleteUserTest() {
        Log.info("Delete user test");
        Response responseCreatedUser = UserServiceSteps.createUser(user);
        UserServiceSteps.deleteUserByUsername(user.getUsername());
        Response responseDeletedUser = UserServiceSteps.getUserByUsername(user.getUsername());
        Assert.assertEquals(responseDeletedUser.getStatusCode(), 404,
                "User not deleted or invalid username");
        Log.info("User was successfully deleted");
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


