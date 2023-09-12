package kitchenpos.menus.application;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuGroup;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.menus.domain.exception.InvalidMenuProductsSizeException;
import kitchenpos.menus.domain.exception.NotFoundMenuException;
import kitchenpos.menus.domain.vo.MenuProducts;
import kitchenpos.products.application.ProductService;
import kitchenpos.products.domain.vo.Products;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final ProductService productService;
    private final MenuGroupService menuGroupService;

    public MenuService(
            final MenuRepository menuRepository,
            final ProductService productService,
            final MenuGroupService menuGroupService) {

        this.menuRepository = menuRepository;
        this.productService = productService;
        this.menuGroupService = menuGroupService;
    }

    @Transactional
    public Menu create(final Menu request) {
        validMenuProduct(request);
        return menuRepository.save(new Menu(UUID.randomUUID(),
                request.getName(),
                request.getPrice(),
                getMenuGroup(request),
                request.isDisplayed(),
                request.checkMenuProductPrice(productService).getMenuProducts().getMenuProducts(),
                getMenuGroup(request).getId())
        );

    }

    @Transactional
    public Menu changePrice(final UUID menuId, final Menu request) {
        return getMenu(menuId)
                .checkMenuProductPrice(request.getPrice())
                .changePrice(request.getPrice());
    }

    @Transactional
    public Menu display(final UUID menuId) {
        return getMenu(menuId)
                .checkMenuProductPrice()
                .displayed();
    }

    @Transactional
    public Menu hide(final UUID menuId) {
        return getMenu(menuId)
                .hide();

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

    private MenuGroup getMenuGroup(Menu request) {
        return this.menuGroupService.findById(request.getMenuGroupId());
    }

    private void validMenuProduct(Menu request) {
        MenuProducts menuProductRequests = request.getMenuProducts();
        Products products = productService.findAllByIdIn(menuProductRequests);

        if (products.size() != menuProductRequests.size()) {
            throw new InvalidMenuProductsSizeException();
        }
    }
}
