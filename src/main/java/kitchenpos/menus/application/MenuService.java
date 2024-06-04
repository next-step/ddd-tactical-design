package kitchenpos.menus.application;

import kitchenpos.menus.application.dto.MenuRequest;
import kitchenpos.menus.application.dto.MenuProductRequest;
import kitchenpos.menus.domain.tobe.menu.Menu;
import kitchenpos.menus.domain.tobe.menu.MenuProduct;
import kitchenpos.menus.domain.tobe.menu.MenuProducts;
import kitchenpos.menus.domain.tobe.menu.MenuRepository;
import kitchenpos.menus.domain.tobe.menugroup.MenuGroup;
import kitchenpos.menus.domain.tobe.menugroup.MenuGroupRepository;
import kitchenpos.products.domain.tobe.Product;
import kitchenpos.products.domain.tobe.ProductRepository;
import kitchenpos.products.domain.tobe.ProfanityValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final ProductRepository productRepository;
    private final ProfanityValidator profanityValidator;

    public MenuService(
        final MenuRepository menuRepository,
        final MenuGroupRepository menuGroupRepository,
        final ProductRepository productRepository,
        final ProfanityValidator profanityValidator
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.productRepository = productRepository;
        this.profanityValidator = profanityValidator;
    }

    @Transactional
    public Menu create(final MenuRequest request) {
        final BigDecimal price = request.getPrice();

        final MenuGroup menuGroup = menuGroupRepository.findById(request.getMenuGroupId())
            .orElseThrow(NoSuchElementException::new);

        final List<MenuProductRequest> menuProductRequests = request.getMenuProducts();

        final List<Product> products = productRepository.findAllByIdIn(
            menuProductRequests.stream()
                .map(MenuProductRequest::getProductId)
                .toList()
        );
        if (products.size() != menuProductRequests.size()) {
            throw new IllegalArgumentException();
        }
        final MenuProducts menuProducts = MenuProducts.of();


        for (final MenuProductRequest menuProductRequest : menuProductRequests) {
            Product product = menuProductRequest.getProduct();
            final MenuProduct menuProduct = MenuProduct.of(menuProductRequest.getProductId(),
                    product.getProductPrice().longValue(),
                    Long.valueOf(menuProductRequest.getQuantity()).intValue());
            menuProducts.add(menuProduct);
        }

        final Menu menu = Menu.of(request.getName(), price.longValue(), menuGroup.getId(), request.isDisplayed(), menuProducts, profanityValidator);

        return menuRepository.save(menu);
    }

    @Transactional
    public Menu changePrice(final UUID menuId, final MenuRequest request) {
        final Menu menu = menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);

        menu.changePrice(request.getPrice());
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
