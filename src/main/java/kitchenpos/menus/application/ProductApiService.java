package kitchenpos.menus.application;

import java.util.List;
import java.util.UUID;

public interface ProductApiService {
    List<ProductDto> findAllByIdIn(List<UUID> collect);
}
