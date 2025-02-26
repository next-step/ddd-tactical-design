package kitchenpos.product.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.UUID;
import kitchenpos.global.exception.ErrorCode;
import kitchenpos.global.exception.NotFoundException;

@Embeddable
public record ProductId(
    UUID id
) {

    public static ProductId of(UUID id) {
        if (id == null) {
            throw new NotFoundException(ErrorCode.NOT_FOUND_PRODUCT.toString());
        }
        return new ProductId(id);
    }


    public UUID get() {
        return id;
    }
}