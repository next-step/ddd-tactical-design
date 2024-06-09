package kitchenpos.menus.tobe.application;

import kitchenpos.menus.tobe.domain.ProductInfo;
import kitchenpos.products.tobe.domain.Product;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

public class FackProductDomainService implements ProductDomainService {

    private final Map<UUID, Product> inMemoryDatabase = new HashMap<>();

    public FackProductDomainService(Product product) {
        inMemoryDatabase.put(product.getId(), product);
    }

    @Override
    public ProductInfo fetchProductInfo(UUID productId) {
        Product product = inMemoryDatabase.get(productId);
        if (Objects.isNull(product)) {
            throw new NoSuchElementException("상품이 존재하지 않습니다.");
        }
        return ProductInfo.is(product.getId(), product.getPrice());
    }
}
