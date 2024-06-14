package kitchenpos.products.tobe.application;

import kitchenpos.products.tobe.domain.*;
import kitchenpos.products.tobe.dto.ProductDto;

import java.util.UUID;

public class ProductFixture {

    public static ProductDto createProductDtoRequest(final String name, final long price) {
        return new ProductDto(UUID.randomUUID(), name, price);
    }

    public static Product createProductRequest(final String name, final long price, final DisplayNamePolicy displayNamePolicy) {
        return new Product(name, new Price(price), displayNamePolicy);
    }

    public static ProductDto changePriceDtoRequest(final long price, DisplayNamePolicy displayNamePolicy) {
        Product product = createProductRequest("후라이드", 16000L, displayNamePolicy);
        product.changePrice(new Price(price));
        return ProductDto.from(product);
    }
}
