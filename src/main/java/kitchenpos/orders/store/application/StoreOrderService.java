package kitchenpos.orders.store.application;

import java.util.List;
import java.util.NoSuchElementException;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.menus.domain.tobe.Menu;
import kitchenpos.orders.store.application.dto.StoreOrderCreateRequest;
import kitchenpos.orders.store.domain.OrderTableRepository;
import kitchenpos.orders.store.domain.StoreOrderRepository;
import kitchenpos.orders.store.domain.tobe.OrderTable;
import kitchenpos.orders.store.domain.tobe.StoreOrder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StoreOrderService {

    private final StoreOrderRepository storeOrderRepository;
    private final MenuRepository menuRepository;
    private final OrderTableRepository orderTableRepository;

    public StoreOrderService(StoreOrderRepository storeOrderRepository,
            MenuRepository menuRepository, OrderTableRepository orderTableRepository) {
        this.storeOrderRepository = storeOrderRepository;
        this.menuRepository = menuRepository;
        this.orderTableRepository = orderTableRepository;
    }

    @Transactional
    public StoreOrder create(final StoreOrderCreateRequest request) {
        final List<Menu> menus = menuRepository.findAllByIdIn(request.getMenuIds());
        request.validate(menus);

        OrderTable orderTable = orderTableRepository.findById(request.getOrderTableId())
                .orElseThrow(NoSuchElementException::new);
        return storeOrderRepository.save(
                new StoreOrder(request.toOrderLineItems(menus), orderTable));
    }
}
