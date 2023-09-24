package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.common.domain.Price;
import kitchenpos.menus.exception.MenuErrorCode;
import kitchenpos.menus.exception.MenuProductException;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "menu_product")
@Entity
public class MenuProduct {

    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long seq;

    @Embedded
    private ProductId productId;

    @Transient
    private Price productPrice;

    @Embedded
    private MenuProductQuantity quantity;

    protected MenuProduct() {

    }

    public MenuProduct(ProductId productId, Price productPrice, MenuProductQuantity quantity) {
        validateProductPrice(productPrice);
        this.productPrice = productPrice;
        this.productId = productId;
        this.quantity = quantity;
    }

    public MenuProduct(ProductId productId, Price productPrice, long quantity) {
        this(productId, productPrice, new MenuProductQuantity(quantity));
    }

    public MenuProduct(UUID productId, Price productPrice, long quantity) {
        this(new ProductId(productId), productPrice, new MenuProductQuantity(quantity));
    }


    private void validateProductPrice(Price productPrice) {
        if (productPrice == null) {
            throw new MenuProductException(MenuErrorCode.MENU_PRODUCT_PRICE_IS_EMPTY);
        }
    }

    public Price calculatePrice() {
        return productPrice.multiply(quantity.getValue());
    }

    public void fetchPrice(Price price) {
        this.productPrice = price;
    }

    public long getSeq() {
        return seq;
    }

    public ProductId getProductId() {
        return productId;
    }


    public boolean hasProduct(ProductId productId) {
        return this.productId.equals(productId);
    }

    public long getQuantityValue() {
        return quantity.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MenuProduct)) return false;

        MenuProduct that = (MenuProduct) o;

        if (getSeq() != that.getSeq()) return false;
        return getProductId() != null ? getProductId().equals(that.getProductId()) : that.getProductId() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (getSeq() ^ (getSeq() >>> 32));
        result = 31 * result + (getProductId() != null ? getProductId().hashCode() : 0);
        return result;
    }
}
