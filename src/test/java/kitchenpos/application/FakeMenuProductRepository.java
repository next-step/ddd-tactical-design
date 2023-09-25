package kitchenpos.application;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.menu.tobe.domain.MenuProductRepository;

public class FakeMenuProductRepository implements MenuProductRepository {

    @Override
    public void bulkUpdateMenuProductPrice(UUID productId, BigDecimal bigDecimal) {

    }
}
