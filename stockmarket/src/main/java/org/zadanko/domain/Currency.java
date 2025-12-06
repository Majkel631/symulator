package org.zadanko.domain;

public class Currency extends Asset {
    private final double spread; // absolutna różnica kursowa

    public Currency(String id, double unitPrice, double quantity, double spread) {
        super(id, unitPrice, quantity);
        if (spread < 0) throw new IllegalArgumentException("spread must be >= 0");
        this.spread = spread;
    }

    public double getSpread() { return spread; }

    @Override
    public double realValue() {
        double bid = Math.max(0.0, unitPrice - spread);
        return Math.max(0.0, bid * quantity);
    }

    @Override
    public double purchaseCost() {
        // Przy zakupie płacimy pełną cenę rynkową (ask). Spread wpływa na to ile realnie mamy (bid).
        return unitPrice * quantity;
    }
}