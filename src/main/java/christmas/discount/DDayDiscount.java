package christmas.discount;

import christmas.DecemberDate;
import christmas.Money;

public class DDayDiscount {
    private static final int DISCOUNT_AMOUNT = 1000;
    private static final DecemberDate startDate = new DecemberDate(1);
    private static final DecemberDate endDate = new DecemberDate(25);

    public static Money calculateDiscountAmount(DecemberDate reserveDate) {
        if (isValidateDate(reserveDate)) {
            return new Money(DISCOUNT_AMOUNT + calculateAdditionalAmount(reserveDate));
        }
        return new Money(0);
    }

    private static int calculateAdditionalAmount(DecemberDate reserveDate) {
        return (reserveDate.dateAmount() - startDate.dateAmount() ) * 100;
    }

    private static boolean isValidateDate(DecemberDate reserveDate) {
        return reserveDate.isLessThan(endDate);
    }
}
