package christmas;

import static jdk.internal.org.jline.reader.impl.LineReaderImpl.CompletionType.List;

import christmas.discount.DDayDiscount;
import christmas.discount.WeekDiscount;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class DDayDiscountTest {

    @DisplayName("D-day이벤트 할인 금액 계산함수 성공 테스트")
    @ParameterizedTest
    @MethodSource("dDayDiscountProvider")
    void dDayDiscountAmount(DecemberDate reserveDate, Money expected) {
    //given
        DDayDiscount discount = new DDayDiscount();
    //when
        Money actual = discount.calculateDiscountAmount(reserveDate);
        //then
        Assertions.assertThat(actual.getAmount()).isEqualTo(expected.getAmount());
    }

    static Stream<Arguments> dDayDiscountProvider() {
        return Stream.of(
                Arguments.of(new DecemberDate(1), new Money(1000)),
                Arguments.of(new DecemberDate(2), new Money(1100)),
                Arguments.of(new DecemberDate(3), new Money(1200)),
                Arguments.of(new DecemberDate(4), new Money(1300)),
                Arguments.of(new DecemberDate(10), new Money(1900)),
                Arguments.of(new DecemberDate(20), new Money(2900)),
                Arguments.of(new DecemberDate(25), new Money(3400)),
                Arguments.of(new DecemberDate(26), new Money(0)),
                Arguments.of(new DecemberDate(27), new Money(0)),
                Arguments.of(new DecemberDate(28), new Money(0)),
                Arguments.of(new DecemberDate(29), new Money(0)),
                Arguments.of(new DecemberDate(30), new Money(0)),
                Arguments.of(new DecemberDate(31), new Money(0))
        );
    }

    @DisplayName("주말이벤트 할인 금액 계산함수 성공 테스트")
    @ParameterizedTest
    @MethodSource("weekDiscountProvider")
    void weekDiscountAmount(DecemberDate reserveDate, Money expected) {
        //given
        WeekDiscount discount = new WeekDiscount();
        //when
        Money actual = discount.calculateDiscountAmount(reserveDate);
        //then
        Assertions.assertThat(actual.getAmount()).isEqualTo(expected.getAmount());
    }

    static Stream<Arguments> weekDiscountProvider() {
        return  Stream.of(
                Arguments.of(Menus, new Money(2023)),
                Arguments.of(new Menus(List.of(Menu.CHOCO_CAKE, )), new Money(2023))

        );
    }
}