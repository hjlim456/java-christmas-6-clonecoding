package christmas;

import java.util.Map;

public record Menus(Map<Menu, Integer> menuRepository) {

    public int findTotalCountbyFoodType(FoodType foodType) {
        return menuRepository.keySet()
                .stream()
                .filter(menu -> menu.isSameType(foodType))
                .map(menuRepository::get)
                .reduce(0, Integer::sum);
    }

    public Money calculateTotalPrice() {
        return menuRepository.entrySet()
                .stream()
                .map(entry -> new Money(entry.getKey().getPrice().amount() * entry.getValue())) // 가격 * 수량을 Money 객체로 변환
                .reduce(new Money(0), Money::sum); // Money 객체들을 합산하여 총 금액 계산
    }
}
