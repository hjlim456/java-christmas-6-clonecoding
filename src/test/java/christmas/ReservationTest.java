package christmas;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTest {

    @DisplayName("12만원이상 주문시 샴페인가격이 혜택금액에 추가되는지 성공여부테스트")
    @Test
    void calculateTotalDiscountMoneyTest() {
        //given
        Map<Menu, Integer> menuIntegerMap = new HashMap<>();
        Menus menus = new Menus(menuIntegerMap);

        DecemberDate date = new DecemberDate(3);//평일이자 별 달린 날.

        Reservation reservation = new Reservation(menus, date);
        //when
        menuIntegerMap.put(Menu.MUSHROOM_CREAM_SOUP, 2);
        menuIntegerMap.put(Menu.SEA_FOOD_PASTA, 5);
        menuIntegerMap.put(Menu.ICE_CREAM, 3);
        Money expected = new Money(1200 + 1000 + 2023 * 3 + Menu.CHAMPAGNE.getPrice().amount());

        Money discountMoney = reservation.calculateTotalDiscountMoney();
        //then
        assertThat(discountMoney).isEqualTo(expected);
    }

    @DisplayName("구입 금액이 1만원미만일시 할인금액 0")
    @Test
    void lessThanMinPriceTest() {

        //given
        Map<Menu, Integer> menuIntegerMap = new HashMap<>();
        Menus menus = new Menus(menuIntegerMap);

        DecemberDate date = new DecemberDate(3);
        Reservation reservation = new Reservation(menus, date);

        Money actual = reservation.calculateTotalDiscountMoney();
        //when
        menuIntegerMap.put(Menu.ICE_CREAM, 1);
        Money expected = new Money(0);
        //then
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("최종할인금액 계산 성공 테스트")
    @Test
    void totalDiscountMoneyTest() {
        //given
        Map<Menu, Integer> menuIntegerMap = new HashMap<>();
        Menus menus = new Menus(menuIntegerMap);

        DecemberDate date = new DecemberDate(3); //평일이자 별달린날

        Reservation reservation = new Reservation(menus, date);
        //when
        menuIntegerMap.put(Menu.MUSHROOM_CREAM_SOUP, 2); //애피 1.2
        menuIntegerMap.put(Menu.TAPAS, 2);//에피 1.1
        menuIntegerMap.put(Menu.T_BORN_STAKE, 2); //메인 11
        menuIntegerMap.put(Menu.CHAMPAGNE, 1); //음료2.5
        Money expected = new Money(130800); // 원래주문15.8만 할인금액 2.72만원
        //then
        assertThat(reservation.calculateTotalDiscountedMoney()).isEqualTo(expected);

    }
}