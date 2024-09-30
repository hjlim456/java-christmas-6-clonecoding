package christmas.discount;

import christmas.DecemberDate;
import christmas.FoodType;
import christmas.Menus;
import christmas.Money;

public class WeekDiscount {
    private static final int WEEK_DISCOUNT_AMOUNT = 2023;
    public static Money calculateDiscountAmount(DecemberDate date, Menus menus) {
        Money weekendDiscountAmount = calculateWeekendDiscountAmount(date, menus);
        Money weekdayDiscountAmount = calculateWeekdayDiscountAmount(date, menus);
        return Money.sum(weekendDiscountAmount, weekdayDiscountAmount);
    }

    private static Money calculateWeekendDiscountAmount(DecemberDate date, Menus menus) {
        if (date.isWeekend()) {
            return new Money(menus.findTotalCountbyFoodType(FoodType.MAIN_COURSE)  * WEEK_DISCOUNT_AMOUNT);
        }
        return new Money(0);
    }
    private static Money calculateWeekdayDiscountAmount(DecemberDate date, Menus menus) {
        if (date.isWeekday()) {
            return new Money(menus.findTotalCountbyFoodType(FoodType.DESSERT)  * WEEK_DISCOUNT_AMOUNT);
        }
        return new Money(0);
    }

}
