package kitchenpos.eatinorders.infrastructure;

import kitchenpos.eatinorders.domain.orders.MenuClient;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Component
public class MenuClientImpl implements MenuClient {
    private final MenuRepository menuRepository;

    public MenuClientImpl(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public void validMenuIds(List<UUID> menuIds) {
        final List<Menu> menus = menuRepository.findAllByIdIn(menuIds);
        if (menus.size() != new HashSet<>(menuIds).size()) {
            throw new IllegalArgumentException("Menu가 존재하지 않는 메뉴를 포함하고 있습니다.");
        }
    }

    @Override
    public boolean isHide(UUID menuId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new NoSuchElementException("Menu를 조회할 수 없습니다. Menu가 존재하지 않습니다. menuId: " + menuId));
        return menu.isHide();
    }

    @Override
    public BigDecimal getMenuPrice(UUID menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalArgumentException("Menu 가격을 조회할 수 없습니다. Menu Id가 존재하지 않습니다. menuId: " + menuId))
                .getPriceValue();
    }
}
