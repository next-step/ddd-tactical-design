package kitchenpos.menus.tobe.application;

import kitchenpos.common.infra.Profanities;
import kitchenpos.menus.tobe.domain.*;
import kitchenpos.menus.tobe.domain.menuproducts.MenuProductTranslator;
import kitchenpos.menus.tobe.domain.menuproducts.MenuProducts;
import kitchenpos.menus.tobe.dto.ChangeMenuPriceRequest;
import kitchenpos.menus.tobe.dto.CreateMenuRequest;
import kitchenpos.menus.tobe.dto.MenuResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service("TobeMenuService")
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuGroupTranslator menuGroupTranslator;
    private final MenuProductTranslator menuProductTranslator;
    private final Profanities profanities;

    public MenuService(
            final MenuRepository menuRepository,
            final MenuGroupTranslator menuGroupTranslator,
            final MenuProductTranslator menuProductTranslator,
            final Profanities profanities
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupTranslator = menuGroupTranslator;
        this.menuProductTranslator = menuProductTranslator;
        this.profanities = profanities;
    }

    @Transactional
    public MenuResponse create(final CreateMenuRequest request) {
        final MenuInfo menuInfo = new MenuInfo(
                new MenuName(request.getName(), profanities),
                new MenuPrice(request.getPrice()),
                request.isDisplayed(),
                request.getMenuGroupId());
        final MenuProducts menuProducts = menuProductTranslator.getMenuProducts(request.getMenuProducts());
        final Menu menu = new Menu(menuInfo, menuProducts);
        menuRepository.save(menu);
        return menu2Response(menu);
    }

    @Transactional
    public MenuResponse changePrice(final UUID menuId, final ChangeMenuPriceRequest request) {
        final MenuPrice menuPrice = new MenuPrice(request.getPrice());
        final Menu menu = menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);
        menu.changePrice(menuPrice);
        return menu2Response(menu);
    }

    @Transactional
    public void updateStatus(final UUID productId) {
        final List<Menu> menus = menuRepository.findAllByProductId(productId);
        menus.forEach(Menu::updateStatus);
    }

    @Transactional
    public MenuResponse display(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);
        menu.display();
        return menu2Response(menu);
    }

    @Transactional
    public MenuResponse hide(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);
        menu.hide();
        return menu2Response(menu);
    }

    @Transactional(readOnly = true)
    public List<MenuResponse> findAll() {
        return menuRepository.findAll().stream()
                .map(this::menu2Response).collect(Collectors.toList());
    }

    private MenuResponse menu2Response(final Menu menu) {
        return MenuResponse.from(menu, menuGroupTranslator.getMenuGroup(menu.getMenuGroupId()));
    }
}
