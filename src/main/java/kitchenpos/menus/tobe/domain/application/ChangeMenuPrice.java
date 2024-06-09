package kitchenpos.menus.tobe.domain.application;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.menus.tobe.domain.entity.Menu;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import kitchenpos.menus.tobe.domain.vo.MenuPrice;
import kitchenpos.menus.tobe.dto.MenuChangePriceDto;
import org.springframework.stereotype.Service;

@FunctionalInterface
public interface ChangeMenuPrice {
    Menu execute(UUID menuId, MenuChangePriceDto menuChangePriceDto);
}

@Service
class DefaultChangeMenuPrice implements ChangeMenuPrice {
    private final MenuRepository menuRepository;
    private final CalculateSumOfMultiplyingMenuProductPriceAndMenuProductQuantity calculateSumOfMultiplyingMenuProductPriceAndMenuProductQuantity;

    public DefaultChangeMenuPrice(MenuRepository menuRepository,
                                  CalculateSumOfMultiplyingMenuProductPriceAndMenuProductQuantity calculateSumOfMultiplyingMenuProductPriceAndMenuProductQuantity) {
        this.menuRepository = menuRepository;
        this.calculateSumOfMultiplyingMenuProductPriceAndMenuProductQuantity = calculateSumOfMultiplyingMenuProductPriceAndMenuProductQuantity;
    }

    @Override
    public final Menu execute(UUID menuId, MenuChangePriceDto menuChangePriceDto) {
        final MenuPrice menuPrice = MenuPrice.of(menuChangePriceDto.getPrice());
        final Menu menu = menuRepository.findMenuById(menuId)
                                        .orElseThrow(NoSuchElementException::new);
        BigDecimal sum = calculateSumOfMultiplyingMenuProductPriceAndMenuProductQuantity.execute(menu);

        if (menuPrice.getValue().compareTo(sum) > 0) {
            throw new IllegalArgumentException();
        }
        menu.changePrice(menuPrice);
        menuRepository.saveMenu(menu);
        return menu;
    }
}
