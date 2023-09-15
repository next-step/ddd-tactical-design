package kitchenpos.eatinorders.application;

import kitchenpos.deliveryorders.infra.KitchenridersClient;
import kitchenpos.eatinorders.domain.tobe.*;
import kitchenpos.eatinorders.ui.request.EatInOrderCreateRequest;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EatInOrderService {
    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final OrderTableRepository orderTableRepository;
    private final OrderManager orderManager;

    public EatInOrderService(OrderRepository orderRepository, MenuRepository menuRepository, OrderTableRepository orderTableRepository, OrderManager orderManager) {
        this.orderRepository = orderRepository;
        this.menuRepository = menuRepository;
        this.orderTableRepository = orderTableRepository;
        this.orderManager = orderManager;
    }

    @Transactional
    public EatInOrder create(final EatInOrderCreateRequest request) {
        List<EatInOrderLineItem> eatInOrderLineItems = Objects.isNull(request.getOrderLineItems()) ? null : request.getOrderLineItems()
                .stream()
                .map(eatInOrderLineItemRequest -> {
                    Menu menu = menuRepository.findById(eatInOrderLineItemRequest.getId())
                            .orElseThrow(IllegalArgumentException::new);

                    if (!menu.isDisplayed()) {
                        throw new IllegalStateException();
                    }

                    return new EatInOrderLineItem(menu.getId(), menu.getPrice(), eatInOrderLineItemRequest.getQuantity());
                })
                .collect(Collectors.toList());

        OrderTable orderTable = orderTableRepository.findById(request.getOrderTableId())
                .orElseThrow(NoSuchElementException::new);

        EatInOrder order = new EatInOrder(eatInOrderLineItems, orderTable);
        return orderRepository.save(order);
    }

    @Transactional
    public EatInOrder accept(final UUID orderId) {
        final EatInOrder order = orderRepository.findById(orderId).orElseThrow(NoSuchElementException::new);
        order.accept();
        return order;
    }

    @Transactional
    public EatInOrder serve(final UUID orderId) {
        final EatInOrder order = orderRepository.findById(orderId).orElseThrow(NoSuchElementException::new);
        order.serve();
        return order;
    }

    @Transactional
    public EatInOrder complete(final UUID orderId) {
        final EatInOrder order = orderRepository.findById(orderId).orElseThrow(NoSuchElementException::new);
        order.complate(orderManager);
        return order;
    }

    @Transactional(readOnly = true)
    public List<EatInOrder> findAll() {
        return orderRepository.findAll();
    }
}
