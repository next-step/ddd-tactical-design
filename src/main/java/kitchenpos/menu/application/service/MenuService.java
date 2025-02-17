package kitchenpos.menu.application.service;

import kitchenpos.menu.application.port.out.LoadMenuGroupPort;
import kitchenpos.menu.application.port.out.LoadMenuPort;
import kitchenpos.menu.application.port.out.SaveMenuPort;
import kitchenpos.menu.application.service.model.ChangeMenuPriceRequest;
import kitchenpos.menu.application.service.model.CreateMenuRequest;
import kitchenpos.menu.domain.exception.MenuNameValidationException;
import kitchenpos.menu.domain.model.*;
import kitchenpos.product.application.port.out.LoadProductPort;
import kitchenpos.product.domain.model.Product;
import kitchenpos.shared.port.out.PurgomalumClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MenuService {
    private final LoadProductPort loadProductPort;
    private final LoadMenuPort loadMenuPort;
    private final LoadMenuGroupPort loadMenuGroupPort;
    private final SaveMenuPort saveMenuPort;
    private final PurgomalumClient purgomalumClient;

    public MenuService(
            final LoadProductPort loadProductPort,
            final LoadMenuPort loadMenuPort,
            final LoadMenuGroupPort loadMenuGroupPort,
            final SaveMenuPort saveMenuPort,
            final PurgomalumClient purgomalumClient
    ) {
        this.loadProductPort = loadProductPort;
        this.loadMenuPort = loadMenuPort;
        this.loadMenuGroupPort = loadMenuGroupPort;
        this.saveMenuPort = saveMenuPort;
        this.purgomalumClient = purgomalumClient;
    }

    @Transactional
    public Menu create(final CreateMenuRequest request) {
        final MenuGroup menuGroup = loadMenuGroupPort.findById(request.getMenuGroupId())
            .orElseThrow(NoSuchElementException::new);

        // Request 내에서 검증하기
        final List<MenuProduct> menuProductRequests = request.getMenuProducts();
        if (Objects.isNull(menuProductRequests) || menuProductRequests.isEmpty()) {
            throw new IllegalArgumentException();
        }

        // 메소드 추출
        final List<Product> products = loadProductPort.findAllByIdIn(
            menuProductRequests.stream()
                .map(MenuProduct::getProductId)
                .toList()
        );

        if (products.size() != menuProductRequests.size()) {
            throw new IllegalArgumentException();
        }

        final Map<UUID, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));

        final List<MenuProduct> menuProducts = new ArrayList<>();
        for (final MenuProduct menuProductRequest : menuProductRequests) {
            // Request 내에서 검증하기
            final long quantity = menuProductRequest.getQuantity();
            if (quantity < 0) {
                throw new IllegalArgumentException();
            }
            final Product product = productMap.get(menuProductRequest.getProductId());
            final MenuProduct menuProduct = new MenuProduct();
            menuProduct.setProduct(product);
            menuProduct.setQuantity(quantity);
            menuProducts.add(menuProduct);
        }

        final Menu menu = Menu.create(
                request.getName(),
                request.getPrice(),
                request.isDisplayed(),
                menuGroup,
                menuProducts,
                getProfanityFilteringMenuNameValidator());

        return saveMenuPort.save(menu);
    }

    @Transactional
    public Menu changePrice(final UUID menuId, final ChangeMenuPriceRequest request) {
        final BigDecimal price = request.getPrice();
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
        final Menu menu = loadMenuPort.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
        BigDecimal sum = BigDecimal.ZERO;
        for (final MenuProduct menuProduct : menu.getMenuProducts()) {
            sum = sum.add(
                menuProduct.getProduct()
                    .getPrice()
                    .multiply(BigDecimal.valueOf(menuProduct.getQuantity()))
            );
        }
        if (price.compareTo(sum) > 0) {
            throw new IllegalArgumentException();
        }
        menu.setPrice(MenuPrice.of(price, p -> {}));
        return menu;
    }

    @Transactional
    public Menu display(final UUID menuId) {
        final Menu menu = loadMenuPort.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
        BigDecimal sum = BigDecimal.ZERO;
        for (final MenuProduct menuProduct : menu.getMenuProducts()) {
            sum = sum.add(
                menuProduct.getProduct()
                    .getPrice()
                    .multiply(BigDecimal.valueOf(menuProduct.getQuantity()))
            );
        }
        if (menu.getPrice().compareTo(sum) > 0) {
            throw new IllegalStateException();
        }
        menu.setDisplayed(true);
        return menu;
    }

    @Transactional
    public Menu hide(final UUID menuId) {
        final Menu menu = loadMenuPort.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
        menu.setDisplayed(false);
        return menu;
    }

    @Transactional(readOnly = true)
    public List<Menu> findAll() {
        return loadMenuPort.findAll();
    }

    private ProfanityFilteringMenuNameValidator getProfanityFilteringMenuNameValidator() {
        return n -> {
            if (purgomalumClient.containsProfanity(n)) {
                throw new MenuNameValidationException("메뉴 이름에 비속어가 포함되어 있습니다. name=" + n);
            }
        };
    }
}
