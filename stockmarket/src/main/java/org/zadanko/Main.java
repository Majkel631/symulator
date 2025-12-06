package org.zadanko;

import org.zadanko.domain.Asset;
import org.zadanko.domain.Commodity;
import org.zadanko.domain.Currency;
import org.zadanko.domain.Share;
import org.zadanko.logic.InsufficientFundsException;
import org.zadanko.logic.Portfolio;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Symulator portfela finansowego ===");

        // Tworzymy portfel z początkowym kapitałem
        Portfolio portfolio = new Portfolio(3100.0);

        // Parametry aktywów
        double unitPrice = 100.0;
        double quantity = 10.0;

        // Tworzymy przykładowe aktywa
        Asset share = new Share("S1", unitPrice, quantity);
        Asset commodity = new Commodity("C1", unitPrice, quantity, 2.0);
        Asset currency = new Currency("CU1", unitPrice, quantity, 1.5);

        // Kupujemy aktywa
        try {
            portfolio.buy(share);
            portfolio.buy(commodity);
            portfolio.buy(currency);
        } catch (InsufficientFundsException e) {
            System.out.println("Błąd zakupu: " + e.getMessage());
        }

        // Wyświetlamy wartości rzeczywiste aktywów (polimorfizm)
        System.out.println("\n--- Wartości rzeczywiste aktywów ---");
        for (Asset asset : portfolio.getAssets()) {
            System.out.printf("%s: realValue = %.2f%n", asset.getId(), asset.realValue());
        }

        // Podsumowanie portfela
        System.out.println("\n--- Podsumowanie portfela ---");
        System.out.printf("Łączna wartość portfela: %.2f%n", portfolio.auditTotalValue());
        System.out.printf("Pozostała gotówka: %.2f%n", portfolio.getCash());

        // Test dynamicznej opłaty manipulacyjnej dla dużych zakupów
        System.out.println("\n--- Test dynamicznej opłaty manipulacyjnej dla dużych zakupów ---");
        Asset bigShare = new Share("S-big", unitPrice, 2000.0); // duży zakup
        System.out.printf("Zakup dużych akcji %s: purchaseCost = %.2f, realValue = %.2f%n",
                bigShare.getId(), bigShare.purchaseCost(), bigShare.realValue());
    }
    }



