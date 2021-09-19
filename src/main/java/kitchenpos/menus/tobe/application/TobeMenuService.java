package kitchenpos.menus.tobe.application;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.tobe.domain.*;
import kitchenpos.menus.tobe.infra.MenuTranslator;
import kitchenpos.menus.tobe.ui.MenuForm;
import kitchenpos.tobeinfra.TobePurgomalumClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class TobeMenuService {
    private final TobeMenuRepository menuRepository;
    private final TobeMenuGroupRepository menuGroupRepository;
    private final MenuTranslator menuTranslator;
    private final TobePurgomalumClient purgomalumClient;

    public TobeMenuService(
        final TobeMenuRepository menuRepository,
        final TobeMenuGroupRepository menuGroupRepository,
        final MenuTranslator menuTranslator,
        final TobePurgomalumClient purgomalumClient
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.menuTranslator = menuTranslator;
        this.purgomalumClient = purgomalumClient;
    }

    @Transactional
    public MenuForm create(final MenuForm request) {
        // 메뉴 가격
        final MenuPrice menuPrice = new MenuPrice(request.getPrice());

        // 메뉴 그룹
        final TobeMenuGroup menuGroup = menuGroupRepository.findById(request.getMenuGroupId())
            .orElseThrow(NoSuchElementException::new);

        // 메뉴 상품
        List<TobeMenuProduct> tobeProducts = menuTranslator.productFindAllByIdIn(request.getMenuProducts());
        final MenuProducts menuProducts = new MenuProducts(tobeProducts, menuPrice);

        // 메뉴 이름
        final MenuName menuName = new MenuName(request.getName(), purgomalumClient);

        // 메뉴 세팅
        final TobeMenu menu = new TobeMenu(
            menuName,
            menuPrice,
            menuGroup,
            request.isDisplayed(),
            menuProducts
        );

        // 저장 후 메뉴Form 반환
        return MenuForm.of(menuRepository.save(menu));
    }

//    @Transactional
//    public Menu changePrice(final UUID menuId, final Menu request) {
//        final MenuPrice menuPrice = new MenuPrice(request.getPrice());
//        final TobeMenu menu = menuRepository.findById(menuId)
//            .orElseThrow(NoSuchElementException::new);
//
//        for (final MenuProduct menuProduct : menu.getMenuProducts()) {
//            final BigDecimal sum = menuProduct.getProduct()
//                .getPrice()
//                .multiply(BigDecimal.valueOf(menuProduct.getQuantity()));
//            if (price.compareTo(sum) > 0) {
//                throw new IllegalArgumentException();
//            }
//        }
//        menu.setPrice(price);
//        return menu;
//    }
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
//
//    @Transactional(readOnly = true)
//    public List<Menu> findAll() {
//        return menuRepository.findAll();
//    }
}
