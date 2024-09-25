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

    public Money calculateTotalMoney() {
        return menus.calculateTotalPrice();
    }

    public Money calculateTotalDiscountMoney(DecemberDate date, Menus menus) {
        List<Money> totalDicountMoney = new ArrayList<>(List.of(
                DDayDiscount.calculateDiscountAmount(date),
                SpecialDiscount.calculateDiscountAmount(date),
                WeekDiscount.calculateDiscountAmount(date, menus)
        ));
        isAppliedGiftEvent(totalDicountMoney);

        return totalDicountMoney.stream()
                .reduce(new Money(0),Money::sum);
    }

    private void isAppliedGiftEvent(List<Money> totalDicountMoney) {
        Money totalMoney = calculateTotalMoney();
        if (totalMoney.isMoreOrEqualThan(APPLY_PRESENT_THRESHOLDER)){
            totalDicountMoney.add(Menu.CHAMPAGNE.getPrice());
        }
    }

    public Money calculateTotalDiscountPrice() {
        return null;
    }

}
