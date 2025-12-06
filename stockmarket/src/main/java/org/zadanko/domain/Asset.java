package org.zadanko.domain;

public abstract class Asset {
    protected final String id;
    protected final double unitPrice;
    protected final double quantity;

    protected Asset(String id, double unitPrice, double quantity) {
        if (unitPrice < 0 || quantity < 0) throw new IllegalArgumentException("Price and quantity must be >= 0");
        this.id = id;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public String getId() { return id; }
    public double getUnitPrice() { return unitPrice; }
    public double getQuantity() { return quantity; }


//      Zwraca rzeczywistą (netto) wartość tego aktywa w portfelu,
//     po uwzględnieniu kosztów specyficznych dla rynku.

    public abstract double realValue();

//
//      Zwraca koszt, jaki inwestor musiałby zapłacić przy zakupie tego aktywa
//      (uwzględnia opłaty początkowe / prowizje / initial fees).
//      Portfolio używa tej metody do weryfikacji, czy inwestora stać na zakup.

    public abstract double purchaseCost();
}
