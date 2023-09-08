package kitchenpos.menus.tobe.application;

import kitchenpos.menus.tobe.domain.*;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.application.ProductValidator;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final PurgomalumClient purgomalumClient;
    private final ProductValidator productValidator;

    public MenuService(
        final MenuRepository menuRepository,
        final MenuGroupRepository menuGroupRepository,
        final ProductRepository productRepository,
        final PurgomalumClient purgomalumClient,
        ProductValidator productValidator) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.purgomalumClient = purgomalumClient;
        this.productValidator = productValidator;
    }

    @Transactional
    public Menu create(final Menu request) {
        final MenuGroup menuGroup = menuGroupRepository.findById(request.getMenuGroupId())
            .orElseThrow(NoSuchElementException::new);
        final MenuProducts menuProductRequests = request.getMenuProducts();

        productValidator.isExistProductIn(menuProductRequests);
        menuProductRequests.getTotalPrice();
        final List<MenuProduct> menuProducts = new ArrayList<>();


//        for (final MenuProduct menuProductRequest : menuProductRequests) {
//            final long quantity = menuProductRequest.getQuantity();
//            final Product product = productRepository.findById(menuProductRequest.getProductId())
//                .orElseThrow(NoSuchElementException::new);
//            sum = sum.add(
//                product.getPrice()
//                    .multiply(BigDecimal.valueOf(quantity))
//            );
//            final MenuProduct menuProduct = new MenuProduct();
//            menuProduct.setProduct(product);
//            menuProduct.setQuantity(quantity);
//            menuProducts.add(menuProduct);
//        }

        if (purgomalumClient.containsProfanity(request.getName())) {
            throw new IllegalArgumentException("메뉴의 이름은 비속어를 포함할 수 없습니다");
        }

        final Menu menu = new Menu();
        menu.setId(UUID.randomUUID());
        menu.setName(name);
        menu.setPrice(price);
        menu.setMenuGroup(menuGroup);
        menu.setDisplayed(request.isDisplayed());
        menu.setMenuProducts(menuProducts);
        return menuRepository.save(menu);
    }

    @Transactional
    public Menu changePrice(final UUID menuId, final Menu request) {
        final Menu menu = menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);
        menu.changePrice(BigDecimal.TEN);
        return menu;
    }

    @Transactional
    public Menu display(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId
        ).orElseThrow(NoSuchElementException::new);
        menu.setDisplayable();
        return menu;
    }

    @Transactional
    public Menu hide(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
        menu.setHide();
        return menu;
    }

    @Transactional(readOnly = true)
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }
}
