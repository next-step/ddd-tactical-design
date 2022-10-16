package kitchenpos.menus.tobe.application;


import kitchenpos.menus.tobe.domain.menu.*;
import kitchenpos.menus.tobe.dto.menu.ChangePriceRequest;
import kitchenpos.menus.tobe.dto.menu.MenuCreateRequest;
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
    public Menu changePrice(final UUID menuId, final ChangePriceRequest request) {
        final Menu menu = menuRepository.findById(menuId).orElseThrow(NoSuchElementException::new);
        menu.changePrice(new MenuPrice(request.price()));
        return menu;
    }

    @Transactional
    public Menu create(final MenuCreateRequest request) {
        MenuProducts menuProducts = new MenuProducts();
        menuGroupRepository.findById(request.getMenuGroupId()).orElseThrow(() -> new NoSuchElementException("해당하는 메뉴 그룹이 업습니다."));
        for (MenuProductRequest menuProductRequest : request.getMenuProducts()) {
            productRepository.findById(menuProductRequest.getProductRequest().getProductId()).orElseThrow(() -> new NoSuchElementException("해당하는 상품이 업습니다."));
            DisplayedName displayedName = new DisplayedName(menuProductRequest.getProductRequest().getProductName(), false);
            Price productPrice = new Price(menuProductRequest.getProductRequest().getProductPrice());
            menuProducts.add(new MenuProduct(new Product(UUID.randomUUID(), displayedName, productPrice), new Quantity(menuProductRequest.getQuantity())));
        }
        return new Menu(new MenuName(request.getMenuName(), false), new MenuPrice(request.getPrice()), menuProducts, null);
    }

    @Transactional(readOnly = true)
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }
}
