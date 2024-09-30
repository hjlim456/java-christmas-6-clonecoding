package christmas;

import christmas.discount.DDayDiscount;
import christmas.discount.SpecialDiscount;
import christmas.discount.WeekDiscount;
import java.util.ArrayList;
import java.util.List;

public class Reservation {
    public static final Money APPLY_PRESENT_THRESHOLDER = new Money(120000);
    private Menus menus;
    private DecemberDate decemberDate;

    public Reservation(Menus menus, DecemberDate decemberDate) {
        this.menus = menus;
        this.decemberDate = decemberDate;
    }

    public Money calculateOrderSum() {
        return menus.calculateTotalPrice();
    }

    public Money calculateTotalDiscountMoney() {
        Money OrderMoney = calculateOrderSum();
        if (OrderMoney.isLessThan(new Money(10000))) {
            return new Money(0);
        }

        List<Money> totalDicountMoney = new ArrayList<>(List.of(
                DDayDiscount.calculateDiscountAmount(decemberDate),
                SpecialDiscount.calculateDiscountAmount(decemberDate),
                WeekDiscount.calculateDiscountAmount(decemberDate, menus)
        ));
        isAppliedGiftEvent(totalDicountMoney);

        return totalDicountMoney.stream()
                .reduce(new Money(0),Money::sum);
    }

    private void isAppliedGiftEvent(List<Money> totalDicountMoney) {
        Money totalMoney = calculateOrderSum();
        if (totalMoney.isMoreOrEqualThan(APPLY_PRESENT_THRESHOLDER)){
            totalDicountMoney.add(Menu.CHAMPAGNE.getPrice());
        }
    }

    public Money calculateTotalDiscountedMoney() {
        return calculateOrderSum().minus(calculateTotalDiscountMoney());
    }
}
