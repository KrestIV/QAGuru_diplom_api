package models;

import lombok.Getter;

@Getter
public enum PetStatus {
    AVAILABLE("available"),
    PENDING("pending"),
    SOLD("sold");

    private final String status;

    PetStatus(String title) {
        this.status = title;
    }

    @Override
    public String toString() {
        return status;
    }
}
