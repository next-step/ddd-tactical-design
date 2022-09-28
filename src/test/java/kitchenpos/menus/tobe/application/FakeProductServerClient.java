package kitchenpos.menus.tobe.application;

import java.util.List;
import java.util.UUID;
import kitchenpos.menus.tobe.domain.ProductServerClient;
import kitchenpos.menus.tobe.dto.ProductDTO;

public class FakeProductServerClient implements ProductServerClient {

    private final List<ProductDTO> productDTOS;

    public FakeProductServerClient(List<ProductDTO> productDTOS) {
        this.productDTOS = productDTOS;
    }

    @Override
    public List<ProductDTO> findAllByIds(List<UUID> productIds) {
        return productDTOS;
    }
}
