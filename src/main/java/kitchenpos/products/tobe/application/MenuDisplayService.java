package kitchenpos.products.tobe.application;

import kitchenpos.products.tobe.ProductPrices;

import java.util.UUID;

public interface MenuDisplayService {

    void display(UUID productId, ProductPrices productPrices);
}
