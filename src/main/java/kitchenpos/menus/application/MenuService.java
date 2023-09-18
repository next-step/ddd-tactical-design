package kitchenpos.menus.application;

import kitchenpos.menus.application.dto.MenuPriceChangeRequest;
import kitchenpos.menus.application.dto.MenuCreateRequest;
import kitchenpos.menus.tobe.domain.menu.*;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroup;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final ProductClient productClient;
    private final MenuDisplayedNamePolicy menuDisplayedNamePolicy;

    public MenuService(
        final MenuRepository menuRepository,
        final MenuGroupRepository menuGroupRepository,
        final ProductClient productClient,
        final MenuDisplayedNamePolicy menuDisplayedNamePolicy
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.productClient = productClient;
        this.menuDisplayedNamePolicy = menuDisplayedNamePolicy;
    }

    @Transactional
    public Menu create(final MenuCreateRequest request) {
        final MenuGroup menuGroup = menuGroupRepository.findById(request.getMenuGroupId())
            .orElseThrow(NoSuchElementException::new);
        if (request.getMenuProducts() == null) {
            throw new IllegalArgumentException();
        }
        List<MenuProductMaterial> menuProductMaterials = request.getMenuProducts().stream()
                .map(it -> new MenuProductMaterial(it.getProductId(), it.getQuantity()))
                .collect(Collectors.toList());
        final Menu menu = new Menu(
                UUID.randomUUID(),
                MenuDisplayedName.from(request.getName(), menuDisplayedNamePolicy),
                MenuPrice.from(request.getPrice()),
                menuGroup,
                request.isDisplayed(),
                MenuProducts.from(menuProductMaterials, productClient)
        );
        return menuRepository.save(menu);
    }

    @Transactional
    public Menu changePrice(final UUID menuId, final MenuPriceChangeRequest request) {
        final MenuPrice price = MenuPrice.from(request.getPrice());
        final Menu menu = menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
        menu.changePrice(price);
        return menu;
    }

    @Transactional
    public Menu display(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
        menu.display();
        return menu;
    }

    @Transactional
    public Menu hide(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
        menu.hide();
        return menu;
    }

    @Transactional(readOnly = true)
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }
}
