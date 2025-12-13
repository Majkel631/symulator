package org.zadanko;

import org.zadanko.domain.Asset;
import org.zadanko.domain.Commodity;
import org.zadanko.domain.Currency;
import org.zadanko.domain.Share;
import org.zadanko.logic.InsufficientFundsException;
import org.zadanko.logic.Portfolio;

public class Main {

    public static void main(String[] args) {
        System.out.println("Symulator portfela finansowego");

        Portfolio portfolio = new Portfolio(3000.0);

        Asset[] assets = {
                new Share("SH-APPLE", 100.0, 10.0),
                new Commodity("CM-GOLD", 100.0, 10.0, 2.0),
                new Currency("FX-USD", 100.0, 10.0, 1.5)
        };

        System.out.println("\nZakup aktywów:");
        for (Asset asset : assets) {
            try {
                portfolio.buy(asset);
                System.out.println(asset.getId() + " purchaseCost = " + asset.purchaseCost());
            } catch (InsufficientFundsException e) {
                System.out.println(asset.getId() + " purchaseCost = " + asset.purchaseCost()
                        + " (brak środków)");
            }
        }

        System.out.println("\nZawartość portfela:");
        for (Asset asset : portfolio.getAssets()) {
            double gross = asset.getUnitPrice() * asset.getQuantity();
            System.out.println(
                    asset.getId()
                            + " gross = " + gross
                            + " realValue = " + asset.realValue()
                            + " strata = " + (gross - asset.realValue())
            );
        }

        System.out.println("\nPodsumowanie:");
        System.out.println("Łączna wartość aktywów = " + portfolio.auditTotalValue());
        System.out.println("Pozostała gotówka = " + portfolio.getCash());

        System.out.println("\nDuża transakcja akcji:");
        Asset bigShare = new Share("SH-BIG", 100.0, 2000.0);
        System.out.println(
                bigShare.getId()
                        + " gross = " + (bigShare.getUnitPrice() * bigShare.getQuantity())
                        + " purchaseCost = " + bigShare.purchaseCost()
                        + " realValue = " + bigShare.realValue()
        );

        System.out.println("\nBłąd biznesowy:");
        try {
            new Portfolio(50.0).buy(new Share("SH-FAIL", 100.0, 1.0));
        } catch (InsufficientFundsException e) {
            System.out.println(e.getMessage());
        }
    }
}


