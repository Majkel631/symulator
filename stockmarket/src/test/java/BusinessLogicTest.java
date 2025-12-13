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
        double quantity = 10.0;

        Share share = new Share("S1", unitPrice, quantity);
        Commodity commodity = new Commodity("C1", unitPrice, quantity, 2.0);
        Currency currency = new Currency("CU1", unitPrice, quantity, 1.5);

        double vShare = share.realValue();         // 995
        double vCommodity = commodity.realValue(); // 980
        double vCurrency = currency.realValue();   // 985

        assertNotEquals(vShare, vCommodity);
        assertNotEquals(vShare, vCurrency);
        assertNotEquals(vCommodity, vCurrency);
    }
    @Test
    void assetRejectsInvalidArguments() {
        assertThrows(IllegalArgumentException.class,
                () -> new Share(null, 100.0, 1.0));

        assertThrows(IllegalArgumentException.class,
                () -> new Share("S", -10.0, 1.0));

        assertThrows(IllegalArgumentException.class,
                () -> new Share("S", 10.0, 0.0));
    }

    @Test
    void purchaseFailsWhenInsufficientFunds() {
        Portfolio portfolio = new Portfolio(50.0);
        Share expensiveShare = new Share("S-exp", 100.0, 1.0);

        InsufficientFundsException ex = assertThrows(
                InsufficientFundsException.class,
                () -> portfolio.buy(expensiveShare)
        );

        assertTrue(ex.getMessage().contains("Not enough cash"));
    }

    @Test
    void portfolioAuditSumsPolymorphicValues() throws InsufficientFundsException {
        Portfolio p = new Portfolio(5000.0);

        p.buy(new Share("S2", 100.0, 5.0));          // 495
        p.buy(new Commodity("C2", 50.0, 10.0, 1.0)); // 490
        p.buy(new Currency("CU2", 10.0, 10.0, 0.5)); // 95

        double audit = p.auditTotalValue();
        double expected = 495.0 + 490.0 + 95.0;

        assertEquals(expected, audit, 1e-9);
    }
}


