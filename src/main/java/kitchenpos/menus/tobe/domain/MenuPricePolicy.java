package kitchenpos.menus.tobe.domain;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class MenuPricePolicy {
    public void validate(Menu menu, List<Integer> prices) {
        BigDecimal productsPrice = calculateTotalPrice(prices);
        if (menu.getPrice().compareTo(productsPrice) >= 0) {
            throw new IllegalArgumentException("메뉴의 가격은 상품 목록 가격의 합보다 작아야 합니다.");
        }
    }

    private BigDecimal calculateTotalPrice(List<Integer> prices) {
        return prices.stream()
                .map(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
