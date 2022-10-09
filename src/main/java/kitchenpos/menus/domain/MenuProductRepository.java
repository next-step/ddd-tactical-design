package kitchenpos.menus.domain;

import java.util.List;
import java.util.UUID;

public interface MenuProductRepository {

    List<MenuProduct> findAllByProductId(UUID productId);
}
