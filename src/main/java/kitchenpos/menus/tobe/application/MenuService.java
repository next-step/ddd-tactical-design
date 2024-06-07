package kitchenpos.menus.tobe.application;

import kitchenpos.menus.tobe.domain.application.CreateMenu;
import kitchenpos.menus.tobe.domain.entity.Menu;
import kitchenpos.menus.tobe.domain.entity.MenuProduct;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import kitchenpos.menus.tobe.domain.vo.MenuPrice;
import kitchenpos.menus.tobe.dto.MenuChangePriceDto;
import kitchenpos.menus.tobe.dto.MenuCreateDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final CreateMenu createMenu;

    public MenuService(
            final MenuRepository menuRepository,
            CreateMenu createMenu
    ) {
        this.menuRepository = menuRepository;
        this.createMenu = createMenu;
    }

    @Transactional
    public Menu create(final MenuCreateDto request) {
        return createMenu.execute(request);
    }

    @Transactional
    public Menu changePrice(final UUID menuId, final MenuChangePriceDto request) {
        final MenuPrice menuPrice = MenuPrice.of(request.getPrice());
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
        menu.setPrice(menuPrice);
        return menu;
    }

    @Transactional
    public Menu display(final UUID menuId) {
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
        if (menu.getPrice().compareTo(sum) > 0) {
            throw new IllegalStateException();
        }
        menu.setDisplayed(true);
        return menu;
    }

    @Transactional
    public Menu hide(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
                                        .orElseThrow(NoSuchElementException::new);
        menu.setDisplayed(false);
        return menu;
    }

    @Transactional(readOnly = true)
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }
}
