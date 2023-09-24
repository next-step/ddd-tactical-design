package kitchenpos.menus.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kitchenpos.menus.application.dto.ChangeMenuPriceRequest;
import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuFactory;
import kitchenpos.menus.domain.MenuPricePolicy;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.menus.domain.Price;
import kitchenpos.menus.domain.vo.MenuVo;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuPricePolicy menuPricePolicy;
    private final MenuFactory menuFactory;

    public MenuService(
            final MenuRepository menuRepository,
            final MenuPricePolicy menuPricePolicy,
            final MenuFactory menuFactory) {
        this.menuRepository = menuRepository;
        this.menuPricePolicy = menuPricePolicy;
        this.menuFactory = menuFactory;
    }

    @Transactional
    public Menu create(final MenuVo menuVo) {
        Menu menu = menuFactory.create(menuVo);
        return menuRepository.save(menu);
    }

    @Transactional
    public Menu changePrice(final UUID menuId, final ChangeMenuPriceRequest request) {
        final Menu menu = findById(menuId);
        menu.changePrice(new Price(request.getPrice()), menuPricePolicy);
        return menu;
    }

    @Transactional
    public Menu display(final UUID menuId) {
        final Menu menu = findById(menuId);
        menu.display(menuPricePolicy);
        return menu;
    }

    @Transactional
    public Menu hide(final UUID menuId) {
        final Menu menu = findById(menuId);
        menu.hide();
        return menu;
    }

    @Transactional(readOnly = true)
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }

    private Menu findById(UUID menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);
    }
}
