package kitchenpos.products.tobe.domain;

import kitchenpos.menus.domain.Menu;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductMenuRepository {

    List<Menu> findAllByProductId(UUID productId);
}

