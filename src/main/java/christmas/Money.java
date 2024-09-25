package christmas;

import java.util.Objects;

public record Money(int amount) {


    public static Money sum(Money money1, Money money2) {
        return new Money(money1.amount + money2.amount);
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Money money = (Money) o;
        return amount == money.amount;
    }


    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
}
