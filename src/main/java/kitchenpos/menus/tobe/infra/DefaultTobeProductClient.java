package kitchenpos.menus.tobe.infra;


import kitchenpos.menus.tobe.domain.menu.TobeProductClient;
import kitchenpos.products.tobe.domain.TobeProductRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class DefaultTobeProductClient implements TobeProductClient {
    private final TobeProductRepository tobeProductRepository;

    public DefaultTobeProductClient(TobeProductRepository tobeProductRepository) {
        this.tobeProductRepository = tobeProductRepository;
    }

    @Override
    public BigDecimal getProductPrice(final UUID productId) {
        return tobeProductRepository.findById(productId).orElseThrow().getPriceValue();
    }
}
