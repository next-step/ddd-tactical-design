package kitchenpos.products.tobe;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public interface ProductValidator {

    void delegate(String name, BigDecimal price);
    void validateName(String name);
    void validatePrice(BigDecimal price);
}
