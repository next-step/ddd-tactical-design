package kitchenpos.products.tobe;

import kitchenpos.infra.PurgomalumClient;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProductValidatorImpl implements ProductValidator {

    private PurgomalumClient purgomalumClient;


    @Override
    public void delegate(String name, BigDecimal price) {
        validateName(name);
        validatePrice(price);
    }

    private void validateName(String name) {
        if (purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException("비속어가 포함되어 있습니다.");
        }
    }

    private void validatePrice(BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("가격은 0보다 작을 수 없습니다.");
        }
    }
}
