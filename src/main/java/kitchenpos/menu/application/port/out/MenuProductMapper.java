package kitchenpos.menu.application.port.out;

import kitchenpos.menu.domain.model.MenuProduct;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface MenuProductMapper {
    List<MenuProduct> toMenuProducts(Map<UUID, Long> productQuantities);
}
