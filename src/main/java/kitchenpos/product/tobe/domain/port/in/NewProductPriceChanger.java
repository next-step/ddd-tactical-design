package kitchenpos.product.tobe.domain.port.in;

import kitchenpos.product.tobe.domain.NewProduct;

public interface NewProductPriceChanger {
    NewProduct change(NewProductPriceChangerCommand command);
}
