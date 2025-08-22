package models;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetModel {
    long id;
    PetCategoryModel category;
    String name;
    String[] photoUrls;
    PetTagModel[] tags;
    String status;

    public PetModel(Faker faker) {
        this.id = 0L;
        this.category = new PetCategoryModel(faker.number().numberBetween(0L, Long.MAX_VALUE), faker.animal().name());
        this.name = faker.dog().name();

        int k = faker.number().numberBetween(0, 5);
        this.photoUrls = new String[k];
        for (int i = 0; i < k; i++) {
            this.photoUrls[i] = faker.internet().url();
        }

        k = faker.number().numberBetween(0, 5);
        this.tags = new PetTagModel[k];
        for (int i = 0; i < k; i++) {
            this.tags[i] = new PetTagModel(faker.number().numberBetween(0L, Long.MAX_VALUE), faker.color().name());
        }

        this.status = faker.options().option(PetStatus.class).getStatus();
    }
}