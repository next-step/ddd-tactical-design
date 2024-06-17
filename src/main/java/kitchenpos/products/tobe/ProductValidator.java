package kitchenpos.products.tobe;

import java.math.BigDecimal;

public interface ProductValidator {

    void delegate(String name, BigDecimal price);
}
