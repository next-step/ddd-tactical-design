package kitchenpos.products.tobe.domain.vo;

import java.io.Serializable;
import java.util.UUID;

public class ProductId implements Serializable {
    private static final long serialVersionUID = -26368028889583549L;

    private UUID id;

    public UUID getValue() {
        return this.id;
    }

    public ProductId(UUID id) {
        this.id = id;
    }
}
