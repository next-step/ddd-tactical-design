package kitchenpos.eatinorders.tobe.domain.order;

import java.util.List;
import java.util.Optional;
import kitchenpos.common.domain.MenuId;
import kitchenpos.common.domain.Validator;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;

public class OrderValidator implements Validator<Order> {

    private final MenuRepository menuRepository;

    public OrderValidator(final MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public void validate(Order order) {
        final List<MenuId> menuIds = order.getMenuIds();

        if (menuIds.isEmpty()) {
            throw new IllegalArgumentException("1개 이상의 등록된 메뉴로 매장 주문을 등록할 수 있습니다.");
        }

        final List<Menu> menus = menuRepository.findAllByIdIn(menuIds);

        if (menuIds.size() != menus.size()) {
            throw new IllegalArgumentException("메뉴가 없으면 매장 주문을 등록할 수 없습니다.");
        }

        final Optional<Menu> maybeHiddenMenu = menus.stream()
            .filter(Menu::isHidden)
            .findAny();

        if (maybeHiddenMenu.isPresent()) {
            throw new IllegalArgumentException("숨겨진 메뉴는 주문할 수 없습니다.");
        }

        menus.forEach(menu -> {
                if (!menu.getPrice().equals(order.calculateTotalPrice())) {
                    throw new IllegalArgumentException("주문한 메뉴의 가격은 실제 메뉴 가격과 일치해야 합니다.");
                }
            }
        );
    }
}
