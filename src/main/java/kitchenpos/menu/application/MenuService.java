package kitchenpos.menu.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import kitchenpos.menu.tobe.application.dto.ChangeMenuPriceRequest;
import kitchenpos.menu.tobe.application.dto.CreateMenuProductRequest;
import kitchenpos.menu.tobe.application.dto.CreateMenuRequest;
import kitchenpos.menu.tobe.application.dto.CreateMenuResponse;
import kitchenpos.menu.tobe.domain.Menu;
import kitchenpos.menu.tobe.domain.MenuName;
import kitchenpos.menu.tobe.domain.MenuPrice;
import kitchenpos.menu.tobe.domain.MenuProduct;
import kitchenpos.menu.tobe.domain.MenuProducts;
import kitchenpos.menu.tobe.domain.MenuRepository;
import kitchenpos.menu.tobe.domain.service.MenuNamePolicy;
import kitchenpos.menugroup.domain.MenuGroup;
import kitchenpos.menugroup.domain.MenuGroupRepository;
import kitchenpos.product.tobe.domain.Product;
import kitchenpos.product.tobe.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final ProductRepository productRepository;
    private final MenuNamePolicy menuNamePolicy;

    public MenuService(
        final MenuRepository menuRepository,
        final MenuGroupRepository menuGroupRepository,
        final ProductRepository productRepository,
        final MenuNamePolicy menuNamePolicy) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.productRepository = productRepository;
        this.menuNamePolicy = menuNamePolicy;
    }

    @Transactional
    public CreateMenuResponse create(final CreateMenuRequest request) {
        final MenuGroup menuGroup = menuGroupRepository.findById(request.getMenuGroupId())
            .orElseThrow(NoSuchElementException::new);

        final Menu menu = new Menu(
            UUID.randomUUID(),
            MenuName.of(request.getName(), menuNamePolicy),
            new MenuPrice(request.getPrice()),
            menuGroup.getId(),
            request.isDisplayed(),
            createMenuProducts(request.getMenuProducts())
        );

        return CreateMenuResponse.of(menuRepository.save(menu));
    }

    private MenuProducts createMenuProducts(List<CreateMenuProductRequest> menuProductRequests) {
        if (Objects.isNull(menuProductRequests)) {
            throw new IllegalArgumentException();
        }

        var productIds = menuProductRequests.stream()
            .map(CreateMenuProductRequest::getProductId)
            .collect(Collectors.toList());
        final var productByIds = productRepository.findAllByIdIn(productIds)
            .stream()
            .collect(Collectors.toMap(Product::getId, Function.identity()));

        if (productByIds.size() != menuProductRequests.size()) {
            throw new IllegalArgumentException();
        }

        var menuProducts = menuProductRequests.stream()
            .map(it -> MenuProduct.of(productByIds.get(it.getProductId()), it.getQuantity()))
            .collect(Collectors.toList());

        return new MenuProducts(menuProducts);
    }

    @Transactional
    public Menu changePrice(final UUID menuId, final ChangeMenuPriceRequest request) {
        final Menu menu = menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
        menu.changePrice(MenuPrice.of(request.getPrice()));
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
