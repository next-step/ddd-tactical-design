package kitchenpos.products.domain.tobe.domain;

import static kitchenpos.products.domain.tobe.domain.Currency.*;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class ProductPrice {
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    @Column(name = "currency", nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    private ProductPrice(BigDecimal price) {
        validationOfPrice(price);
        this.price = price;
        this.currency = defaultCurrency();
    }

    public ProductPrice() {

    }

    public static ProductPrice of(BigDecimal price) {
        return new ProductPrice(price);
    }

    public static ProductPrice of(long price) {
        return new ProductPrice(BigDecimal.valueOf(price));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ProductPrice))
            return false;
        ProductPrice that = (ProductPrice)o;
        return Objects.equals(price, that.price) && Objects.equals(currency, that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, currency);
    }

    private void validationOfPrice(BigDecimal price) {
        if (price == null) {
            throw new IllegalArgumentException("상품의 가격은 필수로 입력해야 합니다.");
        }
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(" 상품의 가격은 0원 이상이어야 합니다.");
        }
    }

    public ProductPrice add(ProductPrice productPrice) {
        return ProductPrice.of(price.add(productPrice.price));
    }

    public boolean isGreaterThan(ProductPrice comparePrice) {
        return price.compareTo(comparePrice.price) >= 1;
    }

    public boolean isSamePrice(ProductPrice comparePrice) {
        return this.equals(comparePrice);
    }

    public ProductPrice changePrice(BigDecimal newPrice) {
        return new ProductPrice(newPrice);
    }

    public ProductPrice changePrice(Long newPrice) {
        return new ProductPrice(BigDecimal.valueOf(newPrice));
    }
}
