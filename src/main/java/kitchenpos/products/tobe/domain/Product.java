package kitchenpos.products.tobe.domain;

import org.apache.logging.log4j.util.Strings;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    private static final Long MIN_PRODUCT_ID = 0L;

    private final Long id;
    private final String name;
    private final BigDecimal price;

    public Product(Long id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
        validateId();
        validateName();
        validatePrice();
    }

    private void validateId() {
        if (id < MIN_PRODUCT_ID) {
            throw new IllegalArgumentException("상품 번호가 잘못 되었습니다.");
        }
    }

    private void validateName() {
        if (Strings.isBlank(name)) {
            throw new IllegalArgumentException("상품 이름이 잘못 되었습니다.");
        }
    }

    private void validatePrice() {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("상품 가격이 잘못 되었습니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(name, product.name) &&
                Objects.equals(price, product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }
}
