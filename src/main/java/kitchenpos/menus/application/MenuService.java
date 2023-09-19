package kitchenpos.menus.application;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.menus.domain.exception.NotFoundMenuException;
import kitchenpos.products.infra.PurgomalumClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuCreateService menuCreateService;
    private final MenuGroupService menuGroupService;
    private final PurgomalumClient purgomalumClient;

    public MenuService(
            final MenuRepository menuRepository,
            final MenuCreateService menuCreateService,
            final MenuGroupService menuGroupService,
            final PurgomalumClient purgomalumClient) {
        this.menuRepository = menuRepository;
        this.menuCreateService = menuCreateService;
        this.menuGroupService = menuGroupService;
        this.purgomalumClient = purgomalumClient;
    }

    @Transactional
    public Menu create(final Menu request) {
        menuCreateService.validMenuProduct(request);
        menuGroupService.validMenuGroupId(request.getMenuGroupId());
        return menuRepository.save(new Menu(request, purgomalumClient));
    }

    @Transactional
    public Menu changePrice(final UUID menuId, final BigDecimal price) {
        Menu menu = getMenu(menuId).changePrice(price);
        return this.menuRepository.save(menu);
    }

    public void changeProductPrice(final UUID productId, final BigDecimal price) {
        this.menuRepository.findAllByProductId(productId)
                .forEach(menu -> changePrice(menu.getId(), price));
    }

    @Transactional
    public Menu display(final UUID menuId) {
        Menu menu = getMenu(menuId).displayed();
        return this.menuRepository.save(menu);
    }

    @Transactional
    public Menu hide(final UUID menuId) {
        Menu menu = getMenu(menuId).hide();
        return this.menuRepository.save(menu);
    }

    @Transactional(readOnly = true)
    public List<Menu> findAll() {
        return this.menuRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Menu getMenu(UUID menuId) {
        Menu menu = this.menuRepository.findById(menuId)
                .orElseThrow(NotFoundMenuException::new);
        menuGroupService.validMenuGroupId(menu.getMenuGroupId());
        return menu;
    }


}
