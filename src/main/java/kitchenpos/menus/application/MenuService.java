package kitchenpos.menus.application;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.menus.domain.exception.InvalidMenuProductsSizeException;
import kitchenpos.menus.domain.exception.NotFoundMenuException;
import kitchenpos.menus.domain.model.MenuModel;
import kitchenpos.products.application.ProductService;
import kitchenpos.products.domain.vo.Products;
import kitchenpos.products.infra.PurgomalumClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final ProductService productService;
    private final MenuGroupService menuGroupService;
    private final PurgomalumClient purgomalumClient;

    public MenuService(
            final MenuRepository menuRepository,
            final ProductService productService,
            final MenuGroupService menuGroupService,
            final PurgomalumClient purgomalumClient) {
        this.menuRepository = menuRepository;
        this.productService = productService;
        this.menuGroupService = menuGroupService;
        this.purgomalumClient = purgomalumClient;
    }

    @Transactional
    public Menu create(final Menu request) {
        validMenuProduct(request);
        menuGroupService.findById(request.getMenuGroupId());
        return menuRepository.save(new MenuModel(request, purgomalumClient).toMenu());
    }

    @Transactional
    public Menu changePrice(final UUID menuId, final Menu request) {
        return new MenuModel(getMenu(menuId), purgomalumClient)
                .changePrice(request.getPrice())
                .toMenu();
    }

    @Transactional
    public Menu display(final UUID menuId) {
        return new MenuModel(getMenu(menuId), purgomalumClient)
                .displayed()
                .toMenu();
    }

    @Transactional
    public Menu hide(final UUID menuId) {
        return new MenuModel(getMenu(menuId), purgomalumClient)
                .hide()
                .toMenu();
    }

    @Transactional(readOnly = true)
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Menu getMenu(UUID menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(NotFoundMenuException::new);
    }

    private void validMenuProduct(Menu request) {
        List<MenuProduct> menuProducts = request.getMenuProducts();
        if (menuProducts == null || menuProducts.isEmpty()) {
            throw new InvalidMenuProductsSizeException();
        }
        Products products = productService.findAllByIdIn(menuProducts.stream()
                .map(MenuProduct::getProductId)
                .collect(Collectors.toList()));

        if (products.size() != menuProducts.size()) {
            throw new InvalidMenuProductsSizeException();
        }
    }
}
