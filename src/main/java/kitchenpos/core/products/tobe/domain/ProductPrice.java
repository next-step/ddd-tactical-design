package kitchenpos.core.products.tobe.domain;

import jakarta.persistence.Embeddable;
import kitchenpos.core.products.tobe.domain.exception.InvalidProductPriceException;
import kitchenpos.core.shared.value.Money;
import kitchenpos.core.shared.domain.ValueObject;
import kitchenpos.core.shared.value.Quantity;

import java.math.BigDecimal;

@Embeddable
public class ProductPrice extends ValueObject<ProductPrice> {
    private Money price;

    @SuppressWarnings("unused")
    protected ProductPrice() {}

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

    //quantity 곱하는 로직


    @Override
    protected Object[] getEqualityFields() {
        return new Object[] { price };
    }

    public Money multiply(Quantity quantity) {
        return price.multiply(quantity.getValue());
    }
}
