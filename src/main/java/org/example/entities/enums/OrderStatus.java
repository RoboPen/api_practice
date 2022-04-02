package org.example.entities.enums;

public enum OrderStatus {
    PLACED("placed"),
    APPROVED("approved"),
    DELIVERED("delivered");

    private final String text;


    OrderStatus(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
