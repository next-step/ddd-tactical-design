package kitchenpos.product.application;

import static kitchenpos.product.support.constant.Name.ENTITY;
import static kitchenpos.support.ParameterValidateUtils.checkNotNull;

import com.google.common.base.MoreObjects;
import java.util.UUID;
import kitchenpos.product.domain.ProductName;
import kitchenpos.product.domain.ProductNew;
import kitchenpos.product.domain.ProductPrice;

public final class ProductDTO {

    private final UUID id;
    private final ProductPrice price;
    private final ProductName name;

    public ProductDTO(final ProductNew entity) {
        checkNotNull(entity, ENTITY);

        id = entity.getId();
        price = entity.getPrice();
        name = entity.getName();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("id", id)
            .add("price", price)
            .add("name", name)
            .toString();
    }
}
