package org.zadanko.logic;

import org.zadanko.domain.Asset;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Portfolio {
    private double cash;
    private final List<Asset> assets = new ArrayList<>();

    public Portfolio(double initialCash) {
        if (initialCash < 0) throw new IllegalArgumentException("initial cash must be >= 0");
        this.cash = initialCash;
    }

    public double getCash() { return cash; }

    public List<Asset> getAssets() { return Collections.unmodifiableList(assets); }


//      Próba zakupu aktywa. Sprawdza czy inwestora stać na zakup używając metody purchaseCost() z Asset.
//     Jeżeli brakuje środków, rzuca InsufficientFundsException.

    public void buy(Asset asset) throws InsufficientFundsException {
        double required = asset.purchaseCost();
        if (required > cash) {
            throw new InsufficientFundsException(String.format("Not enough cash. Required=%.2f, available=%.2f", required, cash));
        }
        cash -= required;
        assets.add(asset);
    }


//      Sumuje rzeczywistą wartość wszystkich aktywów w portfelu wywołując polimorficznie realValue()

    public double auditTotalValue() {
        return assets.stream()
                .mapToDouble(Asset::realValue)
                .sum();
    }
}