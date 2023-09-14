package kitchenpos.product.tobe.domain.port.inp;

import kitchenpos.product.tobe.domain.NewProduct;

public interface NewProductCreator {
    NewProduct create(NewProductCreatorCommand command);
}
