package kitchenpos.menus.tobe.domain.application;

import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.menus.tobe.domain.entity.Menu;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import kitchenpos.menus.tobe.domain.vo.MenuPrice;
import kitchenpos.menus.tobe.dto.MenuChangePriceDto;
import kitchenpos.products.tobe.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;

@FunctionalInterface
public interface ChangeMenuPrice {
    Menu execute(UUID menuId, MenuChangePriceDto menuChangePriceDto);
}

@Service
class DefaultChangeMenuPrice implements ChangeMenuPrice {
    private final MenuRepository menuRepository;
    private final ProductRepository productRepository;

    public DefaultChangeMenuPrice(MenuRepository menuRepository, ProductRepository productRepository) {
        this.menuRepository = menuRepository;
        this.productRepository = productRepository;
    }

    @Override
    public final Menu execute(UUID menuId, MenuChangePriceDto menuChangePriceDto) {
        final MenuPrice menuPrice = MenuPrice.of(menuChangePriceDto.getPrice());
        final Menu menu = menuRepository.findMenuById(menuId)
                                        .orElseThrow(NoSuchElementException::new);
        menu.changePrice(menuPrice, productRepository);
        menuRepository.saveMenu(menu);
        return menu;
    }
}
