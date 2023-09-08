package kitchenpos.products.tobe.domain;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;

@Component
public class ProductPolicy {

    public static void checkDisplayedName(String displayedName) {
        if (Objects.isNull(displayedName)) {
            throw new IllegalArgumentException();
        }
    }

    public static void checkPrice(BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
    }
}
