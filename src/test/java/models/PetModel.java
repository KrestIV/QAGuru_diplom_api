package models;

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
}