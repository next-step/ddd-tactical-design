package kitchenpos.menus.domain;

import java.util.List;
import java.util.UUID;

public interface ProductRepostiory {
    List<NewProduct> findAllByIdIn(List<UUID> productIds);
}
