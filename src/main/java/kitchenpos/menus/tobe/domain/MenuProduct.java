package kitchenpos.menus.tobe.domain;


import kitchenpos.products.tobe.domain.Product;

import javax.persistence.*;

@Table(name = "menu_product")
@Entity
public class MenuProduct {
    @Column(name = "menu_product_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "product_id",
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_menu_product_to_product")
    )
    private Product product;

    @Column(name = "quantity", nullable = false)
    @Embedded
    private MenuProductQuantity quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    public MenuProduct(Long seq, Product product, long quantity) {
        this.seq = seq;
        this.product = product;
        this.quantity = new MenuProductQuantity(quantity);
    }

    protected MenuProduct() { }

    public Long getSeq() {
        return seq;
    }

    public Product getProduct() {
        return product;
    }

    public long getQuantity() {
        return quantity.value();
    }

    public String getProductId() {
        return product.getId();
    }
}
