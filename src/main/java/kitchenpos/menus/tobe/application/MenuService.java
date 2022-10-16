package kitchenpos.menus.tobe.application;


import kitchenpos.menus.tobe.domain.menu.*;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroup;
import kitchenpos.menus.tobe.dto.menu.ChangeMenuPriceRequest;
import kitchenpos.menus.tobe.dto.menu.CreateMenuRequest;
import kitchenpos.menus.tobe.dto.menu.MenuProductRequest;
import kitchenpos.products.tobe.domain.DisplayedName;
import kitchenpos.products.tobe.domain.Price;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final ProductRepository productRepository;

    public MenuService(final MenuRepository menuRepository, final ProductRepository productRepository, final MenuGroupRepository menuGroupRepository) {
        this.menuRepository = menuRepository;
        this.productRepository = productRepository;
        this.menuGroupRepository = menuGroupRepository;
    }

    @Transactional
    public Menu hide(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId).orElseThrow(NoSuchElementException::new);
        menu.hide();
        return menu;
    }

    @Transactional
    public Menu display(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId).orElseThrow(NoSuchElementException::new);
        menu.display();
        return menu;
    }

    @Transactional
    public Menu changePrice(final UUID menuId, final ChangeMenuPriceRequest request) {
        final Menu menu = menuRepository.findById(menuId).orElseThrow(NoSuchElementException::new);
        menu.changePrice(new MenuPrice(request.price()));
        return menu;
    }

    @Transactional
    public Menu create(final CreateMenuRequest request) {
        MenuGroup menuGroup = menuGroupRepository.findById(request.getMenuGroupId()).orElseThrow(() -> new NoSuchElementException("해당하는 메뉴 그룹이 업습니다."));
        validateMenuProductSize(request);
        validateExistProduct(request.getMenuProducts());
        return new Menu(new MenuName(request.getMenuName(), false), new MenuPrice(request.getPrice()), createMenuProducts(request), menuGroup);
    }

    private MenuProducts createMenuProducts(CreateMenuRequest request) {
        MenuProducts menuProducts = new MenuProducts();
        request.getMenuProducts().stream().map(MenuService::createMenuProduct).forEach(menuProducts::add);
        return menuProducts;
    }

    private static void validateMenuProductSize(CreateMenuRequest request) {
        if (request.getMenuProducts().size() < 1) {
            throw new IllegalArgumentException("메뉴 상품을 등록해주세요.");
        }
    }

    private void validateExistProduct(List<MenuProductRequest> menuProducts) {
        for (MenuProductRequest menuProductRequest : menuProducts) {
            productRepository.findById(menuProductRequest.getProductRequest().getProductId()).orElseThrow(() -> new NoSuchElementException("해당하는 상품이 업습니다."));
        }
    }

    private static MenuProduct createMenuProduct(MenuProductRequest menuProductRequest) {
        return new MenuProduct(createProduct(menuProductRequest), new Quantity(menuProductRequest.getQuantity()));
    }

    private static Product createProduct(MenuProductRequest menuProductRequest) {
        return new Product(UUID.randomUUID(), new DisplayedName(menuProductRequest.getProductRequest().getProductName(), false),
                new Price(menuProductRequest.getProductRequest().getProductPrice()));
    }

    @Transactional(readOnly = true)
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }
}
