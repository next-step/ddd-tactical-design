package kitchenpos.menus.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kitchenpos.menus.application.dto.ChangeMenuPriceRequest;
import kitchenpos.menus.application.dto.MenuResponse;
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
    public MenuResponse create(final MenuVo menuVo) {
        Menu menu = menuFactory.create(menuVo);
        return new MenuResponse(menuRepository.save(menu));
    }

    @Transactional
    public MenuResponse changePrice(final UUID menuId, final ChangeMenuPriceRequest request) {
        final Menu menu = findById(menuId);
        menu.changePrice(new Price(request.getPrice()), menuPricePolicy);
        return new MenuResponse(menu);
    }

    @Transactional
    public MenuResponse display(final UUID menuId) {
        final Menu menu = findById(menuId);
        menu.display(menuPricePolicy);
        return new MenuResponse(menu);
    }

    @Transactional
    public MenuResponse hide(final UUID menuId) {
        final Menu menu = findById(menuId);
        menu.hide();
        return new MenuResponse(menu);
    }

    @Transactional(readOnly = true)
    public List<MenuResponse> findAll() {
        return menuRepository.findAll()
                .stream()
                .map(MenuResponse::new)
                .collect(Collectors.toUnmodifiableList());
    }

    private Menu findById(UUID menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);
    }
}
