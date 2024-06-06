package kitchenpos.eatinorders.tobe.domain.service;

import kitchenpos.eatinorders.tobe.domain.entity.EatInOrder;
import kitchenpos.eatinorders.tobe.domain.entity.OrderTable;
import kitchenpos.eatinorders.tobe.domain.repository.OrderTableRepository;
import kitchenpos.menus.tobe.domain.entity.Menu;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;

public class EatInOrderAccept {
    private OrderTableRepository tableRepository;
    private MenuRepository menuRepository;

    public EatInOrderAccept(OrderTableRepository tableRepository, MenuRepository menuRepository) {
        this.tableRepository = tableRepository;
        this.menuRepository = menuRepository;
    }

    public void checkRequiredList(EatInOrder order) {
        checkOrderTableIsOccupied(order.getOrderTableId());
        checkAllMenuIsDisplayed(order.allMenuId());
    }

    private void checkOrderTableIsOccupied(UUID orderId) {
        OrderTable table = tableRepository.findBy(orderId)
                .orElseThrow(() -> new NoSuchElementException());

        if (table.isNotOccupied()) {
            throw new IllegalStateException("빈 테이블입니다.");
        }
    }

    private void checkAllMenuIsDisplayed(Set<UUID> allMenuId) {
        boolean hasNotDisplayedMenu = allMenuId.stream()
                .anyMatch(menuId -> isNotDisplayedMenu(menuId));

        if (hasNotDisplayedMenu) {
            throw new IllegalStateException("숨긴 메뉴가 존재합니다.");
        }
    }

    private boolean isNotDisplayedMenu(UUID menuId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new NoSuchElementException());
        return menu.isNotDisplayed();
    }
}
