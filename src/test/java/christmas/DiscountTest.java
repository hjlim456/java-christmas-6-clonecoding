package christmas;


import static org.assertj.core.api.Assertions.*;

import christmas.discount.DDayDiscount;
import christmas.discount.SpecialDiscount;
import christmas.discount.WeekDiscount;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class DiscountTest {
    //Fixture 도임
    private static final DecemberDate WEEKEND = new DecemberDate(1);
    private static final DecemberDate WEEKDAY = new DecemberDate(   3);

    private static Menus makeMenusByList(List<Menu> menus) {
        Map<Menu, Integer> menuRepository = new HashMap<>();
        menus.forEach(menu -> {
            menuRepository.put(menu, menuRepository.getOrDefault(menu, 0) + 1);
        });
        return new Menus(menuRepository);
    }
    @DisplayName("D-day이벤트 할인 금액 계산함수 성공 테스트")
    @ParameterizedTest
    @MethodSource("dDayDiscountProvider")
    void dDayDiscountAmount(DecemberDate reserveDate, Money expected) {
    //given
        DDayDiscount discount = new DDayDiscount();
    //when
        Money actual = discount.calculateDiscountAmount(reserveDate);
        //then
        assertThat(actual).isEqualTo(expected);
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
    @MethodSource("weekendDiscountProvider")
    void weekendDiscountAmount(Menus menus, DecemberDate decemberDate, Money expected) {
        //given
        WeekDiscount discount = new WeekDiscount();
        //when
        Money actual = discount.calculateDiscountAmount(decemberDate, menus);
        //then
        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> weekendDiscountProvider() {
        return  Stream.of(
                Arguments.of(makeMenusByList(List.of(Menu.CHAMPAGNE, Menu.BBQ_RIBS, Menu.CHOCO_CAKE)), WEEKEND,
                        new Money(2023)),
                Arguments.of(makeMenusByList(List.of(Menu.CHAMPAGNE, Menu.BBQ_RIBS, Menu.T_BORN_STAKE)), WEEKDAY,
                        new Money(0))
        );
    }

    @DisplayName("평일는 디저트 1개당 2023원 할인 적용된다")
    @ParameterizedTest
    @MethodSource("weekdayDiscountProvider")
    void weekdayDiscountAmountTest(Menus menus, DecemberDate date, Money expected) {
        //given
        WeekDiscount weekDiscount = new WeekDiscount();
        //when
        Money actual = weekDiscount.calculateDiscountAmount(date, menus);
        //then
        assertThat(actual).isEqualTo(expected);

    }

    static Stream<Arguments> weekdayDiscountProvider() {
        return Stream.of(
                Arguments.of(makeMenusByList(List.of(Menu.CHAMPAGNE, Menu.CHOCO_CAKE, Menu.ICE_CREAM)),
                                WEEKEND, new Money(0)),
                Arguments.of(makeMenusByList(List.of(Menu.CHAMPAGNE, Menu.CHOCO_CAKE, Menu.T_BORN_STAKE)),
                        WEEKDAY, new Money(2023))
                );
    }

    @DisplayName("달력에 별이 있는 날짜에는 1000원을 할인해준다.")
    @ParameterizedTest
    @MethodSource("specialDiscountProvider")
    void specialDiscountTest(DecemberDate date, Money expected){
        //given
        SpecialDiscount specialDiscount = new SpecialDiscount();
        //when
        Money actual = specialDiscount.calculateDiscountAmount(date);
        //then
        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> specialDiscountProvider() {
        return Stream.of(
                Arguments.of(new DecemberDate(3), new Money(1000)),
                Arguments.of(new DecemberDate(10), new Money(1000)),
                Arguments.of(new DecemberDate(17), new Money(1000)),
                Arguments.of(new DecemberDate(24), new Money(1000)),
                Arguments.of(new DecemberDate(25), new Money(1000)),
                Arguments.of(new DecemberDate(31), new Money(1000)),
                Arguments.of(new DecemberDate(1), new Money(0)),
                Arguments.of(new DecemberDate(2), new Money(0)),
                Arguments.of(new DecemberDate(29), new Money(0)),
                Arguments.of(new DecemberDate(30), new Money(0))
        );
    }

}