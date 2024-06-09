package kitchenpos.menu.tobe.application;

import kitchenpos.menu.tobe.application.dto.ChangeMenuPrice;
import kitchenpos.menu.tobe.application.dto.CreateMenuProductRequest;
import kitchenpos.menu.tobe.application.dto.CreateMenuRequest;
import kitchenpos.menu.tobe.domain.menu.*;
import kitchenpos.menu.tobe.domain.menu.validate.ProductValidator;
import kitchenpos.menu.tobe.domain.menu.validate.ProfanityValidator;
import kitchenpos.menu.tobe.domain.menugroup.MenuGroup;
import kitchenpos.menu.tobe.domain.menugroup.MenuGroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service("newMenuService")
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final ProfanityValidator profanityValidator;
    private final ProductValidator productValidator;

    public MenuService(
            final MenuRepository menuRepository,
            final MenuGroupRepository menuGroupRepository,
            final ProfanityValidator profanityValidator,
            final ProductValidator productValidator
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.profanityValidator = profanityValidator;
        this.productValidator = productValidator;
    }

    @Transactional
    public Menu create(final CreateMenuRequest request) {
        MenuPrice menuPrice = new MenuPrice(request.price());
        MenuGroup menuGroup = getMenuGroup(request.menuGroupId());

        List<MenuProduct> menuProducts = createMenuProducts(request.menuProducts());
        MenuName menuName = new MenuName(request.name(), profanityValidator);

        Menu menu = new Menu(UUID.randomUUID(), menuName, menuPrice, menuGroup, request.display(), new MenuProducts(menuProducts, menuPrice, productValidator));
        return menuRepository.save(menu);
    }

    private MenuGroup getMenuGroup(UUID menuGroupId) {
        return menuGroupRepository.findById(menuGroupId)
                .orElseThrow(() -> new NoSuchElementException("메뉴 그룹을 찾을 수 없습니다."));
    }

    private List<MenuProduct> createMenuProducts(List<CreateMenuProductRequest> createMenuProductRequests) {
        return createMenuProductRequests.stream()
                .map(request -> new MenuProduct(request.productId(), request.quantity(), request.price()))
                .toList();
    }

    @Transactional
    public Menu changePrice(final UUID menuId, final ChangeMenuPrice request) {
        final BigDecimal price = request.price();
        final Menu menu = menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);

        menu.changePrice(price);

        return menu;
    }

    private Menu findMenuById(UUID menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(() -> new NoSuchElementException("메뉴를 찾을 수 없습니다."));
    }

    @Transactional
    public Menu display(final UUID menuId) {
        final Menu menu = findMenuById(menuId);
        menu.display();
        return menu;
    }

    @Transactional
    public Menu hide(final UUID menuId) {
        final Menu menu = findMenuById(menuId);
        menu.hide();
        return menu;
    }

    @Transactional(readOnly = true)
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }
}
