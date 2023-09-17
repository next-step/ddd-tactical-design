package kitchenpos.menus.tobe.domain.menu;


import kitchenpos.products.tobe.domain.Product;

import javax.persistence.*;

import static java.util.Objects.isNull;

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

    @Column(name = "quantity", nullable = false)
    private Long quantity;

    protected MenuProduct() {
    }

    protected MenuProduct(Product product, Long quantity) {
        validateMenuProductQuantityIsNull(quantity);
        validateMenuProductQuantityIsNegative(quantity);

        this.product = product;
        this.quantity = quantity;
    }

    public static MenuProduct create(Product product, Long quantity) {
        return new MenuProduct(product, quantity);
    }

    public Long getSeq() {
        return seq;
    }

    public Product getProduct() {
        return product;
    }

    public long getQuantity() {
        return quantity;
    }

    private static void validateMenuProductQuantityIsNegative(Long value) {
        if (isNegative(value)) {
            throw new IllegalArgumentException("메뉴 수량은 음수일 수 없습니다.");
        }
    }

    private static void validateMenuProductQuantityIsNull(Long value) {
        if (isNull(value)) {
            throw new IllegalArgumentException("메뉴 수량은 비어있을 수 없습니다.");
        }
    }

    private static boolean isNegative(Long value) {
        return 0L > value;
    }
}
