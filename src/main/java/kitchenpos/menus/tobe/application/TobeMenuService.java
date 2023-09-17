package kitchenpos.menus.tobe.application;

import kitchenpos.menus.tobe.application.dto.TobeMenuCreateRequest;
import kitchenpos.menus.tobe.application.dto.TobeMenuCreateResponse;
import kitchenpos.menus.tobe.application.dto.TobeMenuProductRequest;
import kitchenpos.menus.tobe.domain.menu.MenuName;
import kitchenpos.menus.tobe.domain.menu.MenuPrice;
import kitchenpos.menus.tobe.domain.menu.PurgomalumChecker;
import kitchenpos.menus.tobe.domain.menu.TobeMenu;
import kitchenpos.menus.tobe.domain.menu.TobeMenuRepository;
import kitchenpos.menus.tobe.domain.menugroup.TobeMenuGroup;
import kitchenpos.menus.tobe.domain.menugroup.TobeMenuGroupRepository;
import kitchenpos.menus.tobe.domain.menuproduct.TobeMenuProductQuantity;
import kitchenpos.menus.tobe.domain.menuproduct.TobeMenuProduct;
import kitchenpos.products.tobe.domain.TobeProduct;
import kitchenpos.products.tobe.domain.TobeProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TobeMenuService {
    private final TobeMenuRepository menuRepository;
    private final TobeMenuGroupRepository menuGroupRepository;
    private final TobeProductRepository productRepository;
    private final PurgomalumChecker purgomalumChecker;

    public TobeMenuService(
            final TobeMenuRepository menuRepository,
            final TobeMenuGroupRepository menuGroupRepository,
            final TobeProductRepository productRepository,
            final PurgomalumChecker purgomalumChecker
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.productRepository = productRepository;
        this.purgomalumChecker = purgomalumChecker;
    }

    @Transactional
    public TobeMenuCreateResponse create(final TobeMenuCreateRequest request) {
        final TobeMenuGroup menuGroup = menuGroupRepository.findById(request.getMenuGroupId())
                                                           .orElseThrow(NoSuchElementException::new);
        List<TobeMenuProductRequest> menuProductRequests = request.getTobeMenuProducts();
        if (Objects.isNull(menuProductRequests) || menuProductRequests.isEmpty()) {
            throw new IllegalArgumentException();
        }
        final List<TobeProduct> products = productRepository.findAllByIdIn(
                menuProductRequests.stream()
                                   .map(TobeMenuProductRequest::getProductId)
                                   .collect(Collectors.toList())
        );
        if (products.size() != menuProductRequests.size()) {
            throw new IllegalArgumentException();
        }
        final List<TobeMenuProduct> menuProducts = new ArrayList<>();
        BigDecimal sum = BigDecimal.ZERO;
        for (final TobeMenuProductRequest menuProductRequest : menuProductRequests) {
            TobeMenuProductQuantity quantity = new TobeMenuProductQuantity(menuProductRequest.getQuantity());

            final TobeProduct product = productRepository.findById(menuProductRequest.getProductId())
                                                     .orElseThrow(NoSuchElementException::new);
            sum = sum.add(
                    product.getPrice().getPrice()
                           .multiply(BigDecimal.valueOf(quantity.getQuantity()))
            );
            final TobeMenuProduct menuProduct = new TobeMenuProduct(product, quantity);
            menuProducts.add(menuProduct);
        }
        MenuPrice menuPrice = new MenuPrice(request.getPrice());
        menuPrice.checkSum(sum);

        final TobeMenu menu = new TobeMenu(UUID.randomUUID(), new MenuName(request.getName(), purgomalumChecker),
                                           new MenuPrice(request.getPrice()), menuGroup, request.isDisplayed(), menuProducts);
        TobeMenu savedTobeMenu = menuRepository.save(menu);

        return TobeMenuCreateResponse.of(savedTobeMenu);
    }

    private Map<UUID, TobeProduct> getProductMap(final List<TobeMenuProductRequest> menuProductRequests) {
        final List<TobeProduct> products = productRepository.findAllByIdIn(
                menuProductRequests.stream()
                                   .map(TobeMenuProductRequest::getProductId)
                                   .collect(Collectors.toList())
        );
        Map<UUID, TobeProduct> productMap = products.stream().collect(Collectors.toMap(TobeProduct::getId, x -> x));
        return productMap;
    }

    @Transactional
    public TobeMenu changePrice(final UUID menuId, BigDecimal price) {
        final TobeMenu menu = menuRepository.findById(menuId)
                                            .orElseThrow(NoSuchElementException::new);
        BigDecimal sum = BigDecimal.ZERO;
        for (final TobeMenuProduct menuProduct : menu.getTobeMenuProducts()) {
            sum = sum.add(
                    menuProduct.getProduct()
                               .getPrice().getPrice()
                               .multiply(BigDecimal.valueOf(menuProduct.getQuantity().getQuantity()))
            );
        }
        MenuPrice menuPrice = new MenuPrice(price);
        menuPrice.checkSum(sum);
        menu.updatePrice(menuPrice);
        return menu;
    }

    @Transactional
    public TobeMenu display(final UUID menuId) {
        final TobeMenu menu = menuRepository.findById(menuId)
                                        .orElseThrow(NoSuchElementException::new);
        BigDecimal sum = BigDecimal.ZERO;
        for (final TobeMenuProduct menuProduct : menu.getTobeMenuProducts()) {
            sum = sum.add(
                    menuProduct.getProduct()
                               .getPrice().getPrice()
                               .multiply(BigDecimal.valueOf(menuProduct.getQuantity().getQuantity()))
            );
        }
        if (menu.getPrice().getPrice().compareTo(sum) > 0) {
            throw new IllegalStateException();
        }
        menu.display();
        return menu;
    }

    @Transactional
    public TobeMenu hide(final UUID menuId) {
        final TobeMenu menu = menuRepository.findById(menuId)
                                        .orElseThrow(NoSuchElementException::new);
        menu.hide();
        return menu;
    }

    @Transactional(readOnly = true)
    public List<TobeMenu> findAll() {
        return menuRepository.findAll();
    }
}
