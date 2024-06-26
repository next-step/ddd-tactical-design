package kitchenpos.menus.tobe.application;

import kitchenpos.products.tobe.ProductPrices;

import java.util.List;
import java.util.UUID;

public interface ProductPriceService {
    ProductPrices getProductPrices(List<UUID> productIds);
}
