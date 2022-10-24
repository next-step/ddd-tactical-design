package kitchenpos.menus.tobe.application;

import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import kitchenpos.menus.tobe.dto.menu.ChangeMenuPriceRequest;
import kitchenpos.products.tobe.domain.Price;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class MenuPriceService {

    private final MenuRepository menuRepository;

    public MenuPriceService(final MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Transactional
    public Menu changePrice(final UUID menuId, final ChangeMenuPriceRequest request) {
        final Menu menu = menuRepository.findById(menuId).orElseThrow(NoSuchElementException::new);
        menu.changePrice(new Price(request.price()));
        return menu;
    }

}
