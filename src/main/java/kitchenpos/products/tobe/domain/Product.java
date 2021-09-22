package kitchenpos.products.tobe.domain;

import java.util.Objects;
import java.util.UUID;

/**
 * - 상품을 등록할 수 있다.
 * - 상품의 가격이 올바르지 않으면 등록할 수 없다.
 *   - 상품의 가격은 0원 이상이어야 한다.
 * - 상품의 이름이 올바르지 않으면 등록할 수 없다.
 *   - 상품의 이름에는 비속어가 포함될 수 없다.
 * - 상품의 가격을 변경할 수 있다.
 * - 상품의 가격이 올바르지 않으면 변경할 수 없다.
 *   - 상품의 가격은 0원 이상이어야 한다.
 * - 상품의 가격이 변경될 때 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 크면 메뉴가 숨겨진다.
 * - 상품의 목록을 조회할 수 있다.
 */
public class Product {

    private UUID id;

    private Name name;

    private Price price;

    public Product(UUID id, Name name, Price price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public void changePrice(Price price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
