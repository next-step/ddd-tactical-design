package kitchenpos.orders.tobe.application.adapter;

import java.util.List;
import java.util.UUID;

import kitchenpos.menus.tobe.domain.Menu;

public interface MenuServiceAdapter {
    List<Menu> findAllByIdIn(List<UUID> ids);
}