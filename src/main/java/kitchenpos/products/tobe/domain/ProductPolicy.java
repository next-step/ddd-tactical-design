package kitchenpos.products.tobe.domain;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;

@Component
public class ProductPolicy {

    public static void checkDisplayedName(String displayedName) {
        if (Objects.isNull(displayedName)) {
            throw new IllegalArgumentException("상품 이름은 필수 항목입니다.");
        }
    }

    public static void checkPrice(BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("가격은 0원 이상이어야 합니다.");
        }
    }
}
