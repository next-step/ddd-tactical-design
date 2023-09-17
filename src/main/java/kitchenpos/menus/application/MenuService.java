package kitchenpos.menus.application;

import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.menus.tobe.ui.dto.request.MenuCreateRequest;
import kitchenpos.menus.tobe.ui.dto.response.MenuResponse;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.domain.PurgomalumClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final ProductRepository productRepository;
    private final PurgomalumClient purgomalumClient;
    private final MenuValidator validator;

    public MenuService(
            final MenuRepository menuRepository,
            final ProductRepository productRepository,
            final PurgomalumClient purgomalumClient,
            final MenuValidator validator
    ) {
        this.menuRepository = menuRepository;
        this.productRepository = productRepository;
        this.purgomalumClient = purgomalumClient;
        this.validator = validator;
    }

    @Transactional
    public MenuResponse create(final MenuCreateRequest request) {
        validator.validateMenuGroup(request.getMenuGroupId());
        validator.validateMenuProduct(request.getMenuProductCreateRequests());
        validator.validateMenuPrice(request.getMenuProductCreateRequests(), request.getPrice());
        final Menu menu = request.toMenu(purgomalumClient);
        Menu savedMenu = menuRepository.save(menu);
        return MenuResponse.from(savedMenu);
    }

    @Transactional
    public Menu changePrice(final UUID menuId, final Menu request) {
        final BigDecimal price = request.getPrice();
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
        final Menu menu = menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
        BigDecimal sum = BigDecimal.ZERO;
        for (final MenuProduct menuProduct : menu.getMenuProducts()) {
            final Product product = productRepository.findById(menuProduct.getProductId())
                    .orElseThrow(NoSuchElementException::new);
            sum = sum.add(product.multiplyPrice(menuProduct.getQuantity()).getValue());
        }
        if (price.compareTo(sum) > 0) {
            throw new IllegalArgumentException();
        }
        menu.changePrice(price);
        return menu;
    }

    @Transactional
    public Menu display(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
        BigDecimal sum = BigDecimal.ZERO;
        for (final MenuProduct menuProduct : menu.getMenuProducts()) {
            final Product product = productRepository.findById(menuProduct.getProductId())
                    .orElseThrow(NoSuchElementException::new);
            sum = sum.add(product.multiplyPrice(menuProduct.getQuantity()).getValue());
        }
        if (menu.getPrice().compareTo(sum) > 0) {
            throw new IllegalStateException();
        }
        menu.show();
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
