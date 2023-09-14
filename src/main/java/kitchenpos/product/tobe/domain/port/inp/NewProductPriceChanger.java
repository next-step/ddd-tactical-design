package kitchenpos.product.tobe.domain.port.inp;

import kitchenpos.product.tobe.domain.NewProduct;

public interface NewProductPriceChanger {
    NewProduct change(NewProductPriceChangerCommand command);
}
