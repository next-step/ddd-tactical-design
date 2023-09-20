package kitchenpos.menus.application;

import kitchenpos.menus.domain.*;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.tobe.dto.MenuCreateRequestDto;
import kitchenpos.menus.tobe.dto.MenuPriceChangeRequestDto;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.infra.PurgomalumClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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
    public Menu create(final MenuCreateRequestDto request) {
        final MenuGroup menuGroup = menuGroupRepository.findById(request.getMenuGroupId())
                .orElseThrow(NoSuchElementException::new);

        if (Objects.isNull(request.getMenuProducts()) || request.getMenuProducts().isEmpty()) {
            throw new IllegalArgumentException();
        }

        final List<Product> products = productRepository.findAllByIdIn(
            request.getMenuProducts().stream()
                .map(MenuProduct::getProductId)
                .collect(Collectors.toList())
        );

        if (products.size() != request.getMenuProducts().size()) {
            throw new IllegalArgumentException();
        }

        List<MenuProduct> menuProducts = request.getMenuProducts().stream()
                .map(menuProduct -> {
                    Product product = productRepository.findById(menuProduct.getProductId())
                            .orElseThrow(NoSuchElementException::new);
                    return new MenuProduct(product, menuProduct.getQuantity(), menuProduct.getProductId());
                })
                .collect(Collectors.toList());

        final Menu menu = new Menu(request.getName(), request.getPrice(), menuGroup, request.isDisplayed(),
                menuProducts, request.getMenuGroupId(), purgomalumClient);

        return menuRepository.save(menu);
    }

    @Transactional
    public Menu changePrice(final UUID menuId, final MenuPriceChangeRequestDto request) {
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
