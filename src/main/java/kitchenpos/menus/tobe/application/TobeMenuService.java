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
import kitchenpos.menus.tobe.domain.menu.TobeMenuProduct;
import kitchenpos.menus.tobe.domain.menu.TobeMenuProductQuantity;
import kitchenpos.menus.tobe.domain.menu.TobeMenuProducts;
import kitchenpos.products.tobe.domain.TobeProduct;
import kitchenpos.products.tobe.domain.TobeProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
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
        final TobeMenuProducts tobeMenuProducts = getTobeMenuProducts(request.getTobeMenuProducts());
        final MenuPrice menuPrice = new MenuPrice(request.getPrice());
        menuPrice.checkSum(tobeMenuProducts.sum().getPrice());

        final TobeMenuGroup menuGroup = menuGroupRepository.findById(request.getMenuGroupId())
                                                           .orElseThrow(NoSuchElementException::new);
        final TobeMenu menu = new TobeMenu(UUID.randomUUID(), new MenuName(request.getName(), purgomalumChecker),
                                           new MenuPrice(request.getPrice()), menuGroup.getId(), request.isDisplayed(),
                                           tobeMenuProducts);

        return TobeMenuCreateResponse.of(menuRepository.save(menu), menuGroup);
    }

    private TobeMenuProducts getTobeMenuProducts(final List<TobeMenuProductRequest> menuProductRequests) {
        if (Objects.isNull(menuProductRequests) || menuProductRequests.isEmpty()) {
            throw new IllegalArgumentException();
        }

        List<TobeMenuProduct> tobeMenuProducts = menuProductRequests.stream().map(it -> {
            TobeMenuProductQuantity quantity = new TobeMenuProductQuantity(it.getQuantity());
            final TobeProduct product = productRepository.findById(it.getProductId())
                                                         .orElseThrow(NoSuchElementException::new);
            return new TobeMenuProduct(product.getId(), new MenuPrice(product.getBigDecimalPrice()), quantity);
        }).collect(Collectors.toList());

        return new TobeMenuProducts(tobeMenuProducts);
    }

    @Transactional
    public TobeMenu changePrice(final UUID menuId, BigDecimal price) {
        final TobeMenu menu = menuRepository.findById(menuId)
                                            .orElseThrow(NoSuchElementException::new);
        MenuPrice menuPrice = new MenuPrice(price);
        menu.updatePrice(menuPrice);
        return menu;
    }

    @Transactional
    public TobeMenu display(final UUID menuId) {
        final TobeMenu menu = menuRepository.findById(menuId)
                                            .orElseThrow(NoSuchElementException::new);
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
