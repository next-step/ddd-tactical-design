package kitchenpos.menu.domain.service;

import java.math.BigDecimal;
import java.util.List;
import kitchenpos.global.exception.ErrorCode;
import kitchenpos.global.exception.NotFoundException;
import kitchenpos.menu.domain.model.MenuId;
import kitchenpos.menu.domain.model.MenuVo.MenuInfo;
import kitchenpos.menu.domain.repository.MenuRepository;
import kitchenpos.order.common.application.MenuContextProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class DefaultMenuContextService implements MenuContextProvider {

    private final MenuRepository menuRepository;

    public DefaultMenuContextService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public BigDecimal getPrice(MenuId menuId) {
        return menuRepository.findByMenuId(menuId)
            .map(menu -> menu.getPrice().price())
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_MENU.toString()));
    }

    @Override
    public List<MenuInfo> findMenus(List<MenuId> menuIds) {
        return menuRepository.findAllByMenuIdIn(menuIds).stream()
            .map(MenuInfo::fromEntity)
            .toList();
    }
}
