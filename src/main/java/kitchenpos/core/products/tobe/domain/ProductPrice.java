package kitchenpos.core.products.tobe.domain;

import kitchenpos.core.products.tobe.domain.exception.InvalidProductPriceException;
import kitchenpos.core.shared.value.Money;
import kitchenpos.core.shared.domain.ValueObject;

public class ProductPrice extends ValueObject<ProductPrice> {
    private Money price;

    private ProductPrice(Money price) {
        if (price == null || price.isLessThan(Money.ZERO)) {
            throw new InvalidProductPriceException("상품 가격은 null 이거나 0보다 작을 수 없습니다.");
        }

        this.price = price;
    }

    public static ProductPrice of(Money price) {
        return new ProductPrice(price);
    }

    public Money getPrice() {
        return price;
    }

    @Override
    protected Object[] getEqualityFields() {
        return new Object[] { price };
    }
}
