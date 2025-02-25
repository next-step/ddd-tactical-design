package kitchenpos.menu.domain.service;

import java.util.List;
import kitchenpos.menu.domain.model.MenuGroupVo;

public interface MenuGroupCommandService {
    MenuGroupVo.GroupInfo create(final MenuGroupVo.Create request);
}
