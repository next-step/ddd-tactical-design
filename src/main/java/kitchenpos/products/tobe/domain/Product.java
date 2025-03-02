package kitchenpos.products.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kitchenpos.common.infra.ProfanityClient;
import kitchenpos.products.tobe.domain.exception.InvalidProductException;
import kitchenpos.products.tobe.domain.vo.ProductName;
import kitchenpos.products.tobe.domain.vo.ProductPrice;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * ### 상품
 *
 * > 상품을 관리하는 영역
 * >
 * > 사장님(또는 관리자)가 재고에 등록할 상품을 구성할 때 사용한다.
 *
 * | 한글명   | 영문명          | 설명                                                     |
 * |-------|--------------|--------------------------------------------------------|
 * | 상품    | Product      | 재고에 등록할 개별 상품. e.g. 후라이드 치킨, 양념 치킨, 허니머스타드 소스, 콜라 500ml |
 * | 상품 이름 | name  | 재고에 등록할 상품 이름                                          |
 * | 상품 가격 | price | 재고에 등록할 상품 가격                                          |
 */
@Table(name = "product")
@Entity
public class Product {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private ProductName name;

    @Embedded
    private ProductPrice price;

    protected Product() {
    }

    public Product(final String name) {
        this.name = new ProductName(name);
    }

    public Product(final String name,
                   final BigDecimal price) {
        this.id = UUID.randomUUID();
        this.name = new ProductName(name);
        this.price = new ProductPrice(price);
    }

    public Product(final String name,
                   final BigDecimal price,
                   final ProfanityClient profanityChecker) {
        this.id = UUID.randomUUID();
        if (profanityChecker.containsProfanity(name)) {
            throw new InvalidProductException("상품의 이름에 부적절한 단어(비속어)가 포함되면 안됩니다.");
        }
        this.name = new ProductName(name);
        this.price = new ProductPrice(price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        if (id == null || product.id == null) return false;
        return Objects.equals(getId(), product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name.getName();
    }

    public BigDecimal getPrice() {
        return price.getPrice();
    }

    public void updatePrice(final BigDecimal price) {
        this.price = new ProductPrice(price);
    }

    public void updateName(final String name) {
        this.name = new ProductName(name);
    }
}
