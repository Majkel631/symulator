package org.zadanko.domain;

public class Commodity extends Asset {

    private final double storageRatePerUnit;

    public Commodity(String id, double unitPrice, double quantity, double storageRatePerUnit) {
        super(id, unitPrice, quantity);
        if (storageRatePerUnit < 0) {
            throw new IllegalArgumentException("storageRatePerUnit must be >= 0");
        }
        if (storageRatePerUnit >= unitPrice) {
            throw new IllegalArgumentException("storageRatePerUnit must be < unitPrice");
        }
        this.storageRatePerUnit = storageRatePerUnit;
    }

    @Override
    public double realValue() {
        double gross = unitPrice * quantity;
        double storageCost = storageRatePerUnit * quantity;
        double net = gross - storageCost;
        return Math.max(0.0, net);
    }

    @Override
    public double purchaseCost() {
        double gross = unitPrice * quantity;
        double initialStorage = storageRatePerUnit * quantity;
        return gross + initialStorage;
    }
}