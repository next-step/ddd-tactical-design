package kitchenpos.menus.application;

import kitchenpos.menus.application.dto.request.MenuChangePriceRequest;
import kitchenpos.menus.application.dto.request.MenuCreateRequest;
import kitchenpos.menus.application.dto.request.MenuProductCreateRequest;
import kitchenpos.menus.application.dto.response.MenuResponse;
import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.domain.PurgomalumClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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
        final Menu menu = request.toMenu(purgomalumClient, createMenuProducts(request.getMenuProductCreateRequests()));
        Menu savedMenu = menuRepository.save(menu);
        return MenuResponse.from(savedMenu);
    }

    public List<MenuProduct> createMenuProducts(List<MenuProductCreateRequest> menuProductRequests) {
        validator.validateMenuProduct(menuProductRequests);
        List<MenuProduct> menuProducts = new ArrayList<>();
        for (final MenuProductCreateRequest menuProductRequest : menuProductRequests) {
            final Product product = productRepository.findById(menuProductRequest.getProductId())
                    .orElseThrow(IllegalArgumentException::new);
            menuProducts.add(menuProductRequest.toMenuProduct(product.getPrice().getValue()));
        }
        return menuProducts;
    }

    @Transactional
    public MenuResponse changePrice(final UUID menuId, final MenuChangePriceRequest request) {
        final Menu menu = menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);
        menu.changePrice(request.getPrice());
        return MenuResponse.from(menu);
    }

    @Transactional
    public MenuResponse display(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);
        menu.show();
        return MenuResponse.from(menu);
    }

    @Transactional
    public MenuResponse hide(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);
        menu.hide();
        return MenuResponse.from(menu);
    }

    @Transactional(readOnly = true)
    public List<MenuResponse> findAll() {
        return MenuResponse.from(menuRepository.findAll());
    }
}
