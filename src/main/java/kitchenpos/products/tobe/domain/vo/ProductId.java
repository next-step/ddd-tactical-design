package kitchenpos.products.tobe.domain.vo;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class ProductId implements Serializable {
    private static final long serialVersionUID = -26368028889583549L;

    private UUID id;

    public ProductId() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ProductId(UUID id) {
        this.id = id;
    }
}
