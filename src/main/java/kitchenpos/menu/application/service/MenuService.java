package kitchenpos.menu.application.service;

import kitchenpos.menu.application.port.out.LoadMenuPort;
import kitchenpos.menu.application.port.out.MenuProductMapper;
import kitchenpos.menu.application.port.out.SaveMenuPort;
import kitchenpos.menu.application.service.model.ChangeMenuPriceRequest;
import kitchenpos.menu.application.service.model.CreateMenuRequest;
import kitchenpos.menu.domain.exception.MenuNotFoundException;
import kitchenpos.menu.domain.model.Menu;
import kitchenpos.menu.domain.model.MenuProduct;
import kitchenpos.shared.domain.Profanities;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class MenuService {
    private final MenuProductMapper menuProductMapper;
    private final LoadMenuPort loadMenuPort;
    private final SaveMenuPort saveMenuPort;
    private final Profanities profanities;

    public MenuService(
            final MenuProductMapper menuProductMapper,
            final LoadMenuPort loadMenuPort,
            final SaveMenuPort saveMenuPort,
            final Profanities profanities
    ) {
        this.menuProductMapper = menuProductMapper;
        this.loadMenuPort = loadMenuPort;
        this.saveMenuPort = saveMenuPort;
        this.profanities = profanities;
    }

    @Transactional
    public Menu create(final CreateMenuRequest request) {
        final List<MenuProduct> menuProducts = menuProductMapper.toMenuProducts(request.getProductQuantities());
        final Menu menu = Menu.create(
                request.getName(),
                request.getPrice(),
                request.isDisplayed(),
                request.getMenuGroupId(),
                menuProducts,
                profanities);
        return saveMenuPort.save(menu);
    }

    @Transactional
    public Menu changePrice(final UUID menuId, final ChangeMenuPriceRequest request) {
        final Menu menu = loadMenuPort.findById(menuId)
            .orElseThrow(() -> new MenuNotFoundException(menuId));
        menu.changePrice(request.getPrice());
        return menu;
    }

    @Transactional
    public Menu display(final UUID menuId) {
        final Menu menu = loadMenuPort.findById(menuId)
            .orElseThrow(() -> new MenuNotFoundException(menuId));
        menu.display();
        return menu;
    }

    @Transactional
    public Menu hide(final UUID menuId) {
        final Menu menu = loadMenuPort.findById(menuId)
            .orElseThrow(() -> new MenuNotFoundException(menuId));
        menu.hide();
        return menu;
    }

    @Transactional(readOnly = true)
    public List<Menu> findAll() {
        return loadMenuPort.findAll();
    }

    @Transactional(readOnly = true)
    public List<Menu>  findAll(List<UUID> ids) {
        return loadMenuPort.findAllByIdIn(ids);
    }
}
