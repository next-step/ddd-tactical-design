package kitchenpos.menus.tobe.domain;

import java.util.List;
import java.util.UUID;
import kitchenpos.menus.tobe.dto.ProductDTO;

public interface ProductServerClient {

    List<ProductDTO> findAllByIds(List<UUID> productIds);
}
