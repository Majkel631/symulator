import org.junit.jupiter.api.Test;
import org.zadanko.domain.Asset;
import org.zadanko.domain.Commodity;
import org.zadanko.domain.Currency;
import org.zadanko.domain.Share;
import org.zadanko.logic.InsufficientFundsException;
import org.zadanko.logic.Portfolio;

import static org.junit.jupiter.api.Assertions.*;

public class BusinessLogicTest {
    @Test
    void polymorphismDifferentRealValues() {
        double unitPrice = 100.0;
        double quantity = 10.0; // gross = 1000.0

        Share share = new Share("S1", unitPrice, quantity);
        Commodity commodity = new Commodity("C1", unitPrice, quantity, 2.0); // storage 2 per unit => cost 20
        Currency currency = new Currency("CU1", unitPrice, quantity, 1.5); // spread 1.5 => bid=98.5 => value=985

        double vShare = share.realValue();        // expect 1000 - 5 = 995
        double vCommodity = commodity.realValue(); // expect 1000 - 20 = 980
        double vCurrency = currency.realValue();   // expect (100-1.5)*10 = 985

        // wszystkie wyniki muszą być różne
        assertNotEquals(vShare, vCommodity, "Share and Commodity should have different real values");
        assertNotEquals(vShare, vCurrency, "Share and Currency should have different real values");
        assertNotEquals(vCommodity, vCurrency, "Commodity and Currency should have different real values");
    }

    @Test
    void purchaseFailsWhenInsufficientFunds() {
        Portfolio portfolio = new Portfolio(50.0); // mało gotówki
        Share expensiveShare = new Share("S-exp", 100.0, 1.0); // purchaseCost = 100 + 5 = 105

        Exception ex = assertThrows(InsufficientFundsException.class, () -> {
            portfolio.buy(expensiveShare);
        });
        String msg = ex.getMessage();
        assertTrue(msg.contains("Not enough cash"));
    }

    @Test
    void portfolioAuditSumsPolymorphicValues() throws InsufficientFundsException {
        Portfolio p = new Portfolio(5000.0);
        // kupujemy różne aktywa
        p.buy(new Share("S2", 100.0, 5.0));         // purchase cost 500 + 5 = 505
        p.buy(new Commodity("C2", 50.0, 10.0, 1.0)); // purchase cost 500 + 10 = 510
        p.buy(new Currency("CU2", 10.0, 10.0, 0.5)); // purchase cost 100

        double audit = p.auditTotalValue();
        double expected = p.getAssets().stream().mapToDouble(Asset::realValue).sum();
        assertEquals(expected, audit, 1e-9);
    }
}

