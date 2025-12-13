package org.zadanko.logic;

import org.zadanko.domain.Asset;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Portfolio {

    private double cash;
    private final List<Asset> assets = new ArrayList<>();

    public Portfolio(double initialCash) {
        if (initialCash < 0 || Double.isNaN(initialCash) || Double.isInfinite(initialCash)) {
            throw new IllegalArgumentException("initialCash must be a finite non-negative value");
        }
        this.cash = initialCash;
    }

    public double getCash() {
        return cash;
    }

    public List<Asset> getAssets() {
        return Collections.unmodifiableList(assets);
    }

    public void buy(Asset asset) throws InsufficientFundsException {
        if (asset == null) {
            throw new IllegalArgumentException("asset must not be null");
        }

        double required = asset.purchaseCost();
        if (required > cash) {
            throw new InsufficientFundsException(
                    String.format("Not enough cash. Required=%.2f, available=%.2f", required, cash)
            );
        }

        cash -= required;
        assets.add(asset);
    }

    public double auditTotalValue() {
        return assets.stream()
                .mapToDouble(Asset::realValue)
                .sum();
    }
}