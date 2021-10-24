package kitchenpos.eatinorders.tobe.infra;

import kitchenpos.eatinorders.tobe.domain.Menu;
import kitchenpos.menus.tobe.application.TobeMenuService;
import kitchenpos.menus.tobe.ui.MenuForm;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderMenuHandlerAdaptor implements OrderMenuAdaptor {
    private final TobeMenuService menuService;

    public OrderMenuHandlerAdaptor(TobeMenuService menuService) {
        this.menuService = menuService;
    }

    @Override
    public List<Menu> findAllByIdIn(List<UUID> ids) {
        List<Menu> menus = menuService.findAllByIdIn(ids).stream()
                .map(form -> menuFormToMenu(form))
                .collect(Collectors.toList());
        return menus;
    }

    private Menu menuFormToMenu(MenuForm form) {
        return new Menu(
            form.getId(),
            form.getName(),
            form.getPrice(),
            form.isDisplayed()
        );
    }
}
