package kitchenpos.menus.tobe.application;

import static kitchenpos.menus.tobe.domain.MenuName.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import kitchenpos.common.tobe.domain.PurgomalumClient;
import kitchenpos.menus.tobe.application.dto.MenuCreationRequest;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.MenuGroupRepository;
import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.menus.tobe.domain.MenuRepository;
import kitchenpos.products.tobe.application.ProductService;
import kitchenpos.products.tobe.domain.Product;

@Service
public class DefaultMenuService implements MenuService {
    private static final String PRODUCT_NOT_FOUND = "상품을 찾을 수 없습니다";
    private static final String MENU_GROUP_NOT_FOUND_ERROR = "메뉴 그룹을 찾을 수 없습니다.";
    public static final String NAME_WITH_PROFANITY_ERROR = "메뉴 이름에 비속어가 포함될 수 없습니다.";

    private final MenuRepository menuRepository;

    private final MenuGroupRepository menuGroupRepository;

    private final ProductService productService;

    private final PurgomalumClient purgomalumClient;

    public DefaultMenuService(
        final MenuRepository menuRepository,
        final MenuGroupRepository menuGroupRepository,
        final ProductService productService,
        final PurgomalumClient purgomalumClient
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.productService = productService;
        this.purgomalumClient = purgomalumClient;
    }

    @Transactional
    public Menu create(final MenuCreationRequest request) {
        validateMenuName(request.name());

        final MenuGroup menuGroup = menuGroupRepository.findById(request.menuGroupId())
            .orElseThrow(() -> new NoSuchElementException(MENU_GROUP_NOT_FOUND_ERROR));

        final Map<UUID, Product> products =
            productService.findAllByIdIn(new ArrayList<>(request.menuProductQuantities().keySet()))
                .stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));

        Menu menu = new Menu(
            request.name(),
            request.price(),
            menuGroup,
            buildMenuProducts(request, products),
            request.displayed()
        );

        return menuRepository.save(menu);
    }

    private void validateMenuName(String name) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException(NULL_OR_EMPTY_NAME_ERROR);
        }

        if (purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException(NAME_WITH_PROFANITY_ERROR);
        }
    }

    private List<MenuProduct> buildMenuProducts(MenuCreationRequest request, Map<UUID, Product> products) {
        return request.menuProductQuantities()
            .entrySet()
            .stream()
            .map(entry -> buildMenuProduct(entry, products))
            .toList();
    }

    private MenuProduct buildMenuProduct(Map.Entry<UUID, Long> productQuantityEntry, Map<UUID, Product> products) {
        UUID productId = productQuantityEntry.getKey();
        Product product = Optional.ofNullable(products.get(productId))
            .orElseThrow(() -> new NoSuchElementException(String.format("%s: %s", PRODUCT_NOT_FOUND, productId)));
        return new MenuProduct(product, productQuantityEntry.getValue());
    }

    @Transactional
    public Menu changePrice(final UUID menuId, final BigDecimal requestPrice) {
        final Menu menu = menuRepository.findById(menuId).orElseThrow(NoSuchElementException::new);
        menu.changePrice(requestPrice);
        return menu;
    }

    @Transactional
    public Menu display(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId).orElseThrow(NoSuchElementException::new);
        menu.display(true);
        return menu;
    }

    @Transactional
    public Menu hide(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId).orElseThrow(NoSuchElementException::new);
        menu.display(false);
        return menu;
    }

    @Transactional(readOnly = true)
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Menu> findAllByProductId(UUID productId) {
        return menuRepository.findAllByProductId(productId);
    }

    @Override
    @Transactional
    public void hideMenusBasedOnProductPrice(UUID productId) {
        List<Menu> menus = menuRepository.findAllByProductId(productId);

        if (!CollectionUtils.isEmpty(menus)) {
            menus.forEach(Menu::displayBasedOnProductsPrice);
        }
    }
}
