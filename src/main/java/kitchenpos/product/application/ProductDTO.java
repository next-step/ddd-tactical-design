package kitchenpos.product.application;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.UUID;
import kitchenpos.product.domain.ProductName;
import kitchenpos.product.domain.ProductNew;
import kitchenpos.product.domain.ProductPrice;

public final class ProductDTO {

    private final UUID id;
    private final ProductPrice price;
    private final ProductName name;

    public ProductDTO(final ProductNew entity) {
        checkArgument(entity != null, "entity must not be null. entity: %s", entity);

        id = entity.getId();
        price = entity.getPrice();
        name = entity.getName();
    }
}
