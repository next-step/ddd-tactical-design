package kitchenpos.menus.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kitchenpos.menus.tobe.domain.exception.InvalidMenuProductQuantityException;
import kitchenpos.menus.tobe.domain.vo.MenuProductPrice;
import kitchenpos.menus.tobe.domain.vo.MenuProductQuantity;
import kitchenpos.products.tobe.domain.Product;

import java.util.UUID;

/**
 * 메뉴 상품(MenuProduct): 하나의 메뉴에 포함된 개별 상품과 그 수량을 의미.
 * e.g. 후라이드 치킨(치킨 1마리, 허니머스타드 소스, 콜라 500ml)
 */
@Table(name = "menu_product")
@Entity
public class MenuProduct {

    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "product_id",
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_product_to_product")
    )
    private Product product;

    @Embedded
    private MenuProductPrice price;

    @Embedded
    private MenuProductQuantity quantity;

    @Column(name = "product_id", columnDefinition = "binary(16)", nullable = false)
    private UUID productId;

    protected MenuProduct() {
    }

    public MenuProduct(Product product, int price, int quantity, UUID productId) {
        if (quantity < 0) {
            throw new InvalidMenuProductQuantityException("메뉴에 등록된 상품의 수량은 0개 이상이어야 합니다.");
        }
        this.product = product;
        this.price = new MenuProductPrice(price);
        this.quantity = new MenuProductQuantity(quantity);
        this.productId = productId;
    }

    public int amount() {
        return price.getPrice() * quantity.getQuantity();
    }

    public UUID getProductId() {
        return productId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MenuProduct that)) return false;
        return Objects.equals(seq, that.seq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seq);
    }
}
