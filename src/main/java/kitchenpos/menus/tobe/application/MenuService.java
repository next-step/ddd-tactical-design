package kitchenpos.menus.tobe.application;

import kitchenpos.menus.tobe.application.dto.MenuPriceRequest;
import kitchenpos.menus.tobe.application.dto.MenuRequest;
import kitchenpos.menus.tobe.domain.*;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.products.tobe.infra.PurgomalumClient;
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
        request.validate(purgomalumClient::containsProfanity);

        final MenuGroup menuGroup = menuGroupRepository.findById(request.getMenuGroupId())
            .orElseThrow(NoSuchElementException::new);

        final List<Product> products = productRepository.findAllByIdIn(
            request.getMenuProducts().stream()
                .map(MenuProduct::getProductId)
                .collect(Collectors.toList())
        );

        final Menu menu = Menu.create(
            UUID.randomUUID(),
            request.getName(),
            request.getPrice(),
            menuGroup,
            request.isDisplayed(),
            Menu.createMenuProducts(products, request.getMenuProducts()),
            purgomalumClient::containsProfanity
        );

        if (menu.exceedsSum(request.getPrice())) {
            throw new IllegalArgumentException();
        }

        return menuRepository.save(menu);
    }

    @Transactional
    public Menu changePrice(final UUID menuId, final MenuPriceRequest request) {
        request.validatePrice();
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
