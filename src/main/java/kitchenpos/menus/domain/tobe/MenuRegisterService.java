package kitchenpos.menus.domain.tobe;

import kitchenpos.menus.dto.MenuProductRequest;
import kitchenpos.menus.dto.MenuRequest;
import kitchenpos.menus.dto.ProductResponse;
import kitchenpos.products.domain.tobe.BanWordFilter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MenuRegisterService {
    private static final String INVALID_MENU_PRICE = "잘못된 메뉴 가격입니다.";
    private final ApplicationEventPublisher eventPublisher;
    private final BanWordFilter banWordFilter;

    public MenuRegisterService(ApplicationEventPublisher eventPublisher, BanWordFilter banWordFilter) {
        this.eventPublisher = eventPublisher;
        this.banWordFilter = banWordFilter;
    }

    public Menu registerMenu(MenuRequest menuRequest, ProductResponse productResponse) {
        final UUID menuGroupId = menuRequest.getMenuGroupId();
        final String menuName = menuRequest.getMenuName();
        final MenuPrice menuPrice = new MenuPrice(menuRequest.getMenuPrice());
        final boolean isDisplayed = menuRequest.isDisplayed();
        final MenuPrice menuProductTotal = new MenuPrice(productResponse.getPrice());

        if (menuPrice.isBiggerThan(menuProductTotal)) {
            throw new IllegalArgumentException(INVALID_MENU_PRICE);
        }

        final List<MenuProductRequest> menuProductRequests = menuRequest.getMenuProductRequests();
        final List<MenuProduct> menuProducts = menuProductRequests.stream()
                .map(it -> new MenuProduct(it.getQuantity(), it.getProductId()))
                .collect(Collectors.toList());

        final Menu menu = new Menu(
                menuName, banWordFilter, menuPrice,
                menuGroupId, isDisplayed, menuProducts
        );

        eventPublisher.publishEvent(new MenuCreatedEvent(menu));
        return menu;
    }
}
