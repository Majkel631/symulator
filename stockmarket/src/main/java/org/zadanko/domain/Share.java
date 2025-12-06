package org.zadanko.domain;

public class Share extends Asset {
    private static final double MAX_MANIP_FEE = 5.0; // maksymalna opłata
    private static final double FEE_PERCENT = 0.01;  // 1% wartości brutto

    public Share(String id, double unitPrice, double quantity) {
        super(id, unitPrice, quantity);
    }

    @Override
    public double realValue() {
        double gross = unitPrice * quantity;
        double fee = Math.min(MAX_MANIP_FEE, gross * FEE_PERCENT);
        double net = gross - fee;
        return Math.max(0.0, net);
    }

    @Override
    public double purchaseCost() {
        double gross = unitPrice * quantity;
        double fee = Math.min(MAX_MANIP_FEE, gross * FEE_PERCENT);
        return gross + fee;
    }
}