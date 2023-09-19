package kitchenpos.menus.tobe.domain;

import java.util.List;
import java.util.UUID;
import kitchenpos.eatinorders.tobe.domain.OrderCreateRequested;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class MenuOrderService {

    private final MenuRepository menuRepository;

    public MenuOrderService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @EventListener
    public void eventListener(OrderCreateRequested orderCreateRequested) {
        List<UUID> menuIds = orderCreateRequested.getMenuIds();
        List<Menu> menus = menuRepository.findAllByIdIn(menuIds);

        if (menus.size() != menuIds.size()) {
            throw new IllegalArgumentException("존재하지 않는 메뉴에 대한 주문이 되었습니다");
        }

        for (Menu menu : menus) {
            checkMenuIsDisplayed(menu);
        }
    }

    private void checkMenuIsDisplayed(Menu menu) {
        if(!menu.isDisplay()) {
            throw new IllegalStateException("표시되지 않은 메뉴에 대해 주문되었습니다");
        }
    }
}
