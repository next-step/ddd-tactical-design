package kitchenpos.menu.tobe.domain.menu;

import java.util.List;
import java.util.UUID;

public interface ProductClient {
    long countProductsByIds(List<UUID> productId);
}
