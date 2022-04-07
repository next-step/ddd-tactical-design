package kitchenpos.eatinorders.tobe.eatinorder.application;

import kitchenpos.eatinorders.tobe.eatinorder.ui.dto.MenuResponse;

import java.util.List;
import java.util.UUID;

public interface MenuRepository {
    List<MenuResponse> findAllByIdn(final List<UUID> menuIds);
}
