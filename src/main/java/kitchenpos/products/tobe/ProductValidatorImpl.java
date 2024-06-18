package kitchenpos.products.tobe;

import kitchenpos.infra.PurgomalumClient;

import java.math.BigDecimal;

public class ProductValidatorImpl implements ProductValidator {

    private PurgomalumClient purgomalumClient;


    @Override
    public void delegate(String name, BigDecimal price) {
        validateName(name);
        validatePrice(price);
    }

    private void validateName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("상품명은 null일 수 없습니다");
        }
        if (purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException("비속어가 포함되어 있습니다.");
        }
    }

    private void validatePrice(BigDecimal price) {
        if (price == null) {
            throw new IllegalArgumentException("가격은 null일 수 없습니다.");
        }
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("가격은 0보다 작을 수 없습니다.");
        }
    }
}
