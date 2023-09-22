package kitchenpos.menus.application;

import java.util.List;
import java.util.UUID;

import kitchenpos.menus.application.dto.ProductDto;

public interface ProductApiService {
    List<ProductDto> findAllByIdIn(List<UUID> collect);
}
