package kitchenpos.menu.domain.service;

import java.util.List;
import kitchenpos.menu.domain.model.MenuGroupVo;

public interface MenuGroupQueryService {
    List<MenuGroupVo.GroupInfo> findAll();
}
