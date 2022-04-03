package org.example.entities.enums;

public enum PetStatus {
    AVAILABLE("available"),
    PENDING("pending"),
    SOLD("sold");

    private final String text;

    PetStatus(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
