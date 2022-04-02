package org.example.userstest;

import io.restassured.response.Response;
import org.example.entities.Category;
import org.example.entities.Pet;
import org.example.entities.Tag;
import org.example.entities.enums.PetStatus;
import org.example.steps.PetServiceSteps;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;


public class PetServiceTest {
    Pet expectedPet = createPet();

    @Test
    public void addPetTest(){
        Response responseCreatedPet = PetServiceSteps.addPet(expectedPet);
        Assert.assertEquals(responseCreatedPet.getStatusCode(), 200,
                "Incorrect order status code");
        Assert.assertEquals(responseCreatedPet.as(Pet.class).getId(), expectedPet.getId(),
                "Incorrect pet id");
    }

    @Test
    public void getPetById(){
        Response responseCreatedPet = PetServiceSteps.addPet(expectedPet);
        Pet createdPet = PetServiceSteps.getPetById(expectedPet.getId()).as(Pet.class);
        Assert.assertEquals(createdPet.getName(), expectedPet.getName());
    }

    @Test
    public void deletePetById(){
        Response responseCreatedPet = PetServiceSteps.addPet(expectedPet);
        Assert.assertEquals(responseCreatedPet.getStatusCode(), 200,
                "Incorrect status code of response of user creation");
        PetServiceSteps.deletePetById(expectedPet.getId());
        Response responseDeletedPet = PetServiceSteps.getPetById(expectedPet.getId());
        Assert.assertEquals(responseDeletedPet.getStatusCode(), 404);
    }









    private Pet createPet() {
        return new Pet()
                .setId(1)
                .setCategory(new Category()
                        .setId(1)
                        .setName("cat"))
                .setName("Murcia")
                .setPhotoUrls(new String[]{"url"})
                .setTags(new ArrayList<Tag>(Arrays.asList(new Tag()
                        .setId(1)
                        .setName("black"))))
                .setStatus(PetStatus.AVAILABLE.toString());
    }
}
