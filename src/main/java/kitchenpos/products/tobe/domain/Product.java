package kitchenpos.products.tobe.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * ### 모델링
 * - `Product`는 식별자와 `DisplayedName`, 가격을 가진다.
 * - `DisplayedName`에는 `Profanity`가 포함될 수 없다.
 * <p>
 * ### 요구사항
 * - 상품을 등록할 수 있다.
 * - 상품의 가격이 올바르지 않으면 등록할 수 없다.
 * - 상품의 가격은 0원 이상이어야 한다.
 * - 상품의 이름이 올바르지 않으면 등록할 수 없다.
 * - 상품의 이름에는 비속어가 포함될 수 없다.
 * - 상품의 가격을 변경할 수 있다.
 * - 상품의 가격이 올바르지 않으면 변경할 수 없다.
 * - 상품의 가격은 0원 이상이어야 한다.
 * - 상품의 가격이 변경될 때 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 크면 메뉴가 숨겨진다.
 * - 상품의 목록을 조회할 수 있다.
 */
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Embedded
    private ProductName name;

    @Embedded
    private Price price;

    protected Product() {

    }

    public Product(ProductName name, Price price) {
        this.name = name;
        this.price = price;
    }

    public void changePrice(BigDecimal price) {
        this.price.changePrice(price);
    }

    public Price getPrice() {
        return this.price;
    }

}
