package kitchenpos.menus.application;

import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.reader.application.MenuPriceReader;
import kitchenpos.reader.domain.MenuPriceAndDisplayed;
import org.springframework.stereotype.Service;

@Service
public class MenuPriceReaderImpl implements MenuPriceReader {

    private final MenuRepository menuRepository;

    public MenuPriceReaderImpl(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public MenuPriceAndDisplayed getMenuPriceAndDisplayedById(UUID menuId) {
        Menu menu = findMenuById(menuId);
        return new MenuPriceAndDisplayed(menu.getId(), menu.getPriceValue(), menu.isDisplayed());
    }

    private Menu findMenuById(UUID menuId) {
        return menuRepository.findById(menuId)
            .orElseThrow(() -> new NoSuchElementException("ID 에 해당하는 메뉴를 찾을 수 없습니다."));
    }
}
