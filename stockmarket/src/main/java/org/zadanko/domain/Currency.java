package org.zadanko.domain;

public class Currency extends Asset {

    private final double spread;

    public Currency(String id, double unitPrice, double quantity, double spread) {
        super(id, unitPrice, quantity);
        if (spread < 0) {
            throw new IllegalArgumentException("spread must be >= 0");
        }
        if (spread >= unitPrice) {
            throw new IllegalArgumentException("spread must be < unitPrice");
        }
        this.spread = spread;
    }

    public double getSpread() {
        return spread;
    }

    @Override
    public double realValue() {
        double bid = unitPrice - spread;
        return Math.max(0.0, bid * quantity);
    }

    @Override
    public double purchaseCost() {
        // płacimy ask (pełną cenę rynkową)
        return unitPrice * quantity;
    }
}