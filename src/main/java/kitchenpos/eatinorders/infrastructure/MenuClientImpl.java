package kitchenpos.eatinorders.infrastructure;

import kitchenpos.eatinorders.domain.orders.MenuClient;
import kitchenpos.eatinorders.domain.orders.OrderedMenu;
import kitchenpos.eatinorders.domain.orders.OrderedMenus;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class MenuClientImpl implements MenuClient {
    private final MenuRepository menuRepository;

    public MenuClientImpl(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public boolean isHide(UUID menuId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalArgumentException("Menu를 조회할 수 없습니다. Menu가 존재하지 않습니다. menuId: " + menuId));
        return menu.isHide();
    }

    @Override
    public BigDecimal getMenuPrice(UUID menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalArgumentException("Menu 가격을 조회할 수 없습니다. Menu Id가 존재하지 않습니다. menuId: " + menuId))
                .getPriceValue();
    }

    @Override
    public OrderedMenus getOrderedMenuByMenuIds(List<UUID> menuIds) {
        return menuRepository.findAllByIdIn(menuIds).stream()
                .map(it -> new OrderedMenu(it.getId(), it.getPriceValue(), it.isDisplayed()))
                .collect(Collectors.collectingAndThen(Collectors.toList(), OrderedMenus::new));
    }
}
