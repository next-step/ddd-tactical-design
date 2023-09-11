package kitchenpos.menus.tobe.domain;

import kitchenpos.common.domain.Price;
import kitchenpos.products.tobe.domain.Product;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

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

    protected MenuProduct() {

    }

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "line_id")
    private Menu menu;


    @Embedded
    private MenuProductQuantity quantity;

    public MenuProduct(Product product, MenuProductQuantity quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public MenuProduct(Product product, long quantity) {
        this.product = product;
        this.quantity = new MenuProductQuantity(quantity);
    }

    public Long getSeq() {
        return seq;
    }


    public Product getProduct() {
        return product;
    }


    public long getQuantityValue() {
        return quantity.getValue();
    }

    public UUID getProductId() {
        return product.getId();
    }

    public Price getPrice() {
        return product.getPrice().multiply(quantity.getValue());
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenuProduct that = (MenuProduct) o;

        if (!Objects.equals(seq, that.seq)) return false;
        if (!Objects.equals(product, that.product)) return false;
        return Objects.equals(menu, that.menu);
    }

    @Override
    public int hashCode() {
        int result = seq != null ? seq.hashCode() : 0;
        result = 31 * result + (product != null ? product.hashCode() : 0);
        result = 31 * result + (menu != null ? menu.hashCode() : 0);
        return result;
    }
}
