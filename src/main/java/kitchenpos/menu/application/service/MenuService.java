package kitchenpos.menu.application.service;

import kitchenpos.menu.application.port.out.LoadMenuGroupPort;
import kitchenpos.menu.application.port.out.LoadMenuPort;
import kitchenpos.menu.application.port.out.SaveMenuPort;
import kitchenpos.menu.application.service.model.ChangeMenuPriceRequest;
import kitchenpos.menu.application.service.model.CreateMenuProductRequest;
import kitchenpos.menu.application.service.model.CreateMenuRequest;
import kitchenpos.menu.domain.model.Menu;
import kitchenpos.menu.domain.model.MenuGroup;
import kitchenpos.menu.domain.model.MenuProduct;
import kitchenpos.product.application.port.out.LoadProductPort;
import kitchenpos.product.domain.model.Product;
import kitchenpos.shared.domain.Profanities;
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
    private final Profanities profanities;

    public MenuService(
            final LoadProductPort loadProductPort,
            final LoadMenuPort loadMenuPort,
            final LoadMenuGroupPort loadMenuGroupPort,
            final SaveMenuPort saveMenuPort,
            final Profanities profanities
    ) {
        this.loadProductPort = loadProductPort;
        this.loadMenuPort = loadMenuPort;
        this.loadMenuGroupPort = loadMenuGroupPort;
        this.saveMenuPort = saveMenuPort;
        this.profanities = profanities;
    }

    @Transactional
    public Menu create(final CreateMenuRequest request) {
        final MenuGroup menuGroup = loadMenuGroupPort.findById(request.getMenuGroupId())
            .orElseThrow(NoSuchElementException::new);

        // Request 내에서 검증하기
        final List<CreateMenuProductRequest> menuProductRequests = request.getMenuProducts();
        if (Objects.isNull(menuProductRequests) || menuProductRequests.isEmpty()) {
            throw new IllegalArgumentException();
        }

        // 메소드 추출
        final List<Product> products = loadProductPort.findAllByIdIn(
            menuProductRequests.stream()
                .map(CreateMenuProductRequest::getProductId)
                .toList()
        );

        if (products.size() != menuProductRequests.size()) {
            throw new IllegalArgumentException();
        }

        final Map<UUID, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));

        final List<MenuProduct> menuProducts = new ArrayList<>();
        for (final CreateMenuProductRequest menuProductRequest : menuProductRequests) {
            // Request 내에서 검증하기
            final long quantity = menuProductRequest.getQuantity();
            if (quantity < 0) {
                throw new IllegalArgumentException();
            }
            final Product product = productMap.get(menuProductRequest.getProductId());
            final MenuProduct menuProduct = new MenuProduct(null, product.getId(), quantity, product.getPrice());
            menuProducts.add(menuProduct);
        }

        final Menu menu = Menu.create(
                request.getName(),
                request.getPrice(),
                request.isDisplayed(),
                menuGroup,
                menuProducts,
                profanities);

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
        menu.changePrice(price);
        return menu;
    }

    @Transactional
    public Menu display(final UUID menuId) {
        final Menu menu = loadMenuPort.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
        menu.display();
        return menu;
    }

    @Transactional
    public Menu hide(final UUID menuId) {
        final Menu menu = loadMenuPort.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
        menu.hide();
        return menu;
    }

    @Transactional(readOnly = true)
    public List<Menu> findAll() {
        return loadMenuPort.findAll();
    }
}
