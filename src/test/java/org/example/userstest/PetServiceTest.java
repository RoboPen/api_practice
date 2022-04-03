package org.example.userstest;

import io.restassured.response.Response;
import org.example.entities.Category;
import org.example.entities.Pet;
import org.example.entities.Tag;
import org.example.entities.enums.PetStatus;
import org.example.log.Log;
import org.example.steps.PetServiceSteps;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Random;


public class PetServiceTest {
    Pet pet = createPet();

    @Test
    public void addPetTest() {
        Log.info("Pet creation test");
        Response responseCreatedPet = PetServiceSteps.addPet(pet);
        Assert.assertEquals(responseCreatedPet.getStatusCode(), 200,
                "Incorrect pet status code");
        Assert.assertEquals(responseCreatedPet.jsonPath().getInt("id"), pet.getId(),
                "Incorrect pet id");
        Log.info("Pet was successfully created");
    }

    @Test
    public void getPetByIdTest() {
        Log.info("Get pet by id test");
        Response responseCreatedPet = PetServiceSteps.addPet(pet);
        Pet createdPet = PetServiceSteps.getPetById(pet.getId()).as(Pet.class);
        Assert.assertEquals(responseCreatedPet.getStatusCode(), 200,
                "Incorrect pet status code");
        Assert.assertEquals(createdPet.getName(), pet.getName(),
                "Incorrect pet id");
        Log.info("Pet was successfully retrieved");
    }

    @Test
    public void updatePetWithFormDataTest() {
        Log.info("Update Pet with form data test");
        Response responseCreatedPet = PetServiceSteps.addPet(pet);
        Pet petToUpdate = updatePet(pet);
        Response responseUpdatedPet = PetServiceSteps.updatePet(petToUpdate);
        Pet updatedPet = PetServiceSteps.getPetById(pet.getId()).as(Pet.class);
        Assert.assertEquals(responseUpdatedPet.getStatusCode(), 200,
                "Pet was not updated");
        Assert.assertEquals(updatedPet.getName(), petToUpdate.getName(),
                "Name was not updated");
        Assert.assertEquals(updatedPet.getStatus(), petToUpdate.getStatus(),
                "Status was not updated");
        Log.info("Pet was successfully updated");
    }

    @Test
    public void deletePetByIdTest() {
        Log.info("Delete pet test");
        Response responseCreatedPet = PetServiceSteps.addPet(pet);
        PetServiceSteps.deletePetById(pet.getId());
        Response responseDeletedPet = PetServiceSteps.getPetById(pet.getId());
        Assert.assertEquals(responseDeletedPet.getStatusCode(), 404,
                "Pet not deleted or wrong id");
        Log.info("Pet was successfully deleted");
    }

    private Pet updatePet(Pet pet) {
        return pet
                .setId(pet.getId())
                .setName("Kirby")
                .setStatus(PetStatus.PENDING.toString());
    }

    private Pet createPet() {
        Random random = new Random();
        return new Pet()
                .setId(random.nextInt(10) + 1)
                .setCategory(new Category()
                        .setId(1)
                        .setName("cat"))
                .setName("Murcia")
                .setPhotoUrls(new String[]{"url"})
                .setTags(new Tag[]{new Tag()
                        .setId(1)
                        .setName("black")})
                .setStatus(PetStatus.AVAILABLE.toString());
    }
}
