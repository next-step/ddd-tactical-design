package kitchenpos.menus.application;

import kitchenpos.menus.dto.MenuCreateRequest;
import kitchenpos.menus.tobe.domain.*;
import kitchenpos.menus.tobe.domain.policy.ProfanityPolicy;
import kitchenpos.products.application.ProductService;
import kitchenpos.products.tobe.domain.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuGroupService menuGroupService;
    private final ProductService productService;
    private final ProfanityPolicy profanityPolicy;

    public MenuService(
            final MenuRepository menuRepository,
            final MenuGroupService menuGroupService,
            final ProductService productService,
            final ProfanityPolicy profanityPolicy
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupService = menuGroupService;
        this.productService = productService;
        this.profanityPolicy = profanityPolicy;
    }

    @Transactional
    public Menu create(final MenuCreateRequest request) {
        final MenuDisplayedName menuDisplayedName = new MenuDisplayedName(request.getName(), profanityPolicy);
        final MenuPrice menuPrice = new MenuPrice(request.getPrice());
        final MenuGroup menuGroup = menuGroupService.findById(request.getMenugroupId());

        // 메뉴 상품 조립
        List<MenuProduct> menuProductValues = request.getMenuProducts()
                .stream()
                .map(menuProductRequest -> {
                    MenuProductQuantity menuProductQuantity = new MenuProductQuantity(menuProductRequest.getQuantity());
                    Product product = productService.findById(menuProductRequest.getProductId());
                    return new MenuProduct(product, menuProductQuantity);
                })
                .collect(Collectors.toUnmodifiableList());

        final Menu menu = new Menu(
                menuDisplayedName,
                menuPrice,
                menuGroup,
                request.isDisplayed(),
                new MenuProducts(menuProductValues)
        );

        return menuRepository.save(menu);
    }

    @Transactional
    public Menu changePrice(final UUID menuId, final BigDecimal request) {

        MenuPrice price = new MenuPrice(request);

        final Menu menu = findById(menuId);
        menu.changePrice(price);

        return menu;
    }

    @Transactional
    public Menu display(final UUID menuId) {
        final Menu menu = findById(menuId);
        menu.display();
        return menu;
    }

    @Transactional
    public Menu hide(final UUID menuId) {
        final Menu menu = findById(menuId);
        menu.hide();
        return menu;
    }

    @Transactional(readOnly = true)
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }

    @Transactional
    public void hideMenuWhenChangeProductPrice(UUID productId) {
        final List<Menu> menus = menuRepository.findAllByProductId(productId);
        for (final Menu menu : menus) {
            BigDecimal sum = BigDecimal.ZERO;
            for (final MenuProduct menuProduct : menu.getMenuProducts()) {
                sum = sum.add(
                        menuProduct.getProduct()
                                .getPriceValue()
                                .multiply(BigDecimal.valueOf(menuProduct.getQuantity()))
                );
            }
            if (menu.getPrice().compareTo(sum) > 0) {
                menu.setDisplayed(false);
            }
        }

    }

    @Transactional(readOnly = true)
    public Menu findById(UUID menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);
    }
}
