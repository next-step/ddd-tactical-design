package kitchenpos.menus.tobe.application;

import kitchenpos.menus.tobe.application.dto.TobeMenuCreateRequest;
import kitchenpos.menus.tobe.application.dto.TobeMenuCreateResponse;
import kitchenpos.menus.tobe.application.dto.TobeMenuProductRequest;
import kitchenpos.menus.tobe.domain.menu.MenuName;
import kitchenpos.menus.tobe.domain.menu.MenuPrice;
import kitchenpos.menus.tobe.domain.menu.PurgomalumChecker;
import kitchenpos.menus.tobe.domain.menu.TobeMenu;
import kitchenpos.menus.tobe.domain.menu.TobeMenuProduct;
import kitchenpos.menus.tobe.domain.menu.TobeMenuProductQuantity;
import kitchenpos.menus.tobe.domain.menu.TobeMenuProducts;
import kitchenpos.menus.tobe.domain.menu.TobeMenuRepository;
import kitchenpos.menus.tobe.domain.menu.TobeProductClient;
import kitchenpos.menus.tobe.domain.menugroup.TobeMenuGroup;
import kitchenpos.menus.tobe.domain.menugroup.TobeMenuGroupRepository;
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
    private final PurgomalumChecker purgomalumChecker;
    private final TobeProductClient tobeProductClient;

    public TobeMenuService(
            final TobeMenuRepository menuRepository,
            final TobeMenuGroupRepository menuGroupRepository,
            final PurgomalumChecker purgomalumChecker,
            final TobeProductClient tobeProductClient) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.purgomalumChecker = purgomalumChecker;
        this.tobeProductClient = tobeProductClient;
    }

    @Transactional
    public TobeMenuCreateResponse create(final TobeMenuCreateRequest request) {
        final TobeMenuProducts tobeMenuProducts = getTobeMenuProducts(request.getTobeMenuProducts());
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
            BigDecimal productPrice = tobeProductClient.getProductPrice(it.getProductId());
            return new TobeMenuProduct(it.getProductId(), new MenuPrice(productPrice), quantity);
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
