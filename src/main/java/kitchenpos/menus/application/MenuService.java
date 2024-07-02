package kitchenpos.menus.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.menugroups.domain.MenuGroupRepository;
import kitchenpos.menugroups.domain.tobe.MenuGroup;
import kitchenpos.menus.application.dto.MenuCreateRequest;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.menus.domain.tobe.Menu;
import kitchenpos.menus.domain.tobe.MenuPrice;
import kitchenpos.menus.domain.tobe.MenuProducts;
import kitchenpos.products.domain.ProfanityValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final ProfanityValidator profanityValidator;
    private final MenuProductMapper menuProductMapper;

    public MenuService(MenuRepository menuRepository, MenuGroupRepository menuGroupRepository,
        ProfanityValidator profanityValidator, MenuProductMapper menuProductMapper) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.profanityValidator = profanityValidator;
        this.menuProductMapper = menuProductMapper;
    }

    @Transactional
    public Menu create(final MenuCreateRequest request) {
        profanityValidator.validate(request.name().getName());
        final MenuGroup menuGroup = findMenuGroup(request.menuGroupId());
        final MenuProducts menuProducts = menuProductMapper.map(
            request.menuProductsCreateRequest());

        final Menu menu = new Menu(
            request.name(),
            request.price(),
            menuGroup,
            request.displayed(),
            menuProducts);
        return menuRepository.save(menu);
    }


    @Transactional
    public Menu changePrice(final UUID menuId, final MenuPrice request) {
        final Menu menu = findMenu(menuId);
        menu.changePrice(request);
        return menu;
    }

    @Transactional
    public Menu display(final UUID menuId) {
        final Menu menu = findMenu(menuId);
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
