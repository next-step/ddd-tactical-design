package kitchenpos.menus.tobe.domain;

import kitchenpos.common.domain.Price;
import kitchenpos.menus.exception.MenuErrorCode;
import kitchenpos.menus.exception.MenuProductException;
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


    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Transient
    private Price productPrice;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "line_id")
    private Menu menu;

    @Embedded
    private MenuProductQuantity quantity;

    protected MenuProduct() {

    }

    public MenuProduct(UUID productId, Price productPrice, MenuProductQuantity quantity) {
        validateProductPrice(productPrice);
        this.productPrice = productPrice;
        this.productId = productId;
        this.quantity = quantity;
    }

    public MenuProduct(UUID productId, Price productPrice, long quantity) {
        this(productId, productPrice, new MenuProductQuantity(quantity));
    }

    public MenuProduct(Product product, long quantity) {
        this(product.getId(), product.getPrice(), new MenuProductQuantity(quantity));
    }

    public MenuProduct(Product product, MenuProductQuantity quantity) {
        this(product.getId(), product.getPrice(), quantity);
    }

    private void validateProductPrice(Price productPrice) {
        if (productPrice == null) {
            throw new MenuProductException(MenuErrorCode.MENU_PRODUCT_PRICE_IS_EMPTY);
        }
    }

    public Long getSeq() {
        return seq;
    }

    public UUID getProductId() {
        return productId;
    }

    public long getQuantityValue() {
        return quantity.getValue();
    }

    public Price getPrice() {
        return productPrice.multiply(quantity.getValue());
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
        if (!Objects.equals(productId, that.productId)) return false;
        return Objects.equals(menu, that.menu);
    }

    @Override
    public int hashCode() {
        int result = seq != null ? seq.hashCode() : 0;
        result = 31 * result + (productId != null ? productId.hashCode() : 0);
        result = 31 * result + (menu != null ? menu.hashCode() : 0);
        return result;
    }
}
