package apitests;

import com.github.javafaker.Faker;
import models.ConfirmMessageModel;
import models.PetModel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.RequestSpec.*;

@Tag("APITests")
@Tag("FullTest")
public class APITests extends APIBaseTests {
    public final Faker faker = new Faker();

    @Test
    @Tag("smoke")
    public void addingPetMustReturnCorrectAnswer() {
        PetModel pet = step("Подготовить данные питомца для добавления в базу", () ->
                new PetModel(faker));


        PetModel petResult = step("Послать данные питомца в базу и получить подтверждение", () ->
                given(requestWithJsonContentSpec)
                        .body(pet)
                        .when()
                        .post("/pet")
                        .then()
                        .spec(responseSpec(200))
                        .extract().as(PetModel.class));

        step("Проверить соответствие отправленных и сохраненных данных", () -> {
            assertThat(petResult.getId()).isNotEqualTo(0);
            assertThat(petResult.getCategory().getId()).isEqualTo(pet.getCategory().getId());
            assertThat(petResult.getCategory().getName()).isEqualTo(pet.getCategory().getName());
            assertThat(petResult.getPhotoUrls()).hasSameElementsAs(Arrays.stream(pet.getPhotoUrls()).toList());
            assertThat(petResult.getTags()).hasSameElementsAs(Arrays.stream(pet.getTags()).toList());
            assertThat(petResult.getStatus()).isEqualTo(pet.getStatus());
        });
    }

    @Test
    public void gettingPetMustReturnPet() throws InterruptedException {
        PetModel pet = step("Подготовить данные питомца для добавления в базу", () ->
                new PetModel(faker));

        long id = step("Послать данные питомца в базу и получить присвоенный ему id", () ->
                given(requestWithJsonContentSpec)
                        .body(pet)
                        .when()
                        .post("/pet")
                        .then()
                        .spec(responseSpec(200))
                        .extract().as(PetModel.class).getId());

        Thread.sleep(1000);

        PetModel petResult = step("Получить данные питомца по его id", () -> given(requestNoContentSpec)
                .when()
                .get("/pet/" + id)
                .then()
                .spec(responseSpec(200))
                .extract().as(PetModel.class));

        step("Проверить соответствие отправленных и сохраненных данных", () -> {
            assertThat(petResult.getId()).isEqualTo(id);
            assertThat(petResult.getCategory().getId()).isEqualTo(pet.getCategory().getId());
            assertThat(petResult.getCategory().getName()).isEqualTo(pet.getCategory().getName());
            assertThat(petResult.getPhotoUrls()).hasSameElementsAs(Arrays.stream(pet.getPhotoUrls()).toList());
            assertThat(petResult.getTags()).hasSameElementsAs(Arrays.stream(pet.getTags()).toList());
            assertThat(petResult.getStatus()).isEqualTo(pet.getStatus());
        });

    }

    @Test
    @Tag("smoke")
    public void deletingPetMustReturnCorrectResponse() throws InterruptedException {
        PetModel pet = step("Подготовить данные питомца для добавления в базу", () ->
                new PetModel(faker));

        long id = step("Послать данные питомца в базу и получить присвоенный ему id", () ->
                given(requestWithJsonContentSpec)
                        .body(pet)
                        .when()
                        .post("/pet")
                        .then()
                        .spec(responseSpec(200))
                        .extract().as(PetModel.class).getId());

        Thread.sleep(1000);

        ConfirmMessageModel deleteMessage = step("Удалить питомца из базы и получить подтверждение удаления", () ->
                given(requestNoContentSpec)
                        .when()
                        .delete("/pet/" + id)
                        .then()
                        .spec(responseSpec(200))
                        .extract().as(ConfirmMessageModel.class));

        step("Проверить сообщение об успехе операции", () -> {
            assertThat(deleteMessage.getCode()).isEqualTo("200");
            assertThat(deleteMessage.getType()).isNotNull();
            assertThat(deleteMessage.getMessage()).isEqualTo(String.valueOf(id));
        });

    }

    @Test
    public void editPetNameMustSaveInBase() throws InterruptedException {
        PetModel pet = step("Подготовить данные питомца для добавления в базу", () ->
                new PetModel(faker));

        long id = step("Послать данные питомца в базу и получить присвоенный ему id", () ->
                given(requestWithJsonContentSpec)
                        .body(pet)
                        .when()
                        .post("/pet/")
                        .then()
                        .spec(responseSpec(200))
                        .extract().as(PetModel.class).getId());
        Thread.sleep(1000);

        step("Подготовить измененные данные питомца", () -> {
            pet.setName("BillBurr");
            pet.setId(id);
        });

        String nameResult = step("Послать измененные данные питомца в базу и получить новое имя", () ->
                given(requestWithJsonContentSpec)
                        .body(pet)
                        .when()
                        .put("/pet")
                        .then()
                        .spec(responseSpec(200))
                        .extract().as(PetModel.class).getName());

        step("Проверить измененное имя питомца", () -> {
            assertThat(nameResult).isEqualTo("BillBurr");
        });


    }

    @Test
    @Tag("smoke")
    public void editThroughPostPetMustSaveInBase() throws InterruptedException {
        PetModel pet = step("Подготовить данные питомца для добавления в базу", () ->
                new PetModel(faker));

        long id = step("Послать данные питомца в базу и получить присвоенный ему id", () ->
                given(requestWithJsonContentSpec)
                        .body(pet)
                        .when()
                        .post("/pet")
                        .then()
                        .spec(responseSpec(200))
                        .extract().as(PetModel.class).getId());

        Thread.sleep(1000);

        ConfirmMessageModel changeMessage = step("Послать данные питомца в базу и получить присвоенный ему id", () ->
                given(requestWithFormContentSpec)
                        .formParam("name", "BillPurr")
                        .formParam("status", "sold")
                        .when()
                        .post("/pet/" + id)
                        .then()
                        .spec(responseSpec(200))
                        .extract().as(ConfirmMessageModel.class));

        step("Проверить сообщение об успехе операции", () -> {
            assertThat(changeMessage.getCode()).isEqualTo("200");
            assertThat(changeMessage.getType()).isNotNull();
            assertThat(changeMessage.getMessage()).isEqualTo(String.valueOf(id));
        });

    }
}
