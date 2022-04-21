package kitchenpos.menus.application;

import kitchenpos.common.domain.Price;
import kitchenpos.menus.domain.MenuGroupRepository;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.MenuProducts;
import kitchenpos.menus.ui.dto.MenuProductRequest;
import kitchenpos.menus.ui.dto.MenuProductRequests;
import kitchenpos.menus.ui.dto.MenuRequest;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final ProductRepository productRepository;
    private final PurgomalumClient purgomalumClient;

    public MenuService(
            final MenuRepository menuRepository,
            final MenuGroupRepository menuGroupRepository,
            final ProductRepository productRepository,
            final PurgomalumClient purgomalumClient
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.productRepository = productRepository;
        this.purgomalumClient = purgomalumClient;
    }

    @Transactional
    public Menu create(final MenuRequest request) {
        Price price = new Price(request.getPrice());

        final MenuGroup menuGroup = menuGroupRepository.findById(request.getMenuGroupId())
                .orElseThrow(NoSuchElementException::new);
        MenuProductRequests menuProductRequests = new MenuProductRequests(request.getMenuProducts());

        final List<Product> products = productRepository.findAllByIdIn(
                menuProductRequests.getMenuProductRequests()
                        .stream()
                        .map(MenuProductRequest::getProductId)
                        .collect(Collectors.toList())
        );
        MenuProducts menuProducts = new MenuProducts(menuProductRequests.getMenuProductRequests(), products, price);

        final Menu menu = new Menu(purgomalumClient, UUID.randomUUID(), request.getName(), price, menuGroup.getId(), request.isDisplayed(), menuProducts);

        return menuRepository.save(menu);
    }

    @Transactional
    public Menu changePrice(final UUID menuId, final MenuRequest request) {
        Price price = new Price(request.getPrice());

        final Menu menu = menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);

        List<Product> findProducts = productRepository.findAllByIdIn(menu.getProductIds());

        menu.changePrice(price, findProducts);
        return menu;
    }

    @Transactional
    public Menu display(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);

        List<Product> findProducts = productRepository.findAllByIdIn(menu.getProductIds());

        menu.show(findProducts);
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
