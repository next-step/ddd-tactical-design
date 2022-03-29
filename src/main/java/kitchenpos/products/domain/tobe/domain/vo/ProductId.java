package kitchenpos.products.domain.tobe.domain.vo;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class ProductId implements Serializable {
    @Column(name = "id")
    private UUID id;

    public ProductId(UUID id) {
        this.id = id;
    }

    protected ProductId() {
    }

    public UUID getValue() {
        return id;
    }
}
