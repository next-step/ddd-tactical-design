package kitchenpos.products.domain.tobe.domain.vo;

import kitchenpos.support.vo.Id;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class ProductId extends Id implements Serializable {
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
