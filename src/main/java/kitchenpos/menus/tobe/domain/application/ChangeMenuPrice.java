package kitchenpos.menus.tobe.domain.application;

import kitchenpos.menus.tobe.domain.entity.Menu;
import kitchenpos.menus.tobe.domain.entity.MenuProduct;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import kitchenpos.menus.tobe.domain.vo.MenuPrice;
import kitchenpos.menus.tobe.dto.MenuChangePriceDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.UUID;

public interface ChangeMenuPrice {
    Menu execute(UUID menuId, MenuChangePriceDto menuChangePriceDto);
}

@Service
class DefaultChangeMenuPrice implements ChangeMenuPrice {
    private final MenuRepository menuRepository;

    public DefaultChangeMenuPrice(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public final Menu execute(UUID menuId, MenuChangePriceDto menuChangePriceDto) {
        final MenuPrice menuPrice = MenuPrice.of(menuChangePriceDto.getPrice());
        final Menu menu = menuRepository.findById(menuId)
                                        .orElseThrow(NoSuchElementException::new);
        BigDecimal sum = BigDecimal.ZERO;
        for (final MenuProduct menuProduct : menu.getMenuProducts()) {
            sum = sum.add(
                    menuProduct.getProduct()
                               .getPrice()
                               .multiply(BigDecimal.valueOf(menuProduct.getQuantity()))
            );
        }
        if (menuPrice.getValue().compareTo(sum) > 0) {
            throw new IllegalArgumentException();
        }
        menu.changePrice(menuPrice);
        return menu;
    }
}
