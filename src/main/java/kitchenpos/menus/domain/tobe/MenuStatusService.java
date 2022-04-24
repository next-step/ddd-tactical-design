package kitchenpos.menus.domain.tobe;

import kitchenpos.menus.dto.MenuRequest;
import kitchenpos.menus.dto.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class MenuStatusService {
    private static final String INVALID_MENU_PRICE = "잘못된 메뉴 가격입니다.";

    private final MenuRepository menuRepository;

    public MenuStatusService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public void showMenu(Menu menu, ProductResponse productResponse) {
        menuRepository.findByUUId(menu.getId())
                .orElseThrow(NoSuchElementException::new);
        final MenuPrice menuPrice = menu.getPrice();
        final MenuPrice menuProductsTotalPrice = new MenuPrice(productResponse.getPrice());

        if (menuPrice.isBiggerThan(menuProductsTotalPrice)) {
            throw new IllegalStateException(INVALID_MENU_PRICE);
        }
        menu.show();
    }

    public void hideMenu(Menu menu) {
        menuRepository.findByUUId(menu.getId())
                .orElseThrow(NoSuchElementException::new);
        menu.hide();
    }

    public void changePrice(final UUID menuId, ProductResponse productResponse, MenuRequest menuRequest) {
        final Menu menu = menuRepository.findByUUId(menuId)
                .orElseThrow(NoSuchElementException::new);
        final MenuPrice menuProductsTotalPrice = new MenuPrice(productResponse.getPrice());
        final MenuPrice changeMenuPrice = new MenuPrice(menuRequest.getMenuPrice());
        if (changeMenuPrice.isBiggerThan(menuProductsTotalPrice)) {
            throw new IllegalStateException(INVALID_MENU_PRICE);
        }
        menu.changePrice(changeMenuPrice);
    }
}
