package kitchenpos.menu.tobe.application;

import kitchenpos.menu.tobe.application.dto.*;
import kitchenpos.menu.tobe.domain.menu.*;
import kitchenpos.menu.tobe.domain.menu.validate.ProductValidator;
import kitchenpos.menu.tobe.domain.menu.validate.ProfanityValidator;
import kitchenpos.menu.tobe.domain.menugroup.MenuGroup;
import kitchenpos.menu.tobe.domain.menugroup.MenuGroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service("newMenuService")
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final ProfanityValidator profanityValidator;
    private final ProductValidator productValidator;

    public MenuService(
            final MenuRepository menuRepository,
            final MenuGroupRepository menuGroupRepository,
            final ProfanityValidator profanityValidator,
            final ProductValidator productValidator
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.profanityValidator = profanityValidator;
        this.productValidator = productValidator;
    }

    @Transactional
    public MenuResponse create(final CreateMenuRequest request) {
        MenuPrice menuPrice = new MenuPrice(request.price());
        MenuGroup menuGroup = getMenuGroup(request.menuGroupId());

        List<MenuProduct> menuProducts = createMenuProducts(request.menuProducts());
        MenuName menuName = new MenuName(request.name(), profanityValidator);

        Menu menu = new Menu(UUID.randomUUID(), menuName, menuPrice, menuGroup, request.display(), new MenuProducts(menuProducts, menuPrice, productValidator));
        Menu savedMenu = menuRepository.save(menu);

        return toMenuResponse(savedMenu);
    }

    private MenuGroup getMenuGroup(UUID menuGroupId) {
        return menuGroupRepository.findById(menuGroupId)
                .orElseThrow(() -> new NoSuchElementException("메뉴 그룹을 찾을 수 없습니다."));
    }

    private List<MenuProduct> createMenuProducts(List<CreateMenuProductRequest> createMenuProductRequests) {
        return createMenuProductRequests.stream()
                .map(request -> new MenuProduct(request.productId(), request.quantity(), request.price()))
                .toList();
    }

    @Transactional
    public MenuResponse changePrice(final UUID menuId, final ChangeMenuPrice request) {
        final BigDecimal price = request.price();
        final Menu menu = menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);

        menu.changePrice(price);

        return toMenuResponse(menu);
    }

    private Menu findMenuById(UUID menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(() -> new NoSuchElementException("메뉴를 찾을 수 없습니다."));
    }

    @Transactional
    public MenuResponse display(final UUID menuId) {
        final Menu menu = findMenuById(menuId);

        menu.display();

        return toMenuResponse(menu);
    }

    @Transactional
    public MenuResponse hide(final UUID menuId) {
        final Menu menu = findMenuById(menuId);

        menu.hide();

        return toMenuResponse(menu);
    }

    @Transactional(readOnly = true)
    public List<MenuResponse> findAll() {
        return menuRepository.findAll().stream()
                .map(this::toMenuResponse)
                .toList();
    }

    private MenuResponse toMenuResponse(Menu menu) {
        List<MenuProductResponse> menuProductResponses = menu.getMenuProducts().getMenuProducts().stream()
                .map(menuProduct -> new MenuProductResponse(
                        menuProduct.getSeq(),
                        menuProduct.getProductId(),
                        menuProduct.getQuantity(),
                        menuProduct.getPrice()
                ))
                .collect(Collectors.toList());

        return new MenuResponse(
                menu.getId(),
                menu.getName(),
                menu.getPrice(),
                menu.getMenuGroup().getId(),
                menuProductResponses
        );
    }
}
