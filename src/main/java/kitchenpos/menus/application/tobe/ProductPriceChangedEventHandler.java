package kitchenpos.menus.application.tobe;

import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuRepository;
import kitchenpos.products.tobe.domain.event.ProductPriceChangedEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@Component
public class ProductPriceChangedEventHandler {

    /*
    상품의 가격을 변경 시 실행되는 이벤트 핸들러

    1. 가격 변경한 상품을 가진 메뉴들을 조회
    2. 메뉴를 루프 돌면서, 가격 변경된 상품 ID와 메뉴 상품의 ID가 일치할 때
    해당 상품의 가격을 변경
    3. 메뉴의 가격와 메뉴 상품의 합을 비교하여
    메뉴 가격 > 메뉴 상품의 총 합인 경우, 비전시된 메뉴로 만든다

    */
    private final MenuRepository menuRepository;

    public ProductPriceChangedEventHandler(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(ProductPriceChangedEvent event) {
        List<Menu> menus = menuRepository.findAllByProductId(event.getId());
        for (Menu menu : menus) {
            menu.changeProductPrice(event.getId(), event.getPrice());
        }
    }
}
