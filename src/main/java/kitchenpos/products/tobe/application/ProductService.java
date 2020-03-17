package kitchenpos.products.tobe.application;

import kitchenpos.products.tobe.dto.ProductDto;

import java.util.List;

public interface ProductService {

    ProductDto register(ProductDto dto);

    List<ProductDto> list();

}
