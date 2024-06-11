package kitchenpos.menus.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.menugroups.domain.MenuGroupRepository;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.menus.domain.tobe.Menu;
import kitchenpos.menus.domain.tobe.MenuPrice;
import kitchenpos.menus.domain.tobe.MenuProducts;
import kitchenpos.menugroups.domain.tobe.MenuGroup;
import kitchenpos.menus.ui.dto.MenuCreateRequest;
import kitchenpos.products.domain.ProfanityValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final ProfanityValidator profanityValidator;
    private final MenuProductsService menuProductsService;

    public MenuService(
            final MenuRepository menuRepository,
            final MenuGroupRepository menuGroupRepository,
            final ProfanityValidator profanityValidator,
            final MenuProductsService menuProductsService
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.profanityValidator = profanityValidator;
        this.menuProductsService = menuProductsService;
    }

    @Transactional
    public Menu create(final MenuCreateRequest request) {
        request.validateName(profanityValidator);
        final MenuGroup menuGroup = findMenuGroup(request.getMenuGroupId());
        final MenuProducts menuProducts = menuProductsService.create(request.getMenuProducts(),
                request.getPrice());
        return menuRepository.save(request.to(menuGroup, menuProducts));
    }

    @Transactional
    public Menu changePrice(final UUID menuId, final MenuPrice request) {
        final Menu menu = findMenu(menuId);
        menuProductsService.validateMenuPrice(menu.getMenuProducts(), request);
        menu.changePrice(request);
        return menu;
    }

    @Transactional
    public Menu display(final UUID menuId) {
        final Menu menu = findMenu(menuId);
        menuProductsService.validateMenuPrice(menu.getMenuProducts(), menu.getMenuPrice());
        menu.display();
        return menu;
    }

    @Transactional
    public Menu hide(final UUID menuId) {
        final Menu menu = findMenu(menuId);
        menu.hide();
        return menu;
    }

    @Transactional(readOnly = true)
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }

    private MenuGroup findMenuGroup(UUID menuGroupId) {
        return menuGroupRepository.findById(menuGroupId)
                .orElseThrow(NoSuchElementException::new);
    }

    private Menu findMenu(UUID menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);
    }
}
