package helpers;

import com.github.javafaker.Faker;
import models.PetCategoryModel;
import models.PetModel;
import models.PetStatus;
import models.PetTagModel;

public class PetGenerator {
    private static final Faker faker = new Faker();

    public static PetModel generateRandomPet() {

        return new PetModel(0L,
                generatePetCategory(),
                faker.dog().name(),
                generatePetPhotoUrls(),
                generatePetTags(),
                faker.options().option(PetStatus.class).getStatus());
    }

    public static PetCategoryModel generatePetCategory() {
        return new PetCategoryModel(faker.number().numberBetween(0L, Long.MAX_VALUE), faker.animal().name());
    }

    public static String[] generatePetPhotoUrls() {
        int k = faker.number().numberBetween(0, 5);
        String[] photoUrls = new String[k];
        for (int i = 0; i < k; i++) {
            photoUrls[i] = faker.internet().url();
        }
        return photoUrls;
    }

    public static PetTagModel[] generatePetTags() {
        int k = faker.number().numberBetween(0, 5);
        PetTagModel[] tags = new PetTagModel[k];
        for (int i = 0; i < k; i++) {
            tags[i] = new PetTagModel(faker.number().numberBetween(0L, Long.MAX_VALUE), faker.color().name());
        }
        return tags;
    }
}
