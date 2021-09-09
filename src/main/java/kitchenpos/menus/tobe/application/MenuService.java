package kitchenpos.menus.tobe.application;

import kitchenpos.common.infra.Profanities;
import kitchenpos.menus.tobe.domain.*;
import kitchenpos.menus.tobe.dto.ChangeMenuPriceRequest;
import kitchenpos.menus.tobe.dto.CreateMenuRequest;
import kitchenpos.menus.tobe.dto.MenuProductRequest;
import kitchenpos.menus.tobe.dto.MenuResponse;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service("TobeMenuService")
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final ProductRepository productRepository;
    private final Profanities profanities;

    public MenuService(
            final MenuRepository menuRepository,
            final MenuGroupRepository menuGroupRepository,
            final ProductRepository productRepository,
            final Profanities profanities
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.productRepository = productRepository;
        this.profanities = profanities;
    }

    @Transactional
    public MenuResponse create(final CreateMenuRequest request) {
        final BigDecimal price = request.getPrice();
        final MenuGroup menuGroup = menuGroupRepository.findById(request.getMenuGroupId())
                .orElseThrow(NoSuchElementException::new);
        final List<MenuProductRequest> menuProductRequests = request.getMenuProducts();
        if (Objects.isNull(menuProductRequests) || menuProductRequests.isEmpty()) {
            throw new IllegalArgumentException();
        }
        final List<Product> products = productRepository.findAllByIdIn(
                menuProductRequests.stream()
                        .map(MenuProductRequest::getProductId)
                        .collect(Collectors.toList())
        );
        if (products.size() != menuProductRequests.size()) {
            throw new IllegalArgumentException();
        }
        final List<MenuProduct> menuProducts = new ArrayList<>();
        BigDecimal sum = BigDecimal.ZERO;
        for (final MenuProductRequest menuProductRequest : menuProductRequests) {
            final long quantity = menuProductRequest.getQuantity();
            if (quantity < 0) {
                throw new IllegalArgumentException();
            }
            final Product product = productRepository.findById(menuProductRequest.getProductId())
                    .orElseThrow(NoSuchElementException::new);
            sum = sum.add(
                    product.getPrice()
                            .multiply(BigDecimal.valueOf(quantity))
            );
            final MenuProduct menuProduct = new MenuProduct();
            menuProduct.setProduct(product);
            menuProduct.setQuantity(quantity);
            menuProducts.add(menuProduct);
        }
        if (price.compareTo(sum) > 0) {
            throw new IllegalArgumentException();
        }
        final String name = request.getName();
        final Menu menu = new Menu();
        menu.setId(UUID.randomUUID());
        menu.setMenuName(new MenuName(name, profanities));
        menu.setMenuPrice(new MenuPrice(price));
        menu.setMenuGroup(menuGroup);
        menu.setDisplayed(request.isDisplayed());
        menu.setMenuProducts(menuProducts);
        return MenuResponse.from(menuRepository.save(menu));
    }

    @Transactional
    public MenuResponse changePrice(final UUID menuId, final ChangeMenuPriceRequest request) {
        final MenuPrice menuPrice = new MenuPrice(request.getPrice());
        final Menu menu = menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);
        menu.changePrice(menuPrice);
        return MenuResponse.from(menu);
    }

    @Transactional
    public void updateStatus(final UUID productId) {
        final List<Menu> menus = menuRepository.findAllByProductId(productId);
        menus.forEach(Menu::updateStatus);
    }

    @Transactional
    public MenuResponse display(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);
        menu.display();
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
        return menuRepository.findAll().stream()
                .map(MenuResponse::from)
                .collect(Collectors.toList());
    }
}
