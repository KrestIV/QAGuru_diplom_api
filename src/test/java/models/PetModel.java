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

        int k = faker.number().numberBetween(0,5);
        String[] urlArray = new String[k];
        for(int i = 0; i <= k; i++)
        {
            urlArray[i] = faker.internet().url();
        }

        k = faker.number().numberBetween(0,5);
        PetTagModel[] tagArray = new PetTagModel[k];
        for(int i = 0; i <= k; i++)
        {
            tagArray[i] = new PetTagModel(faker.number().numberBetween(0L, Long.MAX_VALUE), faker.color().name());
        }

//        this.photoUrls = new String[]{faker.internet().url(), faker.internet().url()};
//        this.tags = new PetTagModel[]{new PetTagModel(faker.number().numberBetween(0L, Long.MAX_VALUE), faker.color().name())};

        this.status = faker.options().option(PetStatus.class).getStatus();
    }
}