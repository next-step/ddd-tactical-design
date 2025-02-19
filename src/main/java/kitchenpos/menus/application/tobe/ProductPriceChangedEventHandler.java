package kitchenpos.menus.application.tobe;

import kitchenpos.menus.application.MenuService;
import kitchenpos.products.tobe.domain.event.ProductPriceChangedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ProductPriceChangedEventHandler {

    private final MenuService menuService;


    public ProductPriceChangedEventHandler(MenuService menuService) {
        this.menuService = menuService;
    }

    //@AfterDomainEventPublication
    @EventListener(ProductPriceChangedEvent.class)
    public void handle(ProductPriceChangedEvent event) {
        //TODO 메뉴 도메인 구현 시 아래도 구현 할 것
//        final List<Menu> menus = menuRepository.findAllByProductId(productId);
//            for (final Menu menu : menus) {
//                BigDecimal sum = BigDecimal.ZERO;
//                for (final MenuProduct menuProduct : menu.getMenuProducts()) {
//                    sum = sum.add(
//                            menuProduct.getProduct()
//                                    .getPrice()
//                                    .multiply(BigDecimal.valueOf(menuProduct.getQuantity()))
//                    );
//                }
//                if (menu.getPrice().compareTo(sum) > 0) {
//                    menu.setDisplayed(false);
//                }
    }
}
