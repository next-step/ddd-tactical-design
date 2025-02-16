package kitchenpos.menu.application.port.out;

import kitchenpos.menu.domain.model.MenuGroup;

import java.util.List;

public interface LoadMenuGroupPort {
    List<MenuGroup> findAll();
}
