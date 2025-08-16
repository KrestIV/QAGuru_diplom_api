package apitests;

import models.ConfirmMessageModel;
import models.PetModel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static io.qameta.allure.Allure.step;
import static specs.RequestSpec.*;

@Tag("APITests")
@Tag("FullTest")
public class APITests extends APIBaseTests {


    @Test
            //@Tag("user_tests")
    public void addingPetMustReturnCorrectAnswer() {
        PetModel pet = step("Подготовить данные питомца для добавления в базу", () ->
                new PetModel());


        PetModel petResult = step("Послать данные питомца в базу и получить подтверждение", () ->
                given(requestWithJsonContentSpec)
                        .body(pet)
                        .when()
                        .post("/")
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
    public void gettingPetMustReturnPet() {
        PetModel pet = step("Подготовить данные питомца для добавления в базу", () ->
                new PetModel());

        long id = step("Послать данные питомца в базу и получить присвоенный ему id", () ->
                given(requestWithJsonContentSpec)
                        .body(pet)
                        .when()
                        .post("/")
                        .then()
                        .spec(responseSpec(200))
                        .extract().as(PetModel.class).getId());

        PetModel petResult = step("Получить данные питомца по его id", () -> given(requestNoContentSpec)
                .when()
                .get("/" + id)
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
    public void deletingPetMustReturnCorrectResponse() {
        PetModel pet = step("Подготовить данные питомца для добавления в базу", () ->
                new PetModel());

        long id = step("Послать данные питомца в базу и получить присвоенный ему id", () ->
                given(requestWithJsonContentSpec)
                        .body(pet)
                        .when()
                        .post("/")
                        .then()
                        .spec(responseSpec(200))
                        .extract().as(PetModel.class).getId());

        ConfirmMessageModel deleteMessage = step("Удалить питомца из базы и получить подтверждение удаления", () ->
                given(requestNoContentSpec)
                        .when()
                        .delete("/" + id)
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
    public void editPetNameMustSaveInBase() {
        PetModel pet = step("Подготовить данные питомца для добавления в базу", () ->
                new PetModel());

        long id = step("Послать данные питомца в базу и получить присвоенный ему id", () ->
                given(requestWithJsonContentSpec)
                        .body(pet)
                        .when()
                        .post("/")
                        .then()
                        .spec(responseSpec(200))
                        .extract().as(PetModel.class).getId());

        step("Подготовить измененные данные питомца", () -> {
            pet.setName("BillBurr");
            pet.setId(id);
        });

        String nameResult = step("Послать измененные данные питомца в базу и получить новое имя", () ->
                given(requestWithJsonContentSpec)
                .body(pet)
                .when()
                .put("/")
                .then()
                .spec(responseSpec(200))
                .extract().as(PetModel.class).getName());

        step("Проверить измененное имя питомца", () -> {
            assertThat(nameResult).isEqualTo("BillBurr");
        });


    }

    @Test
    public void editThroughPostPetMustSaveInBase() {
        PetModel pet = step("Подготовить данные питомца для добавления в базу", () ->
                new PetModel());

        long id = step("Послать данные питомца в базу и получить присвоенный ему id", () ->
                given(requestWithJsonContentSpec)
                        .body(pet)
                        .when()
                        .post("/")
                        .then()
                        .spec(responseSpec(200))
                        .extract().as(PetModel.class).getId());

        ConfirmMessageModel changeMessage = step("Послать данные питомца в базу и получить присвоенный ему id", () ->
                given(requestWithFormContentSpec)
                .formParam("name","BillPurr")
                .formParam("status","sold")
                .when()
                .post("/" + id)
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
