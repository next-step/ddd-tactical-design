package kitchenpos.menus.tobe.domain.menu;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

@Embeddable
@Access(AccessType.FIELD)
public class ProductQuantity {

    private long productId;
    private long quantity;

    protected ProductQuantity() {
    }

    private ProductQuantity(long productId, long quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("invalid quantity");
        }

        this.productId = productId;
        this.quantity = quantity;
    }

    public static ProductQuantity of(long productId, long quantity) {
        return new ProductQuantity(productId, quantity);
    }

    public long getQuantity() {
        return quantity;
    }

    public long getProductId() {
        return productId;
    }
}
