package kitchenpos.menus.application;

import kitchenpos.common.domain.vo.Price;
import kitchenpos.menus.tobe.domain.entity.Menu;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import kitchenpos.menus.ui.dto.ChangePriceRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ChangePriceService {
    private final MenuRepository menuRepository;

    public ChangePriceService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Transactional
    public Menu change(final UUID menuId, final ChangePriceRequest request) {
        final Menu menu = menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);

        Price price = new Price(request.price);
        menu.changePrice(price);

        return menu;
    }
}
