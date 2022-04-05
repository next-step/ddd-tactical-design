package kitchenpos.eatinorders.tobe.eatinorder.infra;

import kitchenpos.eatinorders.tobe.eatinorder.application.MenuClient;
import kitchenpos.eatinorders.tobe.eatinorder.ui.dto.MenuResponse;
import kitchenpos.menus.tobe.menu.application.MenuService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class RestMenuClient implements MenuClient {

    private final MenuService menuService; // 원래는 HTTP client 활용하여 요청

    public RestMenuClient(final MenuService menuService) {
        this.menuService = menuService;
    }

    @Override
    public List<MenuResponse> findAllByIdn(final List<UUID> menuIds) {
        return menuService.findAllByIdn(menuIds)
                .stream()
                .map(menu -> new MenuResponse(menu.getId(), menu.getPrice(), menu.isDisplayed()))
                .collect(Collectors.toList());
    }
}
