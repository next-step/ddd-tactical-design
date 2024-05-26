package kitchenpos.products.tobe.application;

import kitchenpos.products.tobe.domain.DisplayName;
import kitchenpos.products.tobe.domain.Price;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.dto.ProductDto;

import java.util.UUID;

public class ProductFixture {
    public static ProductDto createProductDtoRequest(final String name, final long price) {
        return new ProductDto(UUID.randomUUID(),name, price);
    }
    public static Product createProductRequest(final String name, final long price) {
        return new Product(new DisplayName(name), new Price(price));
    }

public static ProductDto changePriceDtoRequest(final long price) {
        Product product = createProductRequest("후라이드", 16000L);
        product.changePrice(new Price(price));
        return ProductDto.from(product);
    }
}
