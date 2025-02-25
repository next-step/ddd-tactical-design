package kitchenpos.menu.domain.service;

import java.util.List;
import java.util.UUID;
import kitchenpos.menu.domain.model.MenuVo;

public interface MenuQueryService {
    List<MenuVo.MenuInfo> findAll();
}
