package org.zadanko.domain;

public abstract class Asset {
    protected final String id;
    protected final double unitPrice;
    protected final double quantity;

    protected Asset(String id, double unitPrice, double quantity) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("id must not be null or blank");
        }
        if (unitPrice <= 0) {
            throw new IllegalArgumentException("unitPrice must be > 0");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity must be > 0");
        }
        this.id = id;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public double getQuantity() {
        return quantity;
    }

    // Zwraca rzeczywistą (netto) wartość aktywa
    public abstract double realValue();

    // Zwraca całkowity koszt zakupu aktywa
    public abstract double purchaseCost();
}
