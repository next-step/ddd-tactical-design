package kitchenpos.menus.tobe.application;

import kitchenpos.common.domain.Profanities;
import kitchenpos.menus.tobe.domain.*;
import kitchenpos.menus.tobe.ui.dto.MenuChangePriceRequest;
import kitchenpos.menus.tobe.ui.dto.MenuCreateRequest;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final ProductRepository productRepository;
    private final Profanities profanities;
    private final MenuCreateValidator menuCreateValidator;

    public MenuService(
            final MenuRepository menuRepository,
            final MenuGroupRepository menuGroupRepository,
            final ProductRepository productRepository,
            final Profanities profanities,
            final MenuCreateValidator menuCreateValidator) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.productRepository = productRepository;
        this.profanities = profanities;
        this.menuCreateValidator = menuCreateValidator;
    }

    @Transactional
    public Menu create(final MenuCreateRequest request) {
        final Menu menu = menuCreateValidator.validate(request, menuGroupRepository, productRepository, profanities);
        return menuRepository.save(menu);
    }

    @Transactional
    public Menu changePrice(final UUID menuId, final MenuChangePriceRequest request) {
        final Menu menu = findById(menuId);
        menu.changePrice(request.price());
        return menu;
    }
//
//    @Transactional
//    public Menu display(final UUID menuId) {
//        final Menu menu = menuRepository.findById(menuId)
//            .orElseThrow(NoSuchElementException::new);
//        for (final MenuProduct menuProduct : menu.getMenuProducts()) {
//            final BigDecimal sum = menuProduct.getProduct()
//                .getPrice()
//                .multiply(BigDecimal.valueOf(menuProduct.getQuantity()));
//            if (menu.getPrice().compareTo(sum) > 0) {
//                throw new IllegalStateException();
//            }
//        }
//        menu.setDisplayed(true);
//        return menu;
//    }
//
//    @Transactional
//    public Menu hide(final UUID menuId) {
//        final Menu menu = menuRepository.findById(menuId)
//            .orElseThrow(NoSuchElementException::new);
//        menu.setDisplayed(false);
//        return menu;
//    }

    @Transactional(readOnly = true)
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Menu findById(final UUID menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);
    }
}
